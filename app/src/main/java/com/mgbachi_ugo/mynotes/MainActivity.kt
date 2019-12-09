package com.mgbachi_ugo.mynotes

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var db: SQLiteDatabase? = null
    var cursor:Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        // open note details activity
        fabAddNotes.setOnClickListener {
            val intent = Intent(this, NoteDetails::class.java)
            startActivity(intent)
        }

        var objToCreateDB = MyNoteSQLiteOpenHelper(this)    // Creates the database and notes table
         db = objToCreateDB.readableDatabase  //access to the database

        cursor = db!!.query("NOTES", arrayOf("_id", "title"),
            null, null, null, null, null)

        // Adapter
        val listAdapter = SimpleCursorAdapter(this,
            android.R.layout.simple_list_item_1,
            cursor,
            arrayOf("title"),
            intArrayOf(android.R.id.text1),
            0
        )
        // set the adapter view
        listViewNotes.adapter = listAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor!!.close()
        db!!.close()
    }
}
