package com.iaa2402.talktide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.iaa2402.talktide.databinding.FragmentHomeBinding
import com.iaa2402.talktide.databinding.FragmentSignInBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)

        binding.logoutBtn.setOnClickListener{

            val auth = FirebaseAuth.getInstance()

            auth.signOut().apply {

                Toast.makeText(requireActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)

            }


        }




        return binding.root
    }


}