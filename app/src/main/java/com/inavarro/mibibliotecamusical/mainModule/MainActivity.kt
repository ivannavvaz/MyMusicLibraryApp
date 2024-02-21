package com.inavarro.mibibliotecamusical.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.authModule.authFragment.AuthFragment
import com.inavarro.mibibliotecamusical.authModule.loginFragment.LoginFragment
import com.inavarro.mibibliotecamusical.authModule.singinFragment.SinginFragment
import com.inavarro.mibibliotecamusical.databinding.ActivityMainBinding
import com.inavarro.mibibliotecamusical.mainModule.findFragment.FindFragment
import com.inavarro.mibibliotecamusical.mainModule.findFragment.bottomSheetDialogFragment.BottomSheetDialogAddToPlaylistFragment
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.HomeFragment
import com.inavarro.mibibliotecamusical.mainModule.libraryFragment.LibraryFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mFragmentManager: FragmentManager

    private val bottomSheetDialog = BottomSheetDialogAddToPlaylistFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mFragmentManager = supportFragmentManager

        mFragmentManager.beginTransaction()
            .add(
                R.id.navHostFragment, bottomSheetDialog,
                BottomSheetDialogAddToPlaylistFragment::class.java.name)
            .hide(bottomSheetDialog).commit()
        //setupBottomNav()

        val hostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = hostFragment.navController
        mBinding.bottomNav.setupWithNavController(navController)
    }

    fun showBottomSheetDialog(bundle: Bundle) {

        // Set the bundle arguments to the bottomSheetDialog
        bottomSheetDialog.arguments = bundle
        mFragmentManager.beginTransaction().show(bottomSheetDialog).commit()
        mBinding.bottomNav.visibility = View.GONE
    }

    fun hideBottomSheetDialog() {
        mFragmentManager.beginTransaction().hide(bottomSheetDialog).commit()
        mBinding.bottomNav.visibility = View.VISIBLE
    }


}