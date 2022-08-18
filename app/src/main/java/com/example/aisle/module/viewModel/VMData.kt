package com.example.aisle.module.viewModel

import SharedPref
import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aisle.module.network.ApiInterface
import com.example.aisle.module.network.Service
import com.example.aisle.module.note.model.NotesResponse
import com.example.aisle.module.otp.model.OtpResponse
import com.example.aisle.module.phoneNumber.model.PhoneVerificationResponse
import com.example.aisle.module.utils.Constant
import com.example.aisle.module.utils.Extention
import com.example.aisle.module.utils.Extention.showProgess
import com.example.aisle.module.utils.Extention.stopProgress
import com.google.gson.JsonObject
import isInternetAvailable
import makeToast

class VMData(private var activity: Activity) : ViewModel() {
    var apiInterface = Service.apiClient().create(ApiInterface::class.java)
    private var pref = SharedPref(activity)
    suspend fun phoneNumberVerification(phoneData: JsonObject): LiveData<PhoneVerificationResponse> {
        val mutableLiveData = MutableLiveData<PhoneVerificationResponse>()
        if (activity.isInternetAvailable()) {
            try {
                showProgess(activity)
                val response = apiInterface.verifiedPhone(phoneData)

                if (response.code() == 200) {
                    stopProgress()
                    mutableLiveData.postValue(response.body())
                } else {
                    stopProgress()
                    activity.makeToast(Extention.errorMessage(response.errorBody()!!))
                }
            } catch (e: Exception) {
                stopProgress()
                Log.e("shkgbhsk", e.message.toString())
                activity.makeToast(e.message.toString())
            }
        } else {
            stopProgress()
            activity.makeToast(Constant.NETWORK_NOT_AVAILABLE)
        }
        return mutableLiveData
    }


    suspend fun otpVerification(otpData: JsonObject): LiveData<OtpResponse> {
        val mutableLiveData = MutableLiveData<OtpResponse>()
        if (activity.isInternetAvailable()) {
            try {
                showProgess(activity)
                val response = apiInterface.verifiedOtp(otpData)

                if (response.code() == 200) {
                    stopProgress()
                    mutableLiveData.postValue(response.body())
                } else {
                    stopProgress()
                    activity.makeToast(Extention.errorMessage(response.errorBody()!!))
                }
            } catch (e: Exception) {
                stopProgress()
                Log.e("shkgbhsk", e.message.toString())
                activity.makeToast(e.message.toString())
            }
        } else {
            stopProgress()
            activity.makeToast(Constant.NETWORK_NOT_AVAILABLE)
        }
        return mutableLiveData
    }

    suspend fun getUserProfile(): LiveData<NotesResponse> {
        val mutableLiveData = MutableLiveData<NotesResponse>()
        if (activity.isInternetAvailable()) {
            try {
                showProgess(activity)
                val response = apiInterface.getUserProfileList(pref.getString(Constant.TOKEN))

                if (response.code() == 200) {
                    stopProgress()
                    mutableLiveData.postValue(response.body())
                } else {
                    stopProgress()
                    activity.makeToast(Extention.errorMessage(response.errorBody()!!))
                }
            } catch (e: Exception) {
                stopProgress()
                Log.e("shkgbhsk", e.message.toString())
                activity.makeToast(e.message.toString())
            }
        } else {
            stopProgress()
            activity.makeToast(Constant.NETWORK_NOT_AVAILABLE)
        }
        return mutableLiveData
    }
}