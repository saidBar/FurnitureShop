package com.example.furnitureshop.fragments.loginRegister
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle.State.*
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.furnitureshop.R
import com.example.furnitureshop.activities.ShoppingActivity
import com.example.furnitureshop.com.example.furnitureshop.viewmodel.LoginViewModel
import com.example.furnitureshop.databinding.FragmentsLoginBinding
import com.example.furnitureshop.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragments : Fragment(R.layout.fragments_login) {
    private lateinit var binding: FragmentsLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentsLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDontHaveAccountYet.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragments_to_registerFragments)
        }

        binding.apply {
             buttonLoginLogin.setOnClickListener {
                 val email = etEmailLogin.text.toString().trim()
                 val password = etPasswordLogin.text.toString()
                 viewModel.login(email, password)
             }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(STARTED) {
                viewModel.login.collect {
                    when (it) {
                        is Resource.Loading -> {
                            binding.buttonLoginLogin.startAnimation()
                        }
                        is Resource.Success -> {
                            binding.buttonLoginLogin.revertAnimation()
                            Intent(requireActivity(), ShoppingActivity::class.java).also {intent ->
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            binding.buttonLoginLogin.revertAnimation()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}
