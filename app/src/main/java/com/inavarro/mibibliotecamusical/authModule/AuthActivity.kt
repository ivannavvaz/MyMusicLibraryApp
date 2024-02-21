package com.inavarro.mibibliotecamusical.authModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.UserApplication
import com.inavarro.mibibliotecamusical.authModule.authFragment.AuthFragment
import com.inavarro.mibibliotecamusical.authModule.loginFragment.LoginFragment
import com.inavarro.mibibliotecamusical.authModule.loginFragment.services.LoginService
import com.inavarro.mibibliotecamusical.authModule.singinFragment.SinginFragment
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserInfoEmail
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.user.UserInfoUsername
import com.inavarro.mibibliotecamusical.databinding.ActivityAuthBinding
import com.inavarro.mibibliotecamusical.mainModule.MainActivity
import com.inavarro.mibibliotecamusical.mainModule.SongsFragment.SongsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAuthBinding
    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mActiveFragment: Fragment

    private val loginFragment = LoginFragment()
    private val singinFragment = SinginFragment()
    private val authFragment = AuthFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mFragmentManager = supportFragmentManager
        mActiveFragment = authFragment

        mFragmentManager.beginTransaction()
            .add(
                R.id.frameLayout, authFragment,
                AuthFragment::class.java.name)
            .commit()

        mFragmentManager.beginTransaction()
            .add(
                R.id.frameLayout, singinFragment,
                SinginFragment::class.java.name)
            .hide(singinFragment).commit()

        mFragmentManager.beginTransaction()
            .add(
                R.id.frameLayout, loginFragment,
                LoginFragment::class.java.name)
            .hide(loginFragment).commit()


    }

    fun toLoginFragment() {
        mFragmentManager.beginTransaction().hide(mActiveFragment).show(loginFragment).commit()
        mActiveFragment = loginFragment
    }

    fun toSinginFragment() {
        mFragmentManager.beginTransaction().hide(mActiveFragment).show(singinFragment).commit()
        mActiveFragment = singinFragment
    }


}