package com.example.aisle.module.phoneNumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.aisle.R
import com.example.aisle.databinding.FragmentPhoneNumberBinding
import com.example.aisle.module.phoneNumber.model.PhoneVerificationResponse
import com.example.aisle.module.utils.Constant
import com.example.aisle.module.viewModel.VMData
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import makeToast

class PhoneNumberFragment : Fragment() {

    private lateinit var binding: FragmentPhoneNumberBinding
    private var vmVerifiedPhone: VMData? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        vmVerifiedPhone = VMData(requireActivity())
        binding.btnContinuePhone.setOnClickListener {
            if (binding.etPhoneNumber.text.toString().isEmpty()) {
                requireActivity().makeToast("Please enter phone number")
            } else if (binding.etPhoneNumber.text.toString() == "9876543212") {
                VerifiedApi()
            } else {
                requireActivity().makeToast("Please enter valid number")


            }
        }

    }

    private fun VerifiedApi() {
        CoroutineScope(Dispatchers.Main).launch {
            var jsonObject = JsonObject()
            var phoneNumber = "+91" + binding.etPhoneNumber.text.toString()
            jsonObject.addProperty(Constant.Number, phoneNumber)
            vmVerifiedPhone!!.phoneNumberVerification(jsonObject).observe(this@PhoneNumberFragment,
                Observer {
                    val response = it as PhoneVerificationResponse
                    if (response.status) {
                        var bundle = Bundle()
                        bundle.putString(Constant.Number, phoneNumber)
                        findNavController().navigate(R.id.action_phoneNumberFragment_to_otpFragment,bundle)
                    }
                })
        }

    }
}