package com.example.taskplannercustomapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Task(val taskName: String, val taskDescription: String, val dueDate: String, val status: String)

class MainActivity : AppCompatActivity() {
    val list = mutableListOf<Task>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        val taskList = findViewById<RecyclerView>(R.id.taskList)

        //add horizontal line to seperate each task
        // taskList.setHasFixedSize(true)
        taskList.addItemDecoration(DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL))
        taskList.adapter = Adapter(list.toList())
        taskList.layoutManager = LinearLayoutManager(this)
        registerForContextMenu(taskList)

    }

    private fun initData()
    {
        val taskObject = Task("Task1", "Demo task.", "20/10/2020", "pending")
        val taskObject2 = Task("Task2", "Demo task.", "20/10/2020", "pending")
        val taskObject3 = Task("Task3", "Demo task.", "20/10/2020", "pending")
        val taskObject4 = Task("Task4", "Demo task.", "20/10/2020", "pending")
        val taskObject5 = Task("Task5", "Demo task.", "20/10/2020", "pending")
        val taskObject6 = Task("Task6", "Demo task.", "20/10/2020", "pending")
        list.add(taskObject)
        list.add(taskObject2)
        list.add(taskObject3)
        list.add(taskObject4)
        list.add(taskObject5)
        list.add(taskObject6)
    }

}