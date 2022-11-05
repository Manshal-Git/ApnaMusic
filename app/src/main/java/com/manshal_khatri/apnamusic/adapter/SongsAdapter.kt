package com.manshal_khatri.apnamusic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.apnamusic.databinding.ItemSongCardBinding
import com.manshal_khatri.apnamusic.viewmodel.SongViewModel

class SongsAdapter(val vm : SongViewModel) : RecyclerView.Adapter<SongsAdapter.ViewHolder>()  {
    val list = vm.songsList.value!!
    inner class ViewHolder(val binding : ItemSongCardBinding) : RecyclerView.ViewHolder(binding.root){
        val name = binding.tvName
        val card = binding.songCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSongCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = list[position]
        with(song){
            holder.name.text = name
            holder.card.setOnClickListener {
                vm.makeCurrentSong(song)
            }
        }
    }

    override fun getItemCount() : Int{
        return list.size
    }

}