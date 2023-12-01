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
import com.google.firebase.auth.auth
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

        val buyer = Firebase.auth.currentUser?.email.toString()

        val seller = arguments?.getString("seller", "no-seller")
        //println("selling_page : ${seller}")
        val title = arguments?.getString("title", "")
        val explanation = arguments?.getString("explanation", "")
        val sellingItem = arguments?.getString("sellingItem", "")
        val price = arguments?.getInt("price", 0)
        val status = arguments?.getBoolean("status", false)


        view.findViewById<TextView>(R.id.sellingSeller).text = seller
        view.findViewById<TextView>(R.id.sellingTitle).text = title
        view.findViewById<TextView>(R.id.sellingExplaination).text = explanation
        view.findViewById<TextView>(R.id.sellingItemName).text = sellingItem
        view.findViewById<TextView>(R.id.sellingPrice).text = price.toString()+"원"


        if(status == true){
            view.findViewById<TextView>(R.id.sellingPageStatus).text = "판매 중"
        }
        else{
            view.findViewById<TextView>(R.id.sellingPageStatus).text = "판매 완료"
        }

        println("${title}-${explanation}-${sellingItem}-${price}-${status}#######################")


        //메시지 보내기 버튼 클릭
        val button = view.findViewById<Button>(R.id.sellingPageMessage)

        button.setOnClickListener{
            println("판매자 : ${seller} 구매자 : ${buyer}")

            val bundle = Bundle()
            bundle.putString("yjh_seller", seller)
            bundle.putString("yjh_title", title)
            //bundle.putString("yjh_buyer", buyer)


            //채팅 창으로 이동
            val moveToSend = yjh_chat_fragment()
            moveToSend.arguments = bundle

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, moveToSend)
            transaction.commit()

            /*userDB = Firebase.database.reference.child(DB_USERS)


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


            val sanitizedSeller = seller!!.replace(".", "_")
                .replace("#", "_")
                .replace("$", "_")
                .replace("[", "_")
                .replace("]", "_")
                .replace("@","_")
            userDB.child(sanitizedSeller)
                .child(CHILD_CHAT)
                .push()
                .setValue(chatRoom)

            Snackbar.make(view, "채팅방이 생성되었습니다. 채팅탭에서 확인해주세요.", Snackbar.LENGTH_LONG).show()*/

        }

        return view
    }
}