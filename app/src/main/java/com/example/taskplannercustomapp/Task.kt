package com.example.taskplannercustomapp

import android.app.ActivityManager

class Task {
    var taskName: String = ""
    var taskDescription: String = ""
    var dueDate:String = ""
    var status:String = ""
    constructor()
    {}
    constructor(taskName:String, taskDescription:String, dueDate:String, status:String)
    {
        this.taskName = taskName
        this.taskDescription = taskDescription
        this.dueDate = dueDate
        this.status = status
    }
}