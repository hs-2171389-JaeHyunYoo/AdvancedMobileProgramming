package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.project.DBKey.Companion.DB_USERS
import com.example.project.DBKey.Companion.DB_ITEMS
import androidx.fragment.app.Fragment
import com.example.project.DBKey.Companion.CHILD_CHAT
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class selling_page : Fragment() {

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private lateinit var userDB: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.selling_page, container, false)

        val seller = arguments?.getString("seller", "no-seller")
        println("selling_page : ${seller}")
        val title = arguments?.getString("title", "")
        val explanation = arguments?.getString("explanation", "")
        val sellingItem = arguments?.getString("sellingItem", "")
        val price = arguments?.getInt("price", 0)
        val status = arguments?.getBoolean("status", false)




        view.findViewById<TextView>(R.id.sellingSeller).text = "판매자 : " + seller
        view.findViewById<TextView>(R.id.sellingTitle).text = "글 제목 : " + title
        view.findViewById<TextView>(R.id.sellingExplaination).text = "판매 설명 : " + explanation
        view.findViewById<TextView>(R.id.sellingItemName).text = "판매 물품 : " + sellingItem
        view.findViewById<TextView>(R.id.sellingPrice).text = "판매 가격 : " + price.toString()


        if(status == true){
            view.findViewById<TextView>(R.id.sellingPageStatus).text = "판매 상태 : 판매 중"
        }
        else{
            view.findViewById<TextView>(R.id.sellingPageStatus).text = "판매 상태 : 판매 완료"
        }

        println("${seller}-${title}-${explanation}-${sellingItem}-${price}-${status}#######################")

        val button = view.findViewById<Button>(R.id.sellingPageMessage)

        button.setOnClickListener{
            userDB = Firebase.database.reference.child(DB_USERS)


            val chatRoom = Chatlistitem(
                buyerId = auth.currentUser!!.uid,
                sellerId = seller ?: "",
                itemTitle = title ?: "",
                key = System.currentTimeMillis()
            )

            userDB.child(auth.currentUser!!.uid)
                .child(CHILD_CHAT)
                .push()
                .setValue(chatRoom)

            userDB.child(seller!!)
                .child(CHILD_CHAT)
                .push()
                .setValue(chatRoom)

            Snackbar.make(view, "채팅방이 생성되었습니다. 채팅탭에서 확인해주세요.", Snackbar.LENGTH_LONG).show()

        }

        return view
    }
}