package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
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
        val editExplaination = view.findViewById<EditText>(R.id.editsellingExplaination)
        val editSellingItem = view.findViewById<EditText>(R.id.editsellingItemName)
        val editPrice = view.findViewById<EditText>(R.id.editsellingPrice)
        val editStatus = view.findViewById<EditText>(R.id.editsellingPageStatus)
        val update = view.findViewById<Button>(R.id.update)
        //YJH
        val switch = view.findViewById<Switch>(R.id.statusSwitch)

        val title = arguments?.getString("title", "default-title")
        val explaination = arguments?.getString("explanation", "default-explaination")
        val sellingItem = arguments?.getString("sellingItem", "default-price")
        val price = arguments?.getInt("price", 0)
        val status = arguments?.getBoolean("status", false)
        println("${title}%%${explaination}%%${sellingItem}%%${price}%%${status}")

        editTitle.setText("$title")
        editExplaination.setText("$explaination")
        editSellingItem.setText("$sellingItem")
        editPrice.setText("$price")
        editStatus.setText("${if (status == true) "판매중" else "판매 완료"}")

        if(status == true){
            switch.isChecked = true
        }
        else{
            switch.isChecked = false
        }

        var itemId : String = ""
        itemCollectionRef.whereEqualTo("title", title).get()
            .addOnSuccessListener { documents ->
                for(document in documents) {
                    itemId = document.id
                    println(itemId)
                }
            }

        update.setOnClickListener {
            val updatedTitle = editTitle.text.toString()
            val updatedExplaination = editExplaination.text.toString()
            val updatedSellingItem = editSellingItem.text.toString()
            val updatedPrice = editPrice.text.toString().toInt()
            //val updatedStatus = editStatus.text.toString().toBoolean()
            val updatedStatus = switch.isChecked.toString().toBoolean()
            /*
            itemCollectionRef.document(currentItemID).update(
                mapOf(
                    "title" to updatedTitle,
                    "explanation" to updatedExplanation,
                    "sellingItem" to updatedSellingItem,
                    "price" to updatedPrice,
                    "status" to updatedStatus
                )
            )

             */


            println("[editPage]${itemId} : ${updatedTitle}-${updatedExplaination}-${updatedSellingItem}-${updatedPrice}-${updatedStatus}")
            //정보는 잘 받아오고 있음



            itemCollectionRef.document(itemId).update("title", updatedTitle)
            itemCollectionRef.document(itemId).update("explaination", updatedExplaination)
            itemCollectionRef.document(itemId).update("sellingItem", updatedSellingItem)
            itemCollectionRef.document(itemId).update("price", updatedPrice)
            itemCollectionRef.document(itemId).update("status", updatedStatus)


            val listPage = listFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, listPage)
            transaction.commit()


        }

        return view
    }
}