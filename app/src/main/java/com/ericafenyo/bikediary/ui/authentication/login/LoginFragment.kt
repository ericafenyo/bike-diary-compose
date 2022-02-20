/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2021 Eric Afenyo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ericafenyo.bikediary.ui.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ericafenyo.bikediary.databinding.FragmentLoginBinding
import com.ericafenyo.bikediary.theme.AppTheme
import com.ericafenyo.bikediary.ui.screens.auth.login.Login
import com.ericafenyo.bikediary.ui.screens.auth.login.LoginAction
import com.ericafenyo.bikediary.util.Validator
import com.wada811.databinding.dataBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
  private val binding: FragmentLoginBinding by dataBinding()
  private val viewModel: LoginViewModel by viewModels()
  private val inputValidator by lazy { Validator(requireContext()) }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    return ComposeView(requireContext()).apply {
      setContent {
        AppTheme {
          Login(
            onAction = { action -> handleAction(action) },
            onNavigateUp = { requireActivity().onBackPressed() }
          )
        }
      }
    }
  }

  private fun handleAction(action: LoginAction) {
    when (action) {
      is LoginAction.Authenticate -> viewModel.authenticate(action.email, action.password)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.state.collect { state ->
          if (state.success) {
            requireActivity().onBackPressed()
          }
        }
      }
    }
  }

//  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//    binding.lifecycleOwner = viewLifecycleOwner
//    binding.model = viewModel
//
//    viewModel.message.observe(viewLifecycleOwner) { message ->
//      if (message != null) {
//        Alert.Builder(requireContext())
//          .from(message)
//          .build()
//          .show(this)
//      }
//    }
//
//    viewModel.events.observe(viewLifecycleOwner, EventObserver { action ->
//      when (action) {
//        LoginAction.NAVIGATE_UP -> requireActivity().onBackPressed()
//      }
//    })

//    viewModel.state.observe(viewLifecycleOwner) { state ->
//      if (state != null && state.success) {
//        requireActivity().onBackPressed()
//      }
//    }

  // Clear input errors when the user is typing
//    clearInputErrorsOnChange()
//
//    // Listen and handle sign in button press
//    viewModel.launchLoginAction.observe(viewLifecycleOwner, EventObserver {
//      onSubmit()
//    })
//  }

//  private fun clearInputErrorsOnChange() {
//    binding.editTextEmail.doAfterTextChanged {
//      binding.textFieldEmail.error = null
//    }
//
//    binding.editTextPassword.doAfterTextChanged {
//      binding.textFieldPassword.error = null
//    }
//  }

//  private fun onSubmit() = with(binding) {
//    // Extract the texts from the inputs.
//    val email = editTextEmail.text?.toString() ?: ""
//    val password = editTextPassword.text?.toString() ?: ""
//
//    // Validate inputs and notify users about the errors.
//    val hasValidInputs = validateInputs(email, password)
//
//    // Submit the form only if all inputs are valid.
//    if (hasValidInputs) {
//      submit(email, password)
//    }
//  }

//  private fun submit(email: String, password: String) {
//    viewModel.authenticate(email, password)
//  }

//  private fun validateInputs(email: String, password: String): Boolean = with(binding) {
//    // Validate required email
//    inputValidator.isFieldNotEmpty(email, textFieldEmail)
//      // Validate valid email
//      .doOnTrue { inputValidator.isEmailValid(email, textFieldEmail) }
//      // Validate required password
//      .doOnTrue { inputValidator.isFieldNotEmpty(password, textFieldPassword) }
//  }
}
