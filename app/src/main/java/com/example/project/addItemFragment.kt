package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class addItemFragment : Fragment() {

    private val itemList = arrayListOf<item>()
    private var adapter: adapter = adapter(itemList)
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemCollectionRef = db.collection("items")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.addingitem, container, false)

        val logInWith = Firebase.auth.currentUser?.email.toString()
        println(logInWith)

        val itemAddBtn = view.findViewById<Button>(R.id.addItemButton)
        itemAddBtn.setOnClickListener {
            val title = view.findViewById<TextView>(R.id.addItemTitle).text.toString()
            val explaination = view.findViewById<TextView>(R.id.addItemExplaination).text.toString()
            val sellingItem = view.findViewById<TextView>(R.id.sellingItem).text.toString()
            val price = view.findViewById<TextView>(R.id.itemPrice).text.toString().toInt()
            val itemStatus = view.findViewById<Switch>(R.id.sellOrNot).isChecked

            val itemMap = hashMapOf(
                "seller" to logInWith,
                "title" to title,
                "explaination" to explaination,
                "sellingItem" to sellingItem,
                "price" to price,
                "status" to itemStatus
            )
            println("addItemFragment : ${logInWith}-${title}-${sellingItem}-${price}-${itemStatus}-${explaination}")

            // 파이어베이스에 추가
            itemCollectionRef.add(itemMap)

            adapter.notifyDataSetChanged()

            //아이템 추가 후 자동으로 리스트로 이동
            //백 스택 쌓기 불 필요
            val moveToList = listFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, moveToList)
            transaction.commit()

        }

        return view
    }
}