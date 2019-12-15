package com.mgbachi_ugo.mynotes

import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_note_details.*

class NoteDetails : AppCompatActivity() {
    var db:SQLiteDatabase? = null
    var noteId:Int = 0
    var cursor:Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)

        val myNotesDatabaseHelper = MyNoteSQLiteOpenHelper(this)
        db = myNotesDatabaseHelper.writableDatabase

        noteId = intent.extras?.get("NOTE_ID").toString().toInt()

        // code that reads Note Title and description that its id is equal the NoteId

        if (noteId != 0)
        {
            cursor = db!!.query(
                "Notes",
                arrayOf("TITLE","DESCRIPTION"),
                "_id=?",
                arrayOf(noteId.toString()),
                null,null,null
                )
            if (cursor!!.moveToFirst() == true)
            {
                editTextTitle.setText(cursor!!.getString(0))
                editTextDescription.setText(cursor!!.getString(1))
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == R.id.save_note)
        {
            //Insert a new note
            val newNoteValues = ContentValues()
            if (editTextTitle.text.isEmpty() == true) {
                newNoteValues.put("TITLE", "Untitled")
            } else {
                newNoteValues.put("TITLE", editTextTitle.text.toString())
            }
            newNoteValues.put("DESCRIPTION", editTextDescription.text.toString())

            if (noteId == 0)
            {
            insertNote(newNoteValues)
            }
            else
            {
                updateNote(newNoteValues)
            }
        }
        else if (item.itemId == R.id.delete_note)
        {
            deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }
    fun deleteNote()
    {
        var dialog:AlertDialog
        var builder = AlertDialog.Builder(this)
        
        // set a message for the alert box
        builder.setTitle("Deleting note")
        
        // Set a message for alert dialog
        builder.setMessage("Are you sure you want to delete '${editTextTitle.text}'?")
        
        // set the alert dialog positive (Yes) button
        builder.setPositiveButton("YES",dialogClickListener)

        // set a neutral (cancel) button
        builder.setNegativeButton("CANCEL",dialogClickListener)

        //Initialize the AlertDailog by using the builder object
        dialog = builder.create()

        //display the alert dialog
        dialog.show()

    }

    val dialogClickListener = DialogInterface.OnClickListener { _, which ->
        if (which == DialogInterface.BUTTON_POSITIVE)
        {
            db!!.delete("NOTES","_id=?", arrayOf((noteId.toString())))
            Toast.makeText(this, "Note Deleted!", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    fun  updateNote(newNoteValues: ContentValues)
    {
        db!!.update("NOTES",newNoteValues,"_id=?", arrayOf(noteId.toString()))
        Toast.makeText(this,"Note Updated!", Toast.LENGTH_SHORT).show()

        // Empty the Edit texts
        editTextTitle.setText("")
        editTextDescription.setText("")

    }

    private fun insertNote(newNoteValues:ContentValues) {


        db!!.insert("NOTES", null, newNoteValues)
        Toast.makeText(this, "Note saved!", Toast.LENGTH_LONG).show()

        // Empty the Edit texts
        editTextTitle.setText("")
        editTextDescription.setText("")

        // shift focus to the editTextTitle

        editTextTitle.requestFocus()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notes_details_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        db!!.close()
    }

}
