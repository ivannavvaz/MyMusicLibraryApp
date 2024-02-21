package com.inavarro.mibibliotecamusical.authModule.authFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.authModule.AuthActivity
import com.inavarro.mibibliotecamusical.databinding.FragmentAuthBinding
import com.inavarro.mibibliotecamusical.mainModule.MainActivity

class AuthFragment : Fragment() {

    private lateinit var mBinding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentAuthBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnLogin.setOnClickListener {
            // Change to login fragment
            findNavController().navigate(
                AuthFragmentDirections.actionAuthFragmentToLoginFragment()
            )
        }

        mBinding.btnGoogle.setOnClickListener {
            // Change to login fragment
            findNavController().navigate(
                AuthFragmentDirections.actionAuthFragmentToLoginFragment()
            )
        }

        mBinding.btnFacebook.setOnClickListener {
            // Change to login fragment
            findNavController().navigate(
                AuthFragmentDirections.actionAuthFragmentToLoginFragment()
            )
        }

        mBinding.btnTwitter.setOnClickListener {
            // Change to login fragment
            findNavController().navigate(
                AuthFragmentDirections.actionAuthFragmentToLoginFragment()
            )
        }

        mBinding.btnRegister.setOnClickListener {
            // Change to singin fragment
            findNavController().navigate(
                AuthFragmentDirections.actionAuthFragmentToSinginFragment()
            )
        }

        Glide.with(this)
            .load("https://www.freepnglogos.com/uploads/spotify-logo-png/spotify-icon-marilyn-scott-0.png")
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mBinding.ivLogo)
    }
}