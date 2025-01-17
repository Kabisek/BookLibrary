package com.example.book

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Post a delayed action to start the firstView activity after 5 seconds
        Handler().postDelayed({
            val intent = Intent(this, ActivityList::class.java)
            startActivity(intent)
            finish() // Optional, depending on whether you want to keep MainActivity in the back stack
        }, 1500) // Delay in milliseconds (5 seconds)
    }
}