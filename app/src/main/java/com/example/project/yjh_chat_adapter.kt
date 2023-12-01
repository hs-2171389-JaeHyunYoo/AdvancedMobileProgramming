package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class yjh_chat_adapter(val itemList: ArrayList<yjh_chat_item>):RecyclerView.Adapter<yjh_chat_adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.buyer.text = itemList[position].buyer
        holder.msg.text = itemList[position].msg
        holder.title.text = itemList[position].title
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val buyer = itemView.findViewById<TextView>(R.id.senderTextView)
        val msg = itemView.findViewById<TextView>(R.id.messageTextView)
        val title = itemView.findViewById<TextView>(R.id.titleTextView)
    }
}