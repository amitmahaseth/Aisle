package com.example.aisle.module.model

data class ErrorResponse(
    val message: String,
    val status: Int
)

data class Data(
    val location: String,
    val msg: String,
    val `param`: String,
    val value: String
)