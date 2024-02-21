package com.inavarro.mibibliotecamusical.authModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.authModule.authFragment.AuthFragment
import com.inavarro.mibibliotecamusical.authModule.loginFragment.LoginFragment
import com.inavarro.mibibliotecamusical.authModule.singinFragment.SinginFragment
import com.inavarro.mibibliotecamusical.databinding.ActivityAuthBinding

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

        val hostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragmentAuth) as NavHostFragment
    }
}