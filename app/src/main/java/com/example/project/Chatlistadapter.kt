package com.example.project

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class Chatlistadapter(val onItemClicked: (Chatlistitem) -> Unit) : ListAdapter<Chatlistitem, Chatlistadapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(chatListItem: Chatlistitem) {
            itemView.setOnClickListener {
                onItemClicked(chatListItem)
            }

            itemView.findViewById<TextView>(R.id.chatRoomTitleTextView).text = chatListItem.itemTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Chatlistitem>() {
            override fun areItemsTheSame(oldItem: Chatlistitem, newItem: Chatlistitem): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: Chatlistitem, newItem: Chatlistitem): Boolean {
                return oldItem == newItem
            }
        }
    }
}