package com.inavarro.mibibliotecamusical.authModule.loginFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.UserApplication
import com.inavarro.mibibliotecamusical.authModule.loginFragment.services.LoginService
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserInfoEmail
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserInfoUsername
import com.inavarro.mibibliotecamusical.databinding.ActivityAuthBinding
import com.inavarro.mibibliotecamusical.databinding.FragmentLoginBinding
import com.inavarro.mibibliotecamusical.mainModule.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginFragment : Fragment() {

    private lateinit var mBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentLoginBinding.inflate(layoutInflater)


        mBinding.btnLogin.setOnClickListener {
            login()
        }

        return mBinding.root
    }

    private fun login() {
        val user = mBinding.etUser.text.toString().trim()
        val password = mBinding.etPassword.text.toString().trim()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(LoginService::class.java)

        lifecycleScope.launch {
            try {

                hideKeyboard()

                if (user.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Empty fields", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                if (user.contains("@")) {
                    val result = service.loginUserByEmail(UserInfoEmail(user, password))
                    val userLoged = result.body()!!
                    UserApplication.user = userLoged
                } else {
                    val result = service.loginUserByUsername(UserInfoUsername(user, password))
                    Log.i("ERROR SERVICE", result.toString())
                    val userLoged = result.body()!!
                    UserApplication.user = userLoged
                    Log.i("AUTH", userLoged.toString())
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Login success", Toast.LENGTH_SHORT).show()
                }

                // Change to HomeActivity with user info

                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)

            } catch (e: Exception) {
                Log.e("Error", e.toString())
                (e as? HttpException)?.let {
                    when(it.code()) {
                        400 -> {
                            Toast.makeText(requireContext(), "Error 400", Toast.LENGTH_SHORT).show()
                        }
                        else ->
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }


}