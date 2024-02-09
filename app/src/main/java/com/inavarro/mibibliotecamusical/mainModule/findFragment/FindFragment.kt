package com.inavarro.mibibliotecamusical.mainModule.findFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.databinding.FragmentFindBinding

class FindFragment : Fragment() {

    private lateinit var mBinding: FragmentFindBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentFindBinding.inflate(inflater, container, false)
        return mBinding.root
    }
}