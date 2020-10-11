package com.example.taskplannercustomapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit


class Task(val taskName: String, val taskDescription: String, val dueDate: String, val status: String)

class MainActivity : AppCompatActivity() {
    val list = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setTitle("TY Task Planner")

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
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item!!.itemId) {
            1 ->{
                Toast.makeText(applicationContext, "Modify", Toast.LENGTH_SHORT).show()
                true
            }
            2 ->{
                var index = item.groupId + 1
                list.removeAt(item.groupId)
                taskList.adapter = Adapter(list.toList())

                Toast.makeText(applicationContext, "Delete Task at $index", Toast.LENGTH_SHORT).show()
                true
            }
            else ->        return super.onContextItemSelected(item)

        }
    }
    private fun initData()
    {
        val taskObject = Task("Task1", "Demo task.", "2022-10-25", "pending")
        val taskObject2 = Task("Task2", "Demo task.", "2021-10-25", "pending")
        val taskObject3 = Task("Task3", "Demo task.", "2020-05-25", "pending")
        val taskObject4 = Task("Task4", "Demo task.", "2020-11-25", "pending")
        val taskObject5 = Task("Task5", "Demo task.", "2020-01-25", "pending")
        val taskObject6 = Task("Task6", "Demo task.", "2020-10-14", "pending")
        list.add(taskObject)
        list.add(taskObject2)
        list.add(taskObject3)
        list.add(taskObject4)
        list.add(taskObject5)
        list.add(taskObject6)
        list.sortBy {it.dueDate}
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    //https://www.javatpoint.com/kotlin-android-options-menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.create -> {
                supportActionBar?.setTitle("Create new Task (hard coded)")
                Toast.makeText(applicationContext, "Create new Task", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}