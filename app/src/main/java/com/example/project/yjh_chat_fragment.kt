package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore

class yjh_chat_fragment : Fragment() {

    private val chatItemList = arrayListOf<yjh_chat_item>()
    private var chatAdapter: yjh_chat_adapter = yjh_chat_adapter(chatItemList)

    private val db: FirebaseFirestore = com.google.firebase.ktx.Firebase.firestore
    private val itemCollectionRef = db.collection("message")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.yjh_chat_send, container, false)
        val buyer = Firebase.auth.currentUser?.email.toString()
        val seller = arguments?.getString("yjh_seller", "yjh_no_seller")
        val title = arguments?.getString("yjh_title", "yjh_no_title")

        val button = view.findViewById<Button>(R.id.yjh_send)
        button.setOnClickListener {
            val msg = view.findViewById<TextView>(R.id.yjh_text).text.toString()
            println("buyer : ${buyer}-seller : ${seller}-msg : ${msg}")
            val itemMap = hashMapOf(
                "buyer" to buyer,
                "seller" to seller,
                "msg" to msg,
                "title" to title
            )
            itemCollectionRef.add(itemMap)

            Snackbar.make(view, "메시지를 전송했습니다.", Snackbar.LENGTH_SHORT).show()

            val moveToMain = listFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, moveToMain)
            transaction.commit()
        }






        return view
    }
}