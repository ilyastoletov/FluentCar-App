package com.appninjas.fluentcar.presentation.screens.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.appninjas.domain.model.User
import com.appninjas.fluentcar.R
import com.appninjas.fluentcar.databinding.FragmentRegistrationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel by viewModel<RegistrationViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {

        val registerSuccess: () -> Unit = {
            Toast.makeText(context, "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
        }

        val registerFailure: () -> Unit = {
            Toast.makeText(context, "Регистрация не удалась, из-за ошибки на стороне провайдера", Toast.LENGTH_SHORT).show()
        }

        binding.registerButton.setOnClickListener {
            if (validateCredentials()) {
                viewModel.registerUser(User(
                    phone = binding.phoneNumberRegistrationEditText.text.toString(),
                    name = binding.nameRegistrationEditText.text.toString(),
                    password = binding.passwordRegistrationEditText.text.toString(),
                    email = binding.emailRegistrationEditText.text.toString()
                ), registerSuccess, registerFailure)
            }
        }

        binding.loginFromRegistrationButton.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

    }

    private fun validateCredentials(): Boolean {
        val numberField = binding.phoneNumberRegistrationEditText.text
        val nameField = binding.nameRegistrationEditText.text
        val emailField = binding.emailRegistrationEditText.text
        val passwordField = binding.passwordRegistrationEditText.text
        val confirmPasswordField = binding.confirmPasswordEditText.text

        return if (numberField.isEmpty() || nameField.isEmpty() || emailField.isEmpty() || passwordField.isEmpty() || confirmPasswordField.isEmpty()) {
            Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            false
        } else if (passwordField != confirmPasswordField) {
            //Toast.makeText(context, "Пароли не совпадают $passwordField : $confirmPasswordField", Toast.LENGTH_SHORT).show()
            true
        } else {
            true
        }
    }

}