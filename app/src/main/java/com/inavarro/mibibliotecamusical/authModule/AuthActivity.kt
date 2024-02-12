package com.inavarro.mibibliotecamusical.authModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.inavarro.mibibliotecamusical.authModule.services.LoginService
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserInfoEmail
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserInfoUsername
import com.inavarro.mibibliotecamusical.databinding.ActivityAuthBinding
import com.inavarro.mibibliotecamusical.mainModule.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnLogin.setOnClickListener {
            login()
        }

        mBinding.btnRegister.setOnClickListener {
            register()
        }
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

                if (user.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@AuthActivity, "Empty fields", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                if (user.contains("@")) {
                    val result = service.loginUserByEmail(UserInfoEmail(user, password))
                    val user = result.body()!!
                } else {
                    val result = service.loginUserByUsername(UserInfoUsername(user, password))
                    val user = result.body()!!
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AuthActivity, "Login success", Toast.LENGTH_SHORT).show()
                }

                // Change to HomeActivity with user info

                val intent = Intent(this@AuthActivity, MainActivity::class.java)
                startActivity(intent)

            } catch (e: Exception) {
                Log.e("Error", e.toString())
                (e as? HttpException)?.let {
                    when(it.code()) {
                        400 -> {
                            Toast.makeText(this@AuthActivity, "Error 400", Toast.LENGTH_SHORT).show()
                        }
                        else ->
                            Toast.makeText(this@AuthActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun register() {
        val user = mBinding.etUser.text.toString().trim()
        val password = mBinding.etPassword.text.toString().trim()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(LoginService::class.java)

        /*
        lifecycleScope.launch {
            try {
                val result = service.registerUser(UserInfo(email, password))
                updateUI("${Constants.TOKEN_PROPERTY}: ${result.token}")
            } catch (e: Exception) {
                (e as? HttpException)?.let {
                    when(it.code()) {
                        400 -> {
                            updateUI(getString(R.string.main_error_server))
                        }
                        else ->
                            updateUI(getString(R.string.main_error_response))
                    }
                }
            }
        }
         */

    }
}