package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ItemChatBinding

class Chatitemadapter : ListAdapter<Chatitem, Chatitemadapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val senderTextView = view.findViewById<TextView>(R.id.senderTextView)
        val messageTextView = view.findViewById<TextView>(R.id.messageTextView)

        fun bind(chatItem: Chatitem) {
            senderTextView.text = chatItem.senderId
            messageTextView.text = chatItem.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Chatitem>() {
            override fun areItemsTheSame(oldItem: Chatitem, newItem: Chatitem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Chatitem, newItem: Chatitem): Boolean {
                return oldItem == newItem
            }
        }
    }
}