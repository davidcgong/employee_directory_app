package com.example.block_take_home_project.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.block_take_home_project.data.repository.EmployeeRepository
import com.example.block_take_home_project.data.viewmodel.EmployeeViewModel
import com.example.block_take_home_project.getOrAwaitValue
import com.example.block_take_home_project.infra.Resource
import com.example.block_take_home_project.infra.Status
import com.example.block_take_home_project.model.Employees
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.junit.Assert.*

class EmployeeViewModelShould {
    @Mock
    private lateinit var viewModel: EmployeeViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = Mockito.spy(EmployeeViewModel(repository = EmployeeRepository(apiInterface = ApiInterface.create())))
    }

    @Test
    fun verifyValidEmployeesResponse() {
        // verify that the list contains elements
        viewModel.fetchEmployeeList()
        assertTrue(viewModel.getEmployeeList().getOrAwaitValue().data!!.isNotEmpty())
        assertEquals(viewModel.getEmployeeList().getOrAwaitValue().status, Status.SUCCESS)
    }

    @Test
    fun verifyEmptyEmployeesResponse() {
        // verify that the list is empty
        viewModel.fetchEmptyEmployeeList()
        assertTrue(viewModel.getEmployeeList().getOrAwaitValue().data.isNullOrEmpty())
        assertEquals(viewModel.getEmployeeList().getOrAwaitValue().status, Status.SUCCESS)
    }

    @Test
    fun verifyMalformedEmployeesResponse() {
        // verify that the list was never set / invalidated and Status.MALFORMED
        viewModel.fetchMalformedEmployeeList()
        assertNull(viewModel.getEmployeeList().getOrAwaitValue().data)
        assertEquals(viewModel.getEmployeeList().getOrAwaitValue().status, Status.MALFORMED)
    }
}