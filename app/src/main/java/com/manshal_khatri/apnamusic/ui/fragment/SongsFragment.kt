package com.manshal_khatri.apnamusic.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.manshal_khatri.apnamusic.R
import com.manshal_khatri.apnamusic.adapter.SongsAdapter
import com.manshal_khatri.apnamusic.databinding.FragmentSongsBinding
import com.manshal_khatri.apnamusic.util.Functions
import com.manshal_khatri.apnamusic.viewmodel.SongViewModel

class SongsFragment : Fragment() {

    private lateinit var binding : FragmentSongsBinding
    lateinit var mVM : SongViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mVM.getAllSongs()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_songs, container, false)
        binding = FragmentSongsBinding.bind(view)
        with(binding) {
            rvSongs.layoutManager = LinearLayoutManager(activity)
            with(mVM) {
                songsList.observe(viewLifecycleOwner) {
                    if(it.isNotEmpty()){
                        rvSongs.adapter = SongsAdapter(mVM)
                        Functions.makeViewGone(loadingScreen)
                    }
                }
            }
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(vm : SongViewModel) =
            SongsFragment().apply {
                arguments = Bundle().apply {
                    mVM = vm
                }
            }
    }
}