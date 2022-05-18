package com.example.block_take_home_project.data.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.block_take_home_project.data.repository.EmployeeRepository
import com.example.block_take_home_project.infra.Resource
import com.example.block_take_home_project.model.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// ViewModel for EmployeeFragment.kt - allows the UI to survive configuration changes and avoid fetching network when the view is re-created
@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val repository: EmployeeRepository
) : ViewModel() {
    private var employeeList = MutableLiveData<Resource<List<Employee>>>()

    fun getEmployeeList(): MutableLiveData<Resource<List<Employee>>> {
        return employeeList
    }

    fun fetchEmployeeList() {
        employeeList = repository.getEmployeeList()
    }

    // there is no reason to call the following methods below, only used for testing
    fun fetchEmptyEmployeeList() {
        employeeList = repository.getEmptyEmployeeList()
    }

    fun fetchMalformedEmployeeList() {
        employeeList = repository.getMalformedEmployeeList()
    }
}