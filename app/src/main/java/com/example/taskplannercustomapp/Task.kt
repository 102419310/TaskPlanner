package com.example.taskplannercustomapp

import android.app.ActivityManager

class Task {
    var taskName: String = ""
    var taskDescription: String = ""
    var dueDate:String = ""
    var difficulty:Float = 0F
    var done:Int = 0
    constructor()
    constructor(taskName:String, taskDescription:String, dueDate:String, difficulty:Float)
    {
        this.taskName = taskName
        this.taskDescription = taskDescription
        this.dueDate = dueDate
        this.difficulty = difficulty
        this.done = 0
    }
}