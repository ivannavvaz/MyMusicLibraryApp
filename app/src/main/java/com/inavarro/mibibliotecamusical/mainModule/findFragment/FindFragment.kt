package com.inavarro.mibibliotecamusical.mainModule.findFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import com.inavarro.mibibliotecamusical.common.entities.Song
import com.inavarro.mibibliotecamusical.databinding.FragmentFindBinding
import com.inavarro.mibibliotecamusical.mainModule.MainActivity
import com.inavarro.mibibliotecamusical.mainModule.findFragment.adapters.ItemListAdapter
import com.inavarro.mibibliotecamusical.mainModule.findFragment.adapters.OnClickListener
import com.inavarro.mibibliotecamusical.mainModule.findFragment.services.FindService
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.HomeFragmentDirections
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.PlaylistListAdapter
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FindFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentFindBinding

    private lateinit var mListAdapter: ItemListAdapter
    private lateinit var mLinearlayout: LinearLayoutManager

    private lateinit var items: MutableList<Any>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        items = mutableListOf<Any>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentFindBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        items.clear()
        setupRecyclerViews()
        setupSearchView()
        getItems()
    }
    private fun setupRecyclerViews() {
        mListAdapter = ItemListAdapter(this)

        mLinearlayout = LinearLayoutManager(this.context)
        mLinearlayout.orientation = LinearLayoutManager.VERTICAL

        mBinding.rvFindFragment.apply {
            setHasFixedSize(true)
            layoutManager = mLinearlayout
            adapter = mListAdapter
        }
    }

    override fun onClick(songEntity: Song) {
        val bundle = Bundle()
        bundle.putLong("songIdSelected", songEntity.id)
        (activity as? MainActivity)?.showBottomSheetDialog(bundle)
    }

    override fun onClick(playlist: Playlist) {
        val action = FindFragmentDirections.actionFindFragmentToSongsFragment(false, playlist.id)
        findNavController().navigate(action)
    }

    override fun onClick(podcast: Podcast) {
        val action = FindFragmentDirections.actionFindFragmentToEpisodesFragment(podcast.id)
        findNavController().navigate(action)
    }

    override fun onClick(album: Album) {
        val action = FindFragmentDirections.actionFindFragmentToSongsFragment(true, album.id)
        findNavController().navigate(action)
    }


    private fun getItems() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FindService::class.java)

        lifecycleScope.launch {

            try {
                val playlist = service.getPlaylists().body()
                val podcasts = service.getPodcasts().body()
                val songs = service.getSongs().body()
                val albums = service.getAlbums().body()

                items.addAll(playlist!!)
                items.addAll(podcasts!!)
                items.addAll(songs!!)
                items.addAll(albums!!)

                items.sortBy { it.toString() }
                mListAdapter.submitList(items)

            } catch (e: Exception) {
                Log.e("FIND FRAGMENT GET ITEMS ERROR", e.message.toString())
            }
        }
    }

    private fun setupSearchView() {
        mBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = items.filter {
                    it.toString().lowercase().contains(newText.toString().lowercase())
                }

                mBinding.rvFindFragment.scrollToPosition(0)

                mListAdapter.submitList(filteredList)
                return true
            }
        })
    }
}