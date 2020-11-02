package com.example.taskplannercustomapp

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    var list = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "TY Task Planner"

        setContentView(R.layout.activity_main)
        initData()

        val taskList = findViewById<RecyclerView>(R.id.taskList)

        //add horizontal line to separate each task
        taskList.addItemDecoration(DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL))
        taskList.adapter = Adapter(list)
        taskList.layoutManager = LinearLayoutManager(this)
        registerForContextMenu(taskList)

        var itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(taskList.adapter as Adapter))
        itemTouchHelper.attachToRecyclerView(taskList)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        var db = Handler(applicationContext)
        return when (item!!.itemId) {
            1 ->{
                db.markAsDone( list.elementAt(item.groupId))
                initData()
                taskList.adapter = Adapter(list)
                true
            }
            2 ->{

                //delete the item with that groupID
                //last two line is to present the delete update, then actually delete it in database
                db.deleteData( list.elementAt(item.groupId))
                list.removeAt(item.groupId)
                taskList.adapter = Adapter(list)

                true
            }
            else ->        return super.onContextItemSelected(item)
        }
    }
    public fun initData() {
        var db = Handler(applicationContext)
        var addData = true

        //manual creation
        if (addData)
        {
        val taskObject = Task("Task1", "Demo task.", "2022-10-25", 5F)
        val taskObject2 = Task("Task2", "Demo task.", "2021-10-25", 0.5F)
        val taskObject3 = Task("Task3", "Demo task.", "2020-05-25", 2F)
        val taskObject4 = Task("Task4", "Demo task.", "2020-11-25", 4F)
        val taskObject5 = Task("Task5", "Demo task.", "2020-01-25", 2F)
        val taskObject6 = Task("Task6", "Demo task.", "2020-10-14", 3F)

        db.insertData(taskObject)
        db.insertData(taskObject2)
        db.insertData(taskObject3)
        db.insertData(taskObject4)
        db.insertData(taskObject5)
        db.insertData(taskObject6)
       }

        val list2 = db.readData()
        list2.sortBy {it.dueDate}
        //put the done task at the bottom
        val list3 = list2.sortedWith(compareBy({it.done},{it.dueDate}))
        list = list3.toMutableList()
    }
    override fun onResume(): Unit {
        super.onResume()
        initData()
        taskList.adapter = Adapter(list)
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
                    val i = Intent(this, DetailActivity::class.java).apply{
                    }
                    startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}