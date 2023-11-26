package com.example.project

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class ChatListAdapter(val onItemClicked: (Chatlistitem) -> Unit) : ListAdapter<Chatlistitem, ChatListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(Chatlistitem: Chatlistitem) {
            view.setOnClickListener {
                onItemClicked(Chatlistitem)
            }

            view.findViewById<TextView>(R.id.chatRoomTitleTextView).text = Chatlistitem.itemTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Chatlistitem>() {
            override fun areItemsTheSame(oldItem: Chatlistitem, newItem: Chatlistitem): Boolean {
                return oldItem.key == newItem.key
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Chatlistitem, newItem: Chatlistitem): Boolean {
                return oldItem == newItem
            }
        }
    }
}