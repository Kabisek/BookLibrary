package com.example.book

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.book.databinding.ActivityAddBookBinding

class AddBook : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding
    private lateinit var db:BooksDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BooksDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val priority = binding.priorityEditText.text.toString()
            val date = binding.deadlineEditText.text.toString()
            val book = Book(0,title,content,priority,date)
            db.insertBook(book)

            // Navigate back to ActivityList
            val intent = Intent(this, ActivityList::class.java)
            startActivity(intent)

            // Finish the current activity
            finish()

            Toast.makeText(this, "Book Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
