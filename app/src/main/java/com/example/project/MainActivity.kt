package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


lateinit var sceneRoot : FrameLayout
class MainActivity : AppCompatActivity() {



    private val itemList = arrayListOf<item>()
    private var adapter: adapter = adapter(itemList)
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemCollectionRef = db.collection("items")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)

        val btn = findViewById<Button>(R.id.serviceStart)
        btn.setOnClickListener {
            startActivity(Intent(this, signActivity::class.java))
        }

    }


}