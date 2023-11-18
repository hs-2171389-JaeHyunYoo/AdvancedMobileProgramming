package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.transition.Scene
import android.transition.TransitionManager
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var signUp : Scene
    private lateinit var home : Scene
    private lateinit var addingItem : Scene

    private val itemList = arrayListOf<item>()
    private val adapter = adapter(itemList)
    private val db : FirebaseFirestore = Firebase.firestore
    private val itemCollectionRef = db.collection("items")

    val seller = Firebase.auth.currentUser?.email.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sceneRoot = findViewById<FrameLayout>(R.id.scene_root)
        signUp = Scene.getSceneForLayout(sceneRoot, R.layout.signup, this)
        home = Scene.getSceneForLayout(sceneRoot, R.layout.list, this)
        addingItem = Scene.getSceneForLayout(sceneRoot, R.layout.addingitem, this)

        val signup = findViewById<Button>(R.id.signup)
        val signin = findViewById<Button>(R.id.signin)


        //회원가입
        signup.setOnClickListener {
            //회원 가입 기능
            val email = findViewById<TextView>(R.id.email).text.toString()
            val name = findViewById<TextView>(R.id.name)
            val pw = findViewById<TextView>(R.id.password).text.toString()
            val birth = findViewById<TextView>(R.id.birth)
            println("${email}-${name.text}-${birth.text}-${pw}")
            //회원가입 성공
            Firebase.auth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this) { // it: Task<AuthResult!>
                    if (it.isSuccessful) {
                        doLogin(email,pw)
                    } else {
                        Log.w("LoginActivity", "signInWithEmail", it.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }

        }


        //로그인
        signin.setOnClickListener {
            val email = findViewById<TextView>(R.id.email).text.toString()
            val pw = findViewById<TextView>(R.id.password).text.toString()
            Firebase.auth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this) { // it: Task<AuthResult!>
                    if (it.isSuccessful) { //성공
                        doLogin(email,pw)
                    }

                    else {
                        println("로그인 실패")
                    }
                }
        }
    }
    private fun updateList(){
        itemList.clear()

        //파이어스토어 DB에서 데이터들을 모두 가져와 ArrayList에 추가
        itemCollectionRef.get().addOnSuccessListener { result ->
            for (document in result) {
                val title = document.getString("title") ?: ""
                val explanation = document.getString("explanation") ?: ""
                val sellingItem = document.getString("sellingItem") ?: ""
                val price = document.getLong("price")?.toInt() ?: 0
                val status = document.getBoolean("status") ?: false

                itemList.add(item(seller,title, explanation, sellingItem, price, status))

                println("${title}!${explanation}!${sellingItem}!${price}!${status}")
            }
            adapter.notifyDataSetChanged()
        }
    }
    private fun addItem(){
        val title = findViewById<TextView>(R.id.addItemTitle).text.toString()
        val explaination = findViewById<TextView>(R.id.addItemExplaination).text.toString()
        val sellingItem = findViewById<TextView>(R.id.sellingItem).text.toString()
        val price = findViewById<TextView>(R.id.itemPrice).text.toString().toInt()
        var itemStatus = true
        val sellingStatus = findViewById<RadioGroup>(R.id.sellingStatus)
        sellingStatus.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.sellingTrue -> itemStatus = true

                R.id.sellingFalse -> itemStatus = false
            }
        }
        val itemMap = hashMapOf(
            "seller" to seller,
            "title" to title,
            "explaination" to explaination,
            "sellingItem" to sellingItem,
            "price" to price,
            "status" to itemStatus
        )
        //파이어베이스에 추가
        itemCollectionRef.add(itemMap)
    }
    private fun doLogin(email: String, pw:String){
        Firebase.auth.signInWithEmailAndPassword(email,pw)
            .addOnCompleteListener(this) { // it: Task<AuthResult!>
                if (it.isSuccessful) {
                    TransitionManager.go(home,Fade());
                    val showItems = findViewById<RecyclerView>(R.id.showItems)
                    showItems.layoutManager = LinearLayoutManager(this)
                    showItems.adapter = adapter

                    //데이터 recyclerview에 업데이트
                    updateList()

                    val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
                    fab.setOnClickListener {
                        TransitionManager.go(addingItem, Fade())

                        val addItemButton = findViewById<Button>(R.id.addItemButton)
                        addItemButton.setOnClickListener {
                            addItem()
                            updateList()
                            //리사이클러 뷰에 추가


                            TransitionManager.go(home,Fade())



                        }
                    }

                    val except = findViewById<CheckBox>(R.id.except)
                    except.setOnCheckedChangeListener { buttonView, isChecked ->
                        if(isChecked){
                            itemList.clear()
                            val query : Query = itemCollectionRef.whereEqualTo("status", false)
                            val task : Task<QuerySnapshot> = query.get()
                            task.addOnSuccessListener { querySnapshot ->
                                val notForSale : MutableList<DocumentSnapshot> = querySnapshot.documents
                                for(document in notForSale){
                                    val title = document.getString("title") ?: ""
                                    val explanation = document.getString("explanation") ?: ""
                                    val sellingItem = document.getString("sellingItem") ?: ""
                                    val price = document.getLong("price")?.toInt() ?: 0
                                    val status = document.getBoolean("status") ?: false
                                    println("${title}?${explanation}?${sellingItem}?${price}?${status}")
                                    itemList.add(item(seller,title,explanation,sellingItem, price, status))
                                    adapter.notifyDataSetChanged()
                                }

                            }
                        }
                        else{
                            updateList()
                        }
                    }
                    /*
                    if(except.isChecked){
                        val query : Query = itemCollectionRef.whereEqualTo("status", "false")
                        val task : Task<QuerySnapshot> = query.get()
                        task.addOnSuccessListener { querySnapshot ->
                            var notForSale : MutableList<DocumentSnapshot> = querySnapshot.documents
                            for(document in notForSale){
                                val title = document.getString("title") ?: ""
                                val explanation = document.getString("explanation") ?: ""
                                val sellingItem = document.getString("sellingItem") ?: ""
                                val price = document.getLong("price")?.toInt() ?: 0
                                val status = document.getBoolean("status") ?: false
                                println("${title}?${explanation}?${sellingItem}?${price}?${status}")
                            }

                        }

                    }

                     */

                }

                else {
                    Log.w("LoginActivity", "signInWithEmail", it.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }


}