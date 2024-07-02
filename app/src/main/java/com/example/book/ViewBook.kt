package com.example.book

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.book.databinding.ActivityUpdateBookBinding
import com.example.book.databinding.ActivityViewBookBinding

class ViewBook : AppCompatActivity() {
    private lateinit var binding: ActivityViewBookBinding
    private lateinit var db: BooksDatabaseHelper
    private var bookId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBookBinding.inflate(layoutInflater)
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
