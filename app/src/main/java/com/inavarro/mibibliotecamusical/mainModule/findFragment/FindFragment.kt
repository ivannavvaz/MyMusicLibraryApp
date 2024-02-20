package com.inavarro.mibibliotecamusical.mainModule.findFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.inavarro.mibibliotecamusical.common.Constants
import com.inavarro.mibibliotecamusical.mainModule.findFragment.adapters.OnClickListener
import com.inavarro.mibibliotecamusical.databinding.FragmentFindBinding
import com.inavarro.mibibliotecamusical.mainModule.findFragment.adapters.ItemListAdapter
import com.inavarro.mibibliotecamusical.common.entities.Song
import com.inavarro.mibibliotecamusical.mainModule.findFragment.services.FindService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FindFragment : Fragment(), OnClickListener {

    private lateinit var mBinding: FragmentFindBinding

    private lateinit var mListAdapter: ItemListAdapter
    private lateinit var mLinearlayout: LinearLayoutManager

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
    }

    private fun setupRecyclerViews() {
        mListAdapter = ItemListAdapter(this)

        mLinearlayout = LinearLayoutManager(this.context)
        mLinearlayout.orientation = LinearLayoutManager.VERTICAL

        mBinding.rvPlaylists.apply {
            setHasFixedSize(true)
            layoutManager = mLinearlayout
            adapter = mListAdapter
        }
    }

    override fun onClick(songEntity: Song) {
        TODO("Not yet implemented")
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

                val items = mutableListOf<Any>()

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
}