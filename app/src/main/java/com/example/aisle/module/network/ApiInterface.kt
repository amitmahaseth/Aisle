package com.example.aisle.module.network

import com.example.aisle.module.note.model.NotesResponse
import com.example.aisle.module.otp.model.OtpResponse
import com.example.aisle.module.phoneNumber.model.PhoneVerificationResponse
import com.example.aisle.module.utils.Constant
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @POST("users/phone_number_login")
    suspend fun verifiedPhone(@Body phoneData:JsonObject):Response<PhoneVerificationResponse>

    @POST("users/verify_otp")
    suspend fun verifiedOtp(@Body otpData:JsonObject):Response<OtpResponse>

    @GET("users/test_profile_list")
    suspend fun  getUserProfileList(@Header(Constant.AUTHORIZATION) token: String):Response<NotesResponse>
}