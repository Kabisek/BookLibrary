package com.example.book

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class BooksAdapter (private var books: List<Book>, context: Context) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    private val db: BooksDatabaseHelper = BooksDatabaseHelper(context)

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val priorityTextView:TextView=itemView.findViewById(R.id.priorityTextView)
        val deadlineTextView:TextView=itemView.findViewById(R.id.dateTextView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val viewButton:ImageView = itemView.findViewById(R.id.viewButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.titleTextView.text = book.title
        holder.contentTextView.text = book.content
        holder.priorityTextView.text=book.priority.toString()
        holder.deadlineTextView.text=book.date


        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateBook::class.java).apply {
                putExtra("book_id", book.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.viewButton.setOnClickListener{
            val intent = Intent(holder.itemView.context,ViewBook::class.java).apply {
                putExtra("book_id",book.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteBook(book.id)
            refreshData(db.getAllBooks())
            Toast.makeText(holder.itemView.context, "Book deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newBooks: List<Book>){
        books = newBooks
        notifyDataSetChanged()
    }

}