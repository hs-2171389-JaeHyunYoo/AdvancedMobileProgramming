package com.example.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class infoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = requireActivity()
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        val info_id = view.findViewById<TextView>(R.id.info_id)
        val logInWith = Firebase.auth.currentUser?.email.toString()
        info_id.text = logInWith

        val logOutBtn = view.findViewById<Button>(R.id.info_iog_out)
        logOutBtn.setOnClickListener {
            Firebase.auth.signOut()
            activity.finish()
        }
        return view
    }

}