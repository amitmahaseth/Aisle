package com.example.aisle.module.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.aisle.databinding.FragmentNotesBinding
import com.example.aisle.module.note.model.NotesResponse
import com.example.aisle.module.viewModel.VMData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private var vmGetUserProfile: VMData? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        vmGetUserProfile = VMData(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getUserProfileApi()
    }


    private fun init() {

    }

    private fun getUserProfileApi() {
        CoroutineScope(Dispatchers.Main).launch {
            vmGetUserProfile!!.getUserProfile().observe(this@NotesFragment,
                Observer {
                    val response = it as NotesResponse
                    Glide
                        .with(requireContext())
                        .load(response.invites.profiles[0].photos[0])

                        .into(binding.imgProfile)
                    binding.tvPersonalName.text=response.invites.profiles[0].general_information.first_name+", "+response.invites.profiles[0].general_information.age

                    Glide
                        .with(requireContext())
                        .load(response.likes.profiles[0].avatar[0])
                        .into(binding.imgProfileView)
                    binding.tvProfileName.text=response.likes.profiles[0].first_name

                    Glide
                        .with(requireContext())
                        .load(response.likes.profiles[1].avatar[0])
                        .into(binding.imgProfileView2)
                    binding.tvProfileName2.text=response.likes.profiles[1].first_name
                })
        }

    }
}