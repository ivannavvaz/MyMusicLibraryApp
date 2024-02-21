package com.inavarro.mibibliotecamusical.mainModule.findFragment.bottomSheetDialogFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.UserApplication
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.databinding.FragmentBottomSheetDialogAddToPlaylistBinding
import com.inavarro.mibibliotecamusical.mainModule.MainActivity
import com.inavarro.mibibliotecamusical.mainModule.findFragment.bottomSheetDialogFragment.adapters.OnClickListener
import com.inavarro.mibibliotecamusical.mainModule.findFragment.bottomSheetDialogFragment.adapters.PlaylistListAdapter
import com.inavarro.mibibliotecamusical.mainModule.findFragment.bottomSheetDialogFragment.services.BottomSheetDialogService
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.services.HomeService
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BottomSheetDialogAddToPlaylistFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentBottomSheetDialogAddToPlaylistBinding

    private lateinit var mListAdapter: PlaylistListAdapter
    private lateinit var mLinearlayout: LinearLayoutManager

    private var idSongSelected: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentBottomSheetDialogAddToPlaylistBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.idViewTop.setOnClickListener {
            (activity as? MainActivity)?.hideBottomSheetDialog()
        }

        mBinding.idBtnDismiss.setOnClickListener {
            (activity as? MainActivity)?.hideBottomSheetDialog()
        }

        setupRecyclerViews()

        getPlaylistUser()
    }

    override fun onResume() {
        super.onResume()
        if (arguments != null) {
            idSongSelected = arguments?.getLong(getString(R.string.arg_songSelected_id), 0)!!
            Log.d("BottomSheetDialog", "onViewCreated: $idSongSelected")
        }

    }

    private fun setupRecyclerViews() {
        mListAdapter = PlaylistListAdapter(this)

        mLinearlayout = LinearLayoutManager(this.context)
        mLinearlayout.orientation = LinearLayoutManager.VERTICAL

        mBinding.rvPlaylists.apply {
            setHasFixedSize(true)
            layoutManager = mLinearlayout
            adapter = mListAdapter
        }
    }

    private fun getPlaylistUser() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(HomeService::class.java)

        lifecycleScope.launch {

            try {
                val result = service.getPlaylistUser(UserApplication.user.id) // Aquí debería ir el id del usuario

                val playlists = result.body()!!

                mListAdapter.submitList(playlists)

            } catch (e: Exception) {
                Log.e("SET PLAYLIST ERROR", e.message.toString())
            }
        }
    }

    override fun onClick(playlistEntity: Playlist) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(BottomSheetDialogService::class.java)

        lifecycleScope.launch {

            try {
                val result = service.postPlaylistUser(
                    playlistEntity.id,
                    idSongSelected,
                    UserApplication.user.id
                )

                Toast.makeText(
                    context,
                    "Canción añadida a la lista de reproducción",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "ERROR: Cancion ya añadida a la lista de reproducción",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val navBar = requireActivity().findViewById<BottomNavigationView>(com.inavarro.mibibliotecamusical.R.id.bottomNav)
        navBar.visibility = View.VISIBLE

        requireFragmentManager().beginTransaction().remove((this as Fragment?)!!)
            .commitAllowingStateLoss()
    }
}