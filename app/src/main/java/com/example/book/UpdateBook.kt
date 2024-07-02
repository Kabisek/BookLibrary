package com.example.book

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.book.databinding.ActivityUpdateBookBinding

class UpdateBook : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBookBinding
    private lateinit var db: BooksDatabaseHelper
    private var bookId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BooksDatabaseHelper(this)

        bookId = intent.getIntExtra("book_id", -1)
        if (bookId == -1) {
            finish()
            return
        }

        val book = db.getBookByID(bookId)
        binding.titleEditText.setText(book.title)
        binding.contentEditText.setText(book.content)
        binding.priorityEditText.setText(book.priority)
        binding.deadlineEditText.setText(book.date)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.titleEditText.text.toString()
            val newContent = binding.contentEditText.text.toString()
            val newPriority = binding.priorityEditText.text.toString()
            val newDate = binding.deadlineEditText.text.toString()
            val updatedBook = Book(bookId, newTitle, newContent, newPriority, newDate)
            db.updateBook(updatedBook)
            finish()
            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show()
        }
    }
}
