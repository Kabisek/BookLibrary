package com.example.book

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BooksDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "books.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allbooks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_PRIORITY = "priority"
        private const val COLUMN_DATE = "date"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT, $COLUMN_PRIORITY TEXT, $COLUMN_DATE TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertBook(book: Book){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, book.title)
            put(COLUMN_CONTENT, book.content)
            put(COLUMN_PRIORITY,book.priority)
            put(COLUMN_DATE,book.date)
        }
        db.insert(TABLE_NAME,null, values)
        db.close()
    }

    fun getAllBooks(): List<Book> {
        val booksList = mutableListOf<Book>()
        val db = readableDatabase
        val query  = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))

            val book = Book(id, title, content,priority,date)
            booksList.add(book)

        }
        cursor.close()
        db.close()
        return booksList
    }

    fun updateBook(book: Book){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, book.title)
            put(COLUMN_CONTENT, book.content)
            put(COLUMN_PRIORITY,book.priority)
            put(COLUMN_DATE,book.date)

        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(book.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getBookByID(bookId: Int): Book{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $bookId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
        val priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
        val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))

        cursor.close()
        db.close()
        return Book(id, title, content,priority,date)
    }

    fun deleteBook(bookId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(bookId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}