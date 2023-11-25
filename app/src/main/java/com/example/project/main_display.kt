package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView

class main_display : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment, listFragment::class.java, null)
            addToBackStack(null)
        }

        val menu = findViewById<BottomNavigationView>(R.id.navigationView)
        menu.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.fragment_home -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment, listFragment::class.java, null)
                        addToBackStack(null)
                    }
                    true
                }
                R.id.fragment_chat -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment, chatFragment::class.java, null)
                        addToBackStack(null)
                    }
                    true
                }
                R.id.fragment_info -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment, infoFragment::class.java, null)
                        addToBackStack(null)
                    }
                    true
                }
                else -> false
            }
        }
    }

}