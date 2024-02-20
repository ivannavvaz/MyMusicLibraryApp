package com.inavarro.mibibliotecamusical.mainModule.NewPlaylistFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.inavarro.mibibliotecamusical.UserApplication
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.retrofit.dataclassRequest.playlist.PlaylistInfo
import com.inavarro.mibibliotecamusical.databinding.FragmentNewPlaylistBinding
import com.inavarro.mibibliotecamusical.mainModule.MainActivity
import com.inavarro.mibibliotecamusical.mainModule.NewPlaylistFragment.service.NewPlaylistService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewPlaylistFragment : Fragment() {

    private lateinit var mBinding: FragmentNewPlaylistBinding
    private var mActivity: MainActivity? = null

    private val RC_GALLERY = 18
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentNewPlaylistBinding.inflate(inflater, container, false)

        mActivity = activity as? MainActivity

        mBinding.btnBack.setOnClickListener {
            onDestroy()
        }

        mBinding.btnSave.setOnClickListener {
            createPlaylist()
        }

        mBinding.btnSelect.setOnClickListener {
            openGallery()
        }

        return mBinding.root
    }

    private fun createPlaylist(){

        val titulo = mBinding.etTitulo.text.toString().trim()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(NewPlaylistService::class.java)

        lifecycleScope.launch {
            try {

                if (titulo.isEmpty()) {
                    Toast.makeText(requireContext(), "El título es obligatorio", Toast.LENGTH_SHORT).show()
                    mBinding.etTitulo.requestFocus()

                    return@launch
                }

                Log.i("PLAYLIST INFO", PlaylistInfo(titulo).toString())

                val result = service.createPlaylist(UserApplication.user.id, PlaylistInfo(titulo))
                Log.i("PLAYLIST RESULT", result.toString())
                val playlist = result.body()!!


                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), playlist.titulo + " creada.", Toast.LENGTH_SHORT).show()
                }

                onDestroy()

            } catch (e: Exception) {
                Log.e("CREA PLAYLIST", e.toString() + e.cause)

            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, RC_GALLERY)
    }

    private fun hideKeyboard() {
        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
        requireFragmentManager().beginTransaction().remove((this as Fragment?)!!)
            .commitAllowingStateLoss()
    }
}