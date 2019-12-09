package com.mgbachi_ugo.mynotes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyNoteSQLiteOpenHelper(context: Context) : SQLiteOpenHelper(context, "MyNotesDB", null, 1)  {
    override fun onCreate(db: SQLiteDatabase?) {

        db!!.execSQL(
            "CREATE TABLE notes("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "title TEXT,"
                + "description TEXT)"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}