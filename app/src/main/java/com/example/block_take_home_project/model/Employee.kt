package com.example.block_take_home_project.model

import com.squareup.moshi.Json

data class Employee(
    @Json val uuid: String,
    @Json val full_name: String,
    @Json val phone_number: String?,
    @Json val email_address: String,
    @Json val biography: String?,
    @Json val photo_url_small: String?,
    @Json val photo_url_large: String?,
    @Json val team: String,
    @Json val employee_type: String
)