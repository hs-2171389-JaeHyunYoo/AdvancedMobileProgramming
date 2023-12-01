package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import kotlin.math.log

class yjh_chat_list : Fragment() {

    private val chatItemList = arrayListOf<yjh_chat_item>()
    private var chatAdapter: yjh_chat_adapter? = null

    private val db: FirebaseFirestore = Firebase.firestore
    private val itemCollectionRef = db.collection("message")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.yjh_chat_list, container, false)
        val logInWith = Firebase.auth.currentUser?.email.toString()

        // RecyclerView 설정
        val recyclerView = view.findViewById<RecyclerView>(R.id.yjh_chat_room_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Adapter 초기화
        chatAdapter = yjh_chat_adapter(chatItemList)
        recyclerView.adapter = chatAdapter

        // Firestore에서 데이터 가져오기
        itemCollectionRef.get().addOnSuccessListener { result ->
            chatItemList.clear()
            for (document in result) {
                val seller = document.getString("seller")?:"no-seller"
                val buyer = document.getString("buyer") ?: "no-buyer"
                val msg = document.getString("msg") ?: "no msg"
                val title = document.getString("title") ?: "no title"
                println("구매를 원하는 ${buyer}가 판매글을 올린 ${seller}에게 ${msg}라고 채팅 보냄")
                if (seller == logInWith) {
                    chatItemList.add(yjh_chat_item(buyer, msg, title))
                    //println("chatItemList 추가 완료")
                }
            }
            // 데이터를 가져온 후에 Adapter에 데이터가 변경되었음을 알립니다.
            chatAdapter?.notifyDataSetChanged()
        }

        return view
    }
}
