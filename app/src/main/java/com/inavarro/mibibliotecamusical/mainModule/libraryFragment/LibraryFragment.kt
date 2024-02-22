package com.inavarro.mibibliotecamusical.mainModule.libraryFragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.inavarro.mibibliotecamusical.UserApplication
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.databinding.FragmentLibraryBinding
import com.inavarro.mibibliotecamusical.mainModule.libraryFragment.adapters.GridFormatPlaylistListAdapter
import com.inavarro.mibibliotecamusical.mainModule.libraryFragment.adapters.ListFormatPlaylistListAdapter
import com.inavarro.mibibliotecamusical.mainModule.libraryFragment.adapters.OnClickListener
import com.inavarro.mibibliotecamusical.mainModule.libraryFragment.service.LibraryService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LibraryFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentLibraryBinding

    private lateinit var mListFormatPlaylistListAdapter: ListFormatPlaylistListAdapter
    private lateinit var mGridFormatGridFormatPlaylistListAdapter: GridFormatPlaylistListAdapter

    private lateinit var mLinearlayoutPlaylist: LinearLayoutManager
    private lateinit var mGridlayoutPlaylist: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentLibraryBinding.inflate(inflater, container, false)

        mBinding.cbFormat.setOnClickListener {

            if (mBinding.rvGridFormat.visibility == GONE)
                mBinding.rvGridFormat.visibility = VISIBLE
            else
                mBinding.rvGridFormat.visibility = GONE


            if (mBinding.rvListFormat.visibility == GONE)
                mBinding.rvListFormat.visibility = VISIBLE
            else
                mBinding.rvListFormat.visibility = GONE

        }



        mBinding.bNewPlaylist.setOnClickListener {
            launchNewPlaylistFragment()
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rvGridFormat.visibility = GONE
        setupRecyclerView()
        getPlaylists()
    }


    private fun setupRecyclerView(){
        mListFormatPlaylistListAdapter = ListFormatPlaylistListAdapter(this)

        mLinearlayoutPlaylist = LinearLayoutManager(this.context)

        mBinding.rvListFormat.apply {
            setHasFixedSize(true)
            layoutManager = mLinearlayoutPlaylist
            adapter = mListFormatPlaylistListAdapter
        }

        mGridFormatGridFormatPlaylistListAdapter = GridFormatPlaylistListAdapter(this)

        mGridlayoutPlaylist = GridLayoutManager(this.context, 3)

        mBinding.rvGridFormat.apply {
            setHasFixedSize(true)
            layoutManager = mGridlayoutPlaylist
            adapter = mGridFormatGridFormatPlaylistListAdapter
        }
    }

    private fun getPlaylists(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(LibraryService::class.java)

        lifecycleScope.launch {

            try {
                val result = service.getPlaylistUser(UserApplication.user.id)

                val playlists = result.body()!!

                mListFormatPlaylistListAdapter.submitList(playlists)
                mGridFormatGridFormatPlaylistListAdapter.submitList(playlists)

            } catch (e: Exception) {
                Log.e("SET PLAYLIST ERROR", e.message.toString())
            }
        }
    }

    override fun onClick(playlistEntity: Playlist) {
        findNavController().navigate(
            LibraryFragmentDirections.actionLibraryFragmentToSongsFragment(false, playlistEntity.id)
        )
    }

    override fun onLongClick(playlistEntity: Playlist, position: Int) {

        val builder = AlertDialog.Builder(requireContext())

        var titulo = playlistEntity.titulo
        var tipo = "Playlist • " + playlistEntity.usuario.username

        if (titulo != "favorita_1") {
            titulo = titulo.replace("lista_", "")
            titulo = titulo.replace("_", " ")
            titulo = titulo[0].uppercase() + titulo.substring(1)
        } else {
            titulo = "Canciones que te gustan"
            tipo = "Playlist • " + playlistEntity.numeroCanciones + " canciones"
        }

        builder.setTitle("Eliminar La Playlist $titulo?")
        builder.setMessage("¿Estás seguro de que quieres eliminar esta playlist?")
        builder.setPositiveButton("Sí") { _, _ ->
            deletePlaylist(playlistEntity.id, position)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun deletePlaylist(id: Long, position: Int){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(LibraryService::class.java)

        lifecycleScope.launch {

            try {

                val result = service.deletePlaylist(UserApplication.user.id, id)

                Log.i("DELETE RESULT", result.toString())

                Toast.makeText(requireContext(), "Playlist eliminada", Toast.LENGTH_SHORT).show()

                getPlaylists()

            } catch (e: Exception) {
                Log.e("DELETE PLAYLIST ERROR", e.message.toString())
            }
        }
    }

    private fun launchNewPlaylistFragment() {
        findNavController().navigate(
            LibraryFragmentDirections.actionLibraryFragmentToNewPlaylistFragment()
        )
    }
}