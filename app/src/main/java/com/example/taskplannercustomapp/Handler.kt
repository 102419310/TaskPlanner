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
val COL_STATUS = "status"

class Handler(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,1)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME +" ("+
                COL_TASKNAME + " VARCHAR(50)," +
                COL_TASKDESCRIPTION + " VARCHAR(100)," +
                COL_DUEDATE + " VARCHAR(20)," +
                COL_STATUS + " VARCHAR(10));"
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
        cv.put(COL_STATUS, task.status)

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
                task.status = result.getString(result.getColumnIndex(COL_STATUS))
                list.add(task)
                Log.i("msg","added")

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
}