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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.inavarro.mibibliotecamusical.R
import com.inavarro.mibibliotecamusical.UserApplication
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Song
import com.inavarro.mibibliotecamusical.databinding.FragmentLibraryBinding
import com.inavarro.mibibliotecamusical.mainModule.NewPlaylistFragment.NewPlaylistFragment
import com.inavarro.mibibliotecamusical.mainModule.SongsFragment.SongsFragment
import com.inavarro.mibibliotecamusical.mainModule.libraryFragment.adapters.GridFormatPlaylistListAdapter
import com.inavarro.mibibliotecamusical.mainModule.libraryFragment.adapters.ListFormatPlaylistListAdapter
import com.inavarro.mibibliotecamusical.mainModule.libraryFragment.adapters.OnClickListener
import com.inavarro.mibibliotecamusical.mainModule.libraryFragment.service.LibraryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        mBinding.fabNewPlaylist.setOnClickListener {
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

        mGridlayoutPlaylist = GridLayoutManager(this.context, 2)

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
        val fragment = SongsFragment()
        val bundle = Bundle()
        bundle.putLong("playlistId", playlistEntity.id)
        fragment.arguments = bundle

        val fragmentManager = getFragmentManager()
        val fragmentTransaction = fragmentManager?.beginTransaction()
        if (fragmentTransaction != null) {
            fragmentTransaction.add(R.id.navHostFragment, fragment)
            fragmentTransaction.commit()

            fragmentTransaction.addToBackStack(null)
        }
    }

    override fun onLongClick(playlistEntity: Playlist) {
        val builder = AlertDialog.Builder(requireContext())
        //val inflater = requireActivity().layoutInflater


        val dialogView = layoutInflater.inflate(R.layout.dialog, null)

        Log.i("LONG CLICK", playlistEntity.id.toString())


        builder.setView(dialogView)

            .setPositiveButton("Eliminar"){ dialog, which ->
                deletePlaylist(playlistEntity.id)

            }
            .setNegativeButton("Cancelar"){ dialog, which ->

            }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun deletePlaylist(id: Long){
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

                mListFormatPlaylistListAdapter.notifyDataSetChanged()
                mGridFormatGridFormatPlaylistListAdapter.notifyDataSetChanged()

            } catch (e: Exception) {
                Log.e("DELETE PLAYLIST ERROR", e.message.toString())
            }
        }
    }

    private fun launchNewPlaylistFragment(args: Bundle? = null){
        val fragment = NewPlaylistFragment()

        if(args != null) fragment.arguments = args

        val fragmentManager = getFragmentManager()
        val fragmentTransaction = fragmentManager?.beginTransaction()
        if (fragmentTransaction != null) {
            fragmentTransaction.add(R.id.navHostFragment, fragment)
            fragmentTransaction.commit()

            fragmentTransaction.addToBackStack(null)
        }

    }
}