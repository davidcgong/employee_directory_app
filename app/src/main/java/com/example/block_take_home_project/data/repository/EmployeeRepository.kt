package com.example.block_take_home_project.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.block_take_home_project.data.ApiInterface
import com.example.block_take_home_project.infra.Resource
import com.example.block_take_home_project.infra.Status
import com.example.block_take_home_project.model.Employee
import com.example.block_take_home_project.model.Employees
import com.squareup.moshi.JsonDataException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

// We use a Repository to allow our ViewModel to have better separation of concerns. Repository will
// handle caching and network call, and ViewModel will be the interface between the UI layer and repository
@Singleton // We use Singleton since we only ever need one instance of EmployeeRepository throughout our app
class EmployeeRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    // Valid JSON
    fun getEmployeeList() : MutableLiveData<Resource<List<Employee>>> {
        val response = apiInterface.getEmployees()
        return getEmployeeListLiveData(response, MutableLiveData<Resource<List<Employee>>>())
    }

    // the following methods will only be called to test and validate responses from the API
    // Empty JSON
    fun getEmptyEmployeeList(): MutableLiveData<Resource<List<Employee>>> {
        val response = apiInterface.getEmptyEmployees()
        return getEmployeeListLiveData(response, MutableLiveData<Resource<List<Employee>>>())
    }

    // Malformed JSON
    fun getMalformedEmployeeList(): MutableLiveData<Resource<List<Employee>>> {
        val response = apiInterface.getMalformedEmployees()
        return getEmployeeListLiveData(response, MutableLiveData<Resource<List<Employee>>>())
    }

    // helper function for async network call on background thread, creates a MutableLiveData for its callers
    private fun getEmployeeListLiveData(response: Call<Employees>,
                                        employeeListLiveData: MutableLiveData<Resource<List<Employee>>>
    ): MutableLiveData<Resource<List<Employee>>> {
        response.enqueue(object: Callback<Employees> {
            override fun onResponse(call: Call<Employees>, response: Response<Employees>) {
                if (response.isSuccessful) {
                    employeeListLiveData.postValue(Resource(Status.SUCCESS, response.body()?.employees, null))
                } else {
                    employeeListLiveData.postValue(Resource(Status.ERROR, null, null))
                }
            }

            override fun onFailure(call: Call<Employees>, t: Throwable) {
                when (t)  {
                    // JsonDataException is used for MALFORMED requests
                    is JsonDataException -> {
                        employeeListLiveData.postValue(Resource(Status.MALFORMED, null, t.localizedMessage))
                    }
                }
            }
        })
        return employeeListLiveData
    }
}