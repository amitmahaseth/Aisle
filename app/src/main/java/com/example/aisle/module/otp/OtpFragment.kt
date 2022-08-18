package com.example.aisle.module.otp

import SharedPref
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.aisle.R
import com.example.aisle.databinding.FragmentOtpBinding
import com.example.aisle.module.otp.model.OtpResponse
import com.example.aisle.module.utils.Constant
import com.example.aisle.module.viewModel.VMData
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import makeToast

class OtpFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding
    private var vmVerifiedPhone: VMData? = null
    private lateinit var pref:SharedPref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        pref=SharedPref(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        timer()
    }

    private fun init() {
        vmVerifiedPhone= VMData(requireActivity())
        var bundle = arguments
        var number = bundle!!.getString(Constant.Number)
        binding.tvPhoneNumber.text = number

        binding.btnContinueOtp.setOnClickListener {
            if (binding.etOtp.text.toString() == "1234") {
                verifyApi(number)
            } else {
                requireActivity().makeToast("Please enter valid otp")
            }
        }

    }

    private fun timer() {
        object : CountDownTimer(59000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                if (millisUntilFinished / 1000 < 10) {
                    binding.tvTimer.text = ("00:0${millisUntilFinished / 1000}")

                } else {
                    binding.tvTimer.text = ("00:${millisUntilFinished / 1000}")

                }
            }

            override fun onFinish() {

            }
        }.start()
    }

    private fun verifyApi(number: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            var jsonObject = JsonObject()
            jsonObject.addProperty(Constant.Number, number)
            jsonObject.addProperty(Constant.OTP, binding.etOtp.text.toString())
            vmVerifiedPhone!!.otpVerification(jsonObject).observe(this@OtpFragment,
                Observer {
                    val response = it as OtpResponse
                    if (response.token != null) {
                        pref.saveString(Constant.TOKEN,response.token)
                        findNavController().navigate(R.id.action_otpFragment_to_notesFragment)
                    }

                })
        }

    }

}