package com.example.taskplannercustomapp

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActionBarContextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.prefs.Preferences


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


        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val db = Handler(applicationContext)
                    val snackBar = Snackbar.make(
                       window.decorView.findViewById(android.R.id.content), list.elementAt(viewHolder.adapterPosition).taskName+" Removed from task list.",
                        Snackbar.LENGTH_LONG)
                    snackBar.show()
                    db.deleteData( list.elementAt(viewHolder.adapterPosition))
                    list.removeAt(viewHolder.adapterPosition)
                    //update the view
                    (taskList.adapter as Adapter).notifyItemRemoved(viewHolder.adapterPosition)
                }

                override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                         dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    val paint = Paint()
                    //make sure the red background disappears as you release
                    if (dX != 0f && isCurrentlyActive) {
                        val itemView = viewHolder.itemView
                        paint.color = Color.parseColor("#e03838")

                        if (dX < 0) {
                            val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(),
                                itemView.right.toFloat(), itemView.bottom.toFloat())
                            c.drawRect(background, paint)
                    }
                }

            }}

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(taskList)


    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        var db = Handler(applicationContext)
        return when (item.itemId) {
            1 ->{
                db.markAsDone( list.elementAt(item.groupId))
                initData()
                taskList.adapter = Adapter(list)
                true
            }
            2 ->{

                //delete the item with that groupID
                val snackBar = Snackbar.make(
                    window.decorView.findViewById(android.R.id.content), list.elementAt(item.groupId).taskName+" Removed from task list.",
                    Snackbar.LENGTH_LONG)
                snackBar.show()
                db.deleteData( list.elementAt(item.groupId))
                list.removeAt(item.groupId)
                //update the view
                (taskList.adapter as Adapter).notifyItemRemoved(item.groupId)

                true
            }
            else ->        return super.onContextItemSelected(item)
        }
    }
    //load the data from database, update the list
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