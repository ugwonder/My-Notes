package com.mgbachi_ugo.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

class NoteDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notes_details_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}
