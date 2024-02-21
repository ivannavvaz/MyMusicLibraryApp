package com.inavarro.mibibliotecamusical.mainModule.SongsFragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Song
import com.inavarro.mibibliotecamusical.databinding.FragmentSongsBinding
import com.inavarro.mibibliotecamusical.mainModule.SongsFragment.adapters.OnClickListener
import com.inavarro.mibibliotecamusical.mainModule.SongsFragment.adapters.SongListAdapter
import com.inavarro.mibibliotecamusical.mainModule.SongsFragment.service.SongsService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SongsFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentSongsBinding
    private lateinit var mSongListAdapter: SongListAdapter

    private lateinit var mLinearlayout: LinearLayoutManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentSongsBinding.inflate(inflater, container, false)

        mBinding.btnBack.setOnClickListener {
            onDestroy()
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idPlaylist = arguments?.getLong(getString(R.string.arg_playlist_id), 0)

        setupRecyclerView()

        if (idPlaylist != null) {
            getPlaylist(idPlaylist)
            getSongs(idPlaylist)
        }


    }

    private fun setupRecyclerView() {
        mSongListAdapter = SongListAdapter(this)

        mLinearlayout = LinearLayoutManager(this.context)

        mBinding.rvSongs.apply {
            setHasFixedSize(true)
            layoutManager = mLinearlayout
            adapter = mSongListAdapter
        }
    }

    private fun getPlaylist(id: Long){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SongsService::class.java)

        lifecycleScope.launch {

            try {

                val result = service.getPlaylist(id)

                val playlist = result.body()!!

                mBinding.tvPlaylist.text = playlist.titulo
                mBinding.tvUser.text = playlist.usuario.username

            } catch (e: Exception) {
                Log.e("SET PLAYLIST ERROR", e.message.toString())
            }
        }
    }

    private fun getSongs(id: Long){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SongsService::class.java)

        lifecycleScope.launch {

            try {

                val result = service.getSongs(id) //Aquí va id

                val songs = result.body()!!

                Log.i("AAAAAAAAAAAA", songs.size.toString())

                mSongListAdapter.submitList(songs)
                mBinding.tvCanciones.text = songs.size.toString() + " canciones"

            } catch (e: Exception) {
                Log.e("SET PLAYLIST ERROR", e.message.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireFragmentManager().beginTransaction().remove((this as Fragment?)!!)
            .commitAllowingStateLoss()
    }
    override fun onClick(songEntity: Song) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(songEntity: Song) {
        val builder = AlertDialog.Builder(requireContext())
        //val inflater = requireActivity().layoutInflater


        val dialogView = layoutInflater.inflate(R.layout.dialog, null)

        //Log.println(Log.ASSERT, "LONG CLICK", "AAAAAAAAAAAAA")


        builder.setView(dialogView)

            .setPositiveButton("Eliminar"){ dialog, which ->
                deleteSong(songEntity.id)

            }
            .setNegativeButton("Cancelar"){ dialog, which ->

            }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun deleteSong(id: Long){

    }
}