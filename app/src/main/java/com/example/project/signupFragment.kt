package com.example.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class signupFragment : Fragment() {

    private val itemList = arrayListOf<item>()
    private var adapter: adapter = adapter(itemList)
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemCollectionRef = db.collection("items")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.signup, container, false)

        val signin = view.findViewById<Button>(R.id.signin)
        val signup = view.findViewById<Button>(R.id.signup)

        //회원가입
        signup.setOnClickListener {
            val email = view.findViewById<TextView>(R.id.email).text.toString()
            val name = view.findViewById<TextView>(R.id.name)
            val birth = view.findViewById<TextView>(R.id.birth)
            val pw = view.findViewById<TextView>(R.id.password).text.toString()
            Firebase.auth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {

                        val moveToList = listFragment()
                        val transaction = requireActivity().supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment, moveToList)
                        transaction.commit()

                    } else {
                        Log.w("LoginActivity", "signInWithEmail", task.exception)
                        Toast.makeText(requireContext(), "아이디/비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        //로그인
        signin.setOnClickListener {
            val email = view.findViewById<TextView>(R.id.email).text.toString()
            val name = view.findViewById<TextView>(R.id.name)
            val birth = view.findViewById<TextView>(R.id.birth)
            val pw = view.findViewById<TextView>(R.id.password).text.toString()
            println("${email}-${pw}")

            Firebase.auth.signInWithEmailAndPassword(email,pw)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        println("log in success")
                        //판매글 목록이 보이는 list로 이동
                        //스택 쌓기 불 필요
                        val moveToList = listFragment()
                        val transaction = requireActivity().supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment, moveToList)
                        transaction.commit()

                    }
                    else {
                        Log.w("LoginActivity", "signInWithEmail", task.exception)
                        Toast.makeText(requireContext(), "회원가입 실패.", Toast.LENGTH_SHORT).show()
                    }
                }


        }



        return view
    }
}