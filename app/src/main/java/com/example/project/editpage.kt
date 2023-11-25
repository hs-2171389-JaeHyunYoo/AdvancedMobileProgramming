package com.example.project

import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class edit : Fragment() {

    private val db = Firebase.firestore
    private val itemCollectionRef = db.collection("items")
    lateinit var currentItemID: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit, container, false)

        val editTitle = view.findViewById<EditText>(R.id.editsellingTitle)
        val editExplanation = view.findViewById<EditText>(R.id.editsellingExplaination)
        val editSellingItem = view.findViewById<EditText>(R.id.editsellingItemName)
        val editPrice = view.findViewById<EditText>(R.id.editsellingPrice)
        val editStatus = view.findViewById<EditText>(R.id.editsellingPageStatus)
        val update = view.findViewById<Button>(R.id.update)

        val title = arguments?.getString("title", "")
        val explanation = arguments?.getString("explanation", "")
        val sellingItem = arguments?.getString("sellingItem", "")
        val price = arguments?.getInt("price", 0)
        val status = arguments?.getBoolean("status", false)

        view.findViewById<EditText>(R.id.editsellingTitle).setText("$title")
        view.findViewById<EditText>(R.id.editsellingExplaination).setText("$explanation")
        view.findViewById<EditText>(R.id.editsellingItemName).setText("$sellingItem")
        view.findViewById<EditText>(R.id.editsellingPrice).setText("$price")
        view.findViewById<EditText>(R.id.editsellingPageStatus).setText("${if (status == true) "판매중" else "판매 완료"}")

        update.setOnClickListener {
            val updatedTitle = editTitle.text.toString()
            val updatedExplanation = editExplanation.text.toString()
            val updatedSellingItem = editSellingItem.text.toString()
            val updatedPrice = editPrice.text.toString().toInt()
            val updatedStatus = editStatus.text.toString().toBoolean()

            itemCollectionRef.document(currentItemID).update(
                mapOf(
                    "title" to updatedTitle,
                    "explanation" to updatedExplanation,
                    "sellingItem" to updatedSellingItem,
                    "price" to updatedPrice,
                    "status" to updatedStatus
                )
            )
            val listPage = list()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, listPage)
            transaction.commit()


        }

        return view
    }
}