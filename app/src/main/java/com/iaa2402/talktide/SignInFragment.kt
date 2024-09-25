package com.iaa2402.talktide

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.iaa2402.talktide.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)



        binding.signInBtn.setOnClickListener {

            val email = binding.emailET.text.toString().trim()
            val password = binding.passwordET.text.toString().trim()
            val user = binding.UserNameET.text.toString().trim()

            if (isValidEmail(email) && isValidPassword(password)) {

                signInUser(user, email, password)

            }else{
                Toast.makeText(requireActivity(), "Invalid email and password", Toast.LENGTH_SHORT).show()
            }

        }


        return binding.root
    }

    private fun signInUser(user: String, email: String, password: String) {

        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->


            if (task.isSuccessful) {

                Toast.makeText(requireActivity(), "Create Account Successfully", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_signInFragment_to_loginFragment)

            } else {

                Toast.makeText(requireActivity(), "${task.exception?.message}", Toast.LENGTH_SHORT)
                    .show()

            }


        }


    }


    fun isValidEmail(email: String): Boolean {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches()

    }

    fun isValidPassword(password: String): Boolean {

        val passRegex = Regex("^(?=.*[A-Za-z])(?=.*[@$!%*#?&])[A-Za-z@$!%*#?&\\d]{6,}$")

        return password.matches(passRegex)

    }


}