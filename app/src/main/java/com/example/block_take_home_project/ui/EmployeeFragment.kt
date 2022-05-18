package com.example.block_take_home_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.block_take_home_project.data.viewmodel.EmployeeViewModel
import com.example.block_take_home_project.databinding.FragmentEmployeeListBinding
import com.example.block_take_home_project.infra.Status
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

/**
 * A fragment representing a list of [Employee].
 */
@AndroidEntryPoint
@WithFragmentBindings
class EmployeeFragment : Fragment() {

    // ViewModel (EmployeeViewModel)
    private val viewModel by viewModels<EmployeeViewModel>()

    // Binding
    private lateinit var binding : FragmentEmployeeListBinding

    // Child View / ViewGroups
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingTextView: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyLayout: LinearLayout
    private lateinit var malformedLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeListBinding.inflate(inflater, container, false)
        val view = binding.root

        // instantiate / observe views
        initViews()
        setupSwipeListener()
        setupButtonListeners()
        updateEmployeeList()

        return view
    }

    // initialize views with Data Binding
    private fun initViews() {
        progressBar = binding.employeeListProgressBar
        loadingTextView = binding.employeeListLoadingText
        swipeRefreshLayout = binding.employeeListLayout
        recyclerView = binding.employeeListRecyclerView
        emptyLayout = binding.employeeListEmptyView.root
        malformedLayout = binding.employeeListMalformedView.root
    }

    // fetch the valid JSON data
    private fun fetchData() {
        viewModel.fetchEmployeeList()
    }

    // technical requirement: App displays a list which shows all employees returned from JSON endpoint, contains photo, name, team, and summary
    private fun updateEmployeeList() {
        viewModel.getEmployeeList().observe(viewLifecycleOwner, Observer {
            // technical requirement: An empty response body yields an empty state on the UI
            if (it.data?.isEmpty() == true) {
                displayEmptyState()
                return@Observer
            } else if (it.status == Status.MALFORMED) {
                // technical requirement: A malformed JSON will result in malformed state on the UI
                displayMalformedState()
                return@Observer
            }
            displaySuccessState()
            recyclerView.adapter = MyEmployeeRecyclerViewAdapter(it.data!!)
        })
    }

    // technical requirement: Pull-to-refresh
    private fun setupSwipeListener() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.fetchEmployeeList()
            updateEmployeeList()
        }
    }

    // technical requirement: Button to retry network call
    private fun setupButtonListeners() {
        // empty state button
        binding.employeeListEmptyView.employeeEmptyBtn.setOnClickListener {
            viewModel.fetchEmployeeList()
            updateEmployeeList()
        }

        // malformed state button
        binding.employeeListMalformedView.employeeMalformedBtn.setOnClickListener {
            // re-fetches the valid employee list
            viewModel.fetchEmployeeList()
            updateEmployeeList()
        }
    }

    private fun displaySuccessState() {
        removeLoadingUI()
        swipeRefreshLayout.visibility = View.VISIBLE
        emptyLayout.visibility = View.GONE
        malformedLayout.visibility = View.GONE
    }

    private fun displayEmptyState() {
        removeLoadingUI()
        swipeRefreshLayout.visibility = View.GONE
        emptyLayout.visibility = View.VISIBLE
        malformedLayout.visibility = View.GONE
    }

    private fun displayMalformedState() {
        removeLoadingUI()
        swipeRefreshLayout.visibility = View.GONE
        emptyLayout.visibility = View.GONE
        malformedLayout.visibility = View.VISIBLE
    }

    private fun removeLoadingUI() {
        progressBar.visibility = View.GONE
        loadingTextView.visibility = View.GONE
    }
}