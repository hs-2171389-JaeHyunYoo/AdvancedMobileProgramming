package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class naviActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navi)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fragment_home -> {
                println("home 클릭 됨")
                return true
            }

            R.id.fragment_chat -> {
                println("chat 클릭 됨")
                return true
            }

            R.id.fragment_info -> {
                println("info 클릭 됨")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}