package com.inavarro.mibibliotecamusical.mainModule.songsFragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Song
import com.inavarro.mibibliotecamusical.databinding.FragmentSongsBinding
import com.inavarro.mibibliotecamusical.mainModule.songsFragment.adapters.OnClickListener
import com.inavarro.mibibliotecamusical.mainModule.songsFragment.adapters.SongListAdapter
import com.inavarro.mibibliotecamusical.mainModule.songsFragment.service.SongsService
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

        mBinding.cbBack.setOnClickListener {
            findNavController().navigate(
                SongsFragmentDirections.actionSongsFragmentToLibraryFragment()
            )
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idPlaylist = arguments?.getLong("idSong")

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

                var titulo = playlist.titulo
                var image = Constants.DEFAULT_PLAYLIST_IMAGE

                // Replace "_" with " " in the title of the playlist if it is not the favorite playlist
                // If it is the favorite playlist, the title is "Canciones que te gustan"
                if (titulo != "favorita_1") {
                    titulo = titulo.replace("lista_", "")
                    titulo = titulo.replace("_", " ")
                    titulo = titulo[0].uppercase() + titulo.substring(1)
                } else {
                    image = Constants.FAVORITE_PLAYLIST_IMAGE
                    titulo = "Canciones que te gustan"
                }

                mBinding.tvPlaylist.text = titulo
                mBinding.tvUser.text = playlist.usuario.username

                context?.let {
                    Glide.with(it)
                        .load(image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(mBinding.ivPlaylistSf)
                }

            } catch (e: Exception) {
                Log.e("SET PLAYLIST ERROR", e.message.toString())
            }
        }
    }

    @SuppressLint("SetTextI18n")
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

                mSongListAdapter.submitList(songs)
                mBinding.tvCanciones.text = "${songs.size} canciones"

            } catch (e: Exception) {
                Log.e("SET PLAYLIST ERROR", e.message.toString())
            }
        }
    }

    override fun onClick(songEntity: Song) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(songEntity: Song) {
        val builder = AlertDialog.Builder(requireContext())

        val dialogView = layoutInflater.inflate(R.layout.dialog, null)

        builder.setView(dialogView)

            .setPositiveButton("Eliminar"){ _, _ ->
                deleteSong(songEntity.id)

            }
            .setNegativeButton("Cancelar"){ _, _ ->

            }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun deleteSong(id: Long){

    }
}