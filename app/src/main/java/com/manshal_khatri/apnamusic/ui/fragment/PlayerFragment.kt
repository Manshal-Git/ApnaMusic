package com.manshal_khatri.apnamusic.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manshal_khatri.apnamusic.R
import com.manshal_khatri.apnamusic.databinding.FragmentPlayerBinding
import com.manshal_khatri.apnamusic.viewmodel.SongViewModel

class PlayerFragment : Fragment() {

    private lateinit var binding : FragmentPlayerBinding
    lateinit var mVM : SongViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_player, container, false)
        binding = FragmentPlayerBinding.bind(view)
        binding.tv.text = mVM.currentSong.value.toString()
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(vm : SongViewModel) =
            PlayerFragment().apply {
                arguments = Bundle().apply {
                    mVM = vm
                }
            }
    }
}