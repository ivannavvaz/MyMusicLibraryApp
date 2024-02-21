package com.inavarro.mibibliotecamusical.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.databinding.ActivityMainBinding
import com.inavarro.mibibliotecamusical.mainModule.findFragment.FindFragment
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.HomeFragment
import com.inavarro.mibibliotecamusical.mainModule.libraryFragment.LibraryFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mActiveFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupBottomNav()

        //val navController = findNavController(R.id.hostFragment)
        //val appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.hostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupBottomNav() {
        mFragmentManager = supportFragmentManager

        val homeFragment = HomeFragment()
        val findFragment = FindFragment()
        val libraryFragment = LibraryFragment()

        mActiveFragment = homeFragment

        mFragmentManager.beginTransaction()
            .add(
                R.id.hostFragment, libraryFragment,
                LibraryFragment::class.java.name)
            .hide(libraryFragment).commit()

        mFragmentManager.beginTransaction()
            .add(
                R.id.hostFragment, findFragment,
                FindFragment::class.java.name)
            .hide(findFragment).commit()

        mFragmentManager.beginTransaction()
            .add(
                R.id.hostFragment, homeFragment,
                HomeFragment::class.java.name).commit()

        mBinding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.action_home -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(homeFragment).commit()
                    mActiveFragment = homeFragment
                    true
                }
                R.id.action_add -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(findFragment).commit()
                    mActiveFragment = findFragment
                    true
                }
                R.id.action_profile -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(libraryFragment).commit()
                    mActiveFragment = libraryFragment
                    true
                }
                else -> false
            }
        }
    }
}