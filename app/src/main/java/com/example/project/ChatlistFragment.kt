package com.example.project

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.DBKey.Companion.CHILD_CHAT
import com.example.project.DBKey.Companion.DB_USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatlistFragment : Fragment(R.layout.fragment_chatlist) {

    private lateinit var chatListAdapter: ChatListAdapter
    private val chatRoomList = mutableListOf<Chatlistitem>()

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatListAdapter = ChatListAdapter(onItemClicked = { chatRoom ->
            // 채팅방으로 이동 하는 코드
            context?.let {
                val intent = Intent(it, Chatroomactivity::class.java)
                intent.putExtra("chatKey", chatRoom.key)
                startActivity(intent)
            }
        })

        chatRoomList.clear()

        val chatListRecyclerView = view.findViewById<RecyclerView>(R.id.chatListRecyclerView)
        chatListRecyclerView.adapter = chatListAdapter
        chatListRecyclerView.layoutManager = LinearLayoutManager(context)

        if (auth.currentUser == null) {
            return
        }

        val chatDB = Firebase.database.reference.child(DB_USERS).child(auth.currentUser!!.uid).child(CHILD_CHAT)

        chatDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val buyerId = it.child("buyerId").getValue(String::class.java)
                    val sellerId = it.child("sellerId").getValue(String::class.java)
                    val itemTitle = it.child("itemTitle").getValue(String::class.java)
                    val key = it.child("key").getValue(Long::class.java)

                    if (buyerId != null && sellerId != null && itemTitle != null && key != null) {
                        val model = Chatlistitem(buyerId, sellerId, itemTitle, key)
                        chatRoomList.add(model)
                    }
                }

                chatListAdapter.submitList(chatRoomList)
                chatListAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    override fun onResume() {
        super.onResume()
        chatListAdapter.notifyDataSetChanged()
    }
}