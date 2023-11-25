package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class signActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val signUpBtn = findViewById<Button>(R.id.signup)

        //회원 가입
        signUpBtn.setOnClickListener {
            val email = findViewById<TextView>(R.id.email).text.toString()
            val name = findViewById<TextView>(R.id.name)
            val birth = findViewById<TextView>(R.id.birth)
            val pw = findViewById<TextView>(R.id.password).text.toString()
            Firebase.auth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, main_display::class.java))
                    } else {
                        Log.w("LoginActivity", "signInWithEmail", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        //로그인
        val signinBtn = findViewById<Button>(R.id.signin)
        signinBtn.setOnClickListener {
            val email = findViewById<TextView>(R.id.email).text.toString()
            val name = findViewById<TextView>(R.id.name)
            val birth = findViewById<TextView>(R.id.birth)
            val pw = findViewById<TextView>(R.id.password).text.toString()
            println("${email}-${pw}")

            Firebase.auth.signInWithEmailAndPassword(email,pw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, main_display::class.java))
                        /*
                        println("log in success")
                        //판매글 목록이 보이는 list로 이동
                        //스택 쌓기 불 필요
                        val moveToList = list()
                        val transaction = this.supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment, moveToList)
                        transaction.commit()


                         */
                    }
                    else {
                        Log.w("LoginActivity", "signInWithEmail", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}