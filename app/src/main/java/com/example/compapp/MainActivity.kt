package com.example.compapp

import TableAdapter
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DbHelper(this, null)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        val statuses = dbHelper.loadTableStatuses()
        val tables = mutableListOf<Table>()
        for (i in 0 until 20) {
            val status = if (i < statuses.size) statuses[i] else 0
            tables.add(Table(i, status))
        }

        val adapter = TableAdapter(dbHelper, tables)
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}