package com.example.compapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "app", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE main (id INTEGER PRIMARY KEY AUTOINCREMENT, status INTEGER)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


    private fun insertInitialData(db: SQLiteDatabase?) {
        for (i in 0 until 5) {
            val values = ContentValues()
            values.put("status", 0)
            db?.insert("main", null, values)
        }
    }

    fun updateTableStatus(tableId: Int, newStatus: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("status", newStatus)
        db.update("main", values, "id = ?", arrayOf(tableId.toString()))
        db.close()
    }

    fun loadTableStatuses(): List<Int> {
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT status FROM main", null)
        val statuses = mutableListOf<Int>()
        cursor?.use {
            while (it.moveToNext()) {
                statuses.add(it.getInt(0))
            }
        }
        db.close()
        return statuses
    }


    fun viewData(): String {
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM main", null)
        var result = ""
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val status = cursor.getInt(cursor.getColumnIndexOrThrow("status"))
                result += "ID: $id, Status: $status\n"
            } while (cursor.moveToNext())
            cursor.close()
        } else {
            result = "Таблица пуста"
        }
        db.close()
        return result
    }
}
