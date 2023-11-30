package com.example.project
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project.DBKey.Companion.CHILD_CHAT
import com.example.project.DBKey.Companion.DB_USERS
import com.example.project.DBKey.Companion.DB_ITEMS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class signActivity : AppCompatActivity() {
    private lateinit var itemDB: DatabaseReference
    private lateinit var userDB: DatabaseReference
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)


        val signUpBtn = findViewById<Button>(R.id.signup)

        // 회원 가입
        signUpBtn.setOnClickListener {
            val email = findViewById<TextView>(R.id.email).text.toString()
            val pw = findViewById<TextView>(R.id.password).text.toString()

            Firebase.auth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        userDB = Firebase.database.reference.child(DB_USERS)
                        itemDB= Firebase.database.reference.child(DB_ITEMS)
                        //initializeChatRoom()
                        startActivity(Intent(this, main_display::class.java))
                    } else {
                        Log.w("LoginActivity", "signInWithEmail", task.exception)
                        Toast.makeText(this, "회원 가입 실패.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // 로그인
        val signinBtn = findViewById<Button>(R.id.signin)
        signinBtn.setOnClickListener {
            val email = findViewById<TextView>(R.id.email).text.toString()
            val pw = findViewById<TextView>(R.id.password).text.toString()

            Firebase.auth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        userDB = Firebase.database.reference.child(DB_USERS)
                        itemDB= Firebase.database.reference.child(DB_ITEMS)
                        //initializeChatRoom()
                        startActivity(Intent(this, main_display::class.java))
                    } else {
                        Log.w("LoginActivity", "signInWithEmail", task.exception)
                        Toast.makeText(this, "아이디/비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
