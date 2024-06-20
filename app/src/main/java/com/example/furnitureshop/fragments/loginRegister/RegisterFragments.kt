package com.example.furnitureshop.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle.State.*
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.furnitureshop.R
import com.example.furnitureshop.com.example.furnitureshop.util.RegisterValidation
import com.example.furnitureshop.data.User
import com.example.furnitureshop.databinding.FragmentsRegisterBinding
import com.example.furnitureshop.util.Resource
import com.example.furnitureshop.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "RegisterFragments"

@AndroidEntryPoint
class RegisterFragments : Fragment(R.layout.fragments_register) {

    private lateinit var binding: FragmentsRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentsRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonRegisterRegister.setOnClickListener {
                val user = User(
                    etFirstName.text.toString().trim(),
                    etLastName.text.toString().trim(),
                    etEmailRegister.text.toString().trim()
                )
                val password = etPasswordRegister.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch() {
            lifecycle.repeatOnLifecycle(STARTED) {
                viewModel.register.collect {
                    when (it) {
                        is Resource.Loading -> {
                            binding.buttonRegisterRegister.startAnimation()
                        }

                        is Resource.Success -> {
                            Log.d("test", it.data.toString())
                            binding.buttonRegisterRegister.revertAnimation()
                        }

                        is Resource.Error -> {
                            Log.d(TAG, it.message.toString())
                            binding.buttonRegisterRegister.revertAnimation()
                        }

                        else -> Unit
                    }
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(STARTED) {
                    viewModel.validation.collect() { validation ->
                        if (validation.email is RegisterValidation.Failed) withContext(Dispatchers.Main) {
                            binding.etEmailRegister.apply {
                                error = validation.email.message
                            }
                        }
                        if (validation.password is RegisterValidation.Failed) withContext(Dispatchers.Main) {
                            binding.etPasswordRegister.apply {
                                error = validation.password.message
                            }
                        }
                    }
                }
            }
        }
    }
}
