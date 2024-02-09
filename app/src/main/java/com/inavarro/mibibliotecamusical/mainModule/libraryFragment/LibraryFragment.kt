package com.inavarro.mibibliotecamusical.mainModule.libraryFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {

    private lateinit var mBinding: FragmentLibraryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentLibraryBinding.inflate(inflater, container, false)
        return mBinding.root
    }
}