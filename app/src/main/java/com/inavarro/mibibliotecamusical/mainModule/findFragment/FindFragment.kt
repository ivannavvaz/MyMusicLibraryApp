package com.inavarro.mibibliotecamusical.mainModule.findFragment

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.common.entities.Album
import com.inavarro.mibibliotecamusical.common.entities.Playlist
import com.inavarro.mibibliotecamusical.common.entities.Podcast
import com.inavarro.mibibliotecamusical.common.entities.Song
import com.inavarro.mibibliotecamusical.databinding.FragmentFindBinding
import com.inavarro.mibibliotecamusical.mainModule.findFragment.adapters.ItemListAdapter
import com.inavarro.mibibliotecamusical.mainModule.findFragment.adapters.OnClickListener
import com.inavarro.mibibliotecamusical.mainModule.findFragment.bottomSheetDialogFragment.BottomSheetDialogAddToPlaylistFragment
import com.inavarro.mibibliotecamusical.mainModule.findFragment.services.FindService
import com.inavarro.mibibliotecamusical.mainModule.homeFragment.adapters.PlaylistListAdapter
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FindFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentFindBinding

    private lateinit var mListAdapter: ItemListAdapter
    private lateinit var mLinearlayout: LinearLayoutManager

    private lateinit var items: MutableList<Any>

    private lateinit var mPlaylistsListAdapter: PlaylistListAdapter

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

        setupRecyclerViews()

        getItems()

        setupSearchView()
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
        bundle.putLong("songId", songEntity.id)

        launchBottomSheetDialogFragment(bundle)
    }

    private fun getItems() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FindService::class.java)

        lifecycleScope.launch {

            try {
                val playlist: MutableList<Playlist>? = service.getPlaylists().body()
                val podcasts: MutableList<Podcast>? = service.getPodcasts().body()
                val songs: MutableList<Song>? = service.getSongs().body()
                val albums: MutableList<Album>? = service.getAlbums().body()

                items = mutableListOf<Any>()

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
                    it.toString().toLowerCase().contains(newText.toString().toLowerCase())
                }

                mBinding.rvFindFragment.scrollToPosition(0)

                mListAdapter.submitList(filteredList)
                return true
            }
        })
    }

    private fun launchBottomSheetDialogFragment(args: Bundle? = null) {
        val fragment = BottomSheetDialogAddToPlaylistFragment()

        if(args != null) fragment.arguments = args

        val fragmentManager = getFragmentManager()
        val fragmentTransaction = fragmentManager?.beginTransaction()
        if (fragmentTransaction != null) {
            fragmentTransaction.add(com.inavarro.mibibliotecamusical.R.id.flBottomSetDialog, fragment)
            fragmentTransaction.commit()

            fragmentTransaction.addToBackStack(null)
        }

        val navBar = requireActivity().findViewById<BottomNavigationView>(com.inavarro.mibibliotecamusical.R.id.bottomNav)
        navBar.visibility = View.GONE
    }
}