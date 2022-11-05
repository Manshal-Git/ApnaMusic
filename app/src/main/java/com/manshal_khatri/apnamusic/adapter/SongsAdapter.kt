package com.manshal_khatri.apnamusic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manshal_khatri.apnamusic.databinding.ItemSongCardBinding

class SongsAdapter() : RecyclerView.Adapter<SongsAdapter.ViewHolder>()  {

    inner class ViewHolder(binding : ItemSongCardBinding) : RecyclerView.ViewHolder(binding.root){

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

    }

    override fun getItemCount() : Int{
        return 0
    }

}