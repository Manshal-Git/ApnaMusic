package com.manshal_khatri.apnamusic.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manshal_khatri.apnamusic.R
import com.manshal_khatri.apnamusic.databinding.FragmentSongsBinding
import com.manshal_khatri.apnamusic.viewmodel.SongViewModel

class SongsFragment : Fragment() {

    private lateinit var binding : FragmentSongsBinding
    lateinit var mVM : SongViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_songs, container, false)
        binding = FragmentSongsBinding.bind(view)

        with(mVM){
            getAllSongs()
            songsList.observe(viewLifecycleOwner){
                binding.tv.text = it.toString()
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