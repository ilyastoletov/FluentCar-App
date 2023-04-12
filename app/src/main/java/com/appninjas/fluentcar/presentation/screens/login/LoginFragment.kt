package com.appninjas.fluentcar.presentation.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.appninjas.domain.model.UserCredentials
import com.appninjas.fluentcar.R
import com.appninjas.fluentcar.databinding.FragmentLoginBinding
import com.appninjas.fluentcar.presentation.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {

        val loginSuccess: () -> Unit = {
            Toast.makeText(context, "Успешный вход", Toast.LENGTH_SHORT).show()
            with(requireActivity()) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                finish()
            }
        }

        val loginFailure: () -> Unit = {
            Toast.makeText(context, "Вход не удался. Неправильный логин или пароль, или вы не зарегистрированы", Toast.LENGTH_SHORT).show()
        }

        binding.loginButton.setOnClickListener {
            viewModel.loginUser(UserCredentials(email = binding.emailLoginEdittext.text.toString(),
                password = binding.passwordLoginEdittext.text.toString()), loginSuccess, loginFailure)
        }

        binding.navigateToRegistration.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }

    }

}