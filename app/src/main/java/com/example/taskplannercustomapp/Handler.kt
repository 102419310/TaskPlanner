package com.example.taskplannercustomapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.TableLayout
import android.widget.Toast

val DATABASE_NAME = "MyDB"
val TABLE_NAME = "TASKS"
val COL_TASKNAME = "taskname"
val COL_TASKDESCRIPTION = "description"
val COL_DUEDATE = "duedate"
val  COL_DIFFICULTY = "difficult"
val  COL_DONE = "done"

class Handler(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,1)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME +" ("+
                COL_TASKNAME + " VARCHAR(20) NOT NULL PRIMARY KEY," +
                COL_TASKDESCRIPTION + " VARCHAR(50) NOT NULL," +
                COL_DUEDATE + " VARCHAR(20) NOT NULL," +
                COL_DIFFICULTY + " Float NOT NULL," +
                COL_DONE + " INT NOT NULL);"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(task: Task)
    {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_TASKNAME, task.taskName)
        cv.put(COL_TASKDESCRIPTION, task.taskDescription)
        cv.put(COL_DUEDATE, task.dueDate)
        cv.put(COL_DIFFICULTY, task.difficulty)
        cv.put(COL_DONE,task.done)
        var result =  db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong())
        {
            Log.i("msg","failed")
        }
        else
        {
            Log.i("msg","successed")
        }
    }

    fun readData():MutableList<Task>
    {
        var list : MutableList<Task> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst())
        {
            do
            {
                var task = Task()
                task.taskName = result.getString(result.getColumnIndex(COL_TASKNAME))
                task.taskDescription = result.getString(result.getColumnIndex(COL_TASKDESCRIPTION))
                task.dueDate = result.getString(result.getColumnIndex(COL_DUEDATE))
                task.difficulty = result.getFloat(result.getColumnIndex(COL_DIFFICULTY))
                task.done = result.getInt(result.getColumnIndex(COL_DONE))
                list.add(task)

            }while(result.moveToNext())
        }
            result.close()
        db.close()
        return list
    }

    fun deleteData(task: Task)
    {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, COL_TASKNAME + "=?", arrayOf(task.taskName))
        db.close()
    }

    fun markAsDone(task: Task)
    {
        val cv = ContentValues()
        val db = this.writableDatabase
        //done to 1
        cv.put(COL_DONE,1)
        db.update(TABLE_NAME, cv,COL_TASKNAME + "=?", arrayOf(task.taskName))
        db.close()
    }
}