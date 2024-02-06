package com.inavarro.mibibliotecamusical.authModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}