package com.example.block_take_home_project.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Employees(
    @Json val employees: List<Employee>
)