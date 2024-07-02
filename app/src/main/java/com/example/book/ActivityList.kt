package com.example.book

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.book.databinding.ActivityListBinding

class ActivityList : AppCompatActivity() {
    private lateinit var binding : ActivityListBinding
    private lateinit var db:BooksDatabaseHelper
    private lateinit var bookAdapter:BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BooksDatabaseHelper(this)

        bookAdapter = BooksAdapter(db.getAllBooks(),this)

        binding.notesRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.notesRecyclerView.adapter=bookAdapter

        // Set click listener for addButton
        binding.addButton.setOnClickListener {
            val intent = Intent(this,AddBook::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        bookAdapter.refreshData(db.getAllBooks())
    }
}

