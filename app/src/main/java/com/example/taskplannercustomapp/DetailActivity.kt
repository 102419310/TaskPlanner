package com.example.taskplannercustomapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class DetailActivity : AppCompatActivity() {
   // lateinit var detail: Details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_task)
        supportActionBar?.title = "Create a new Task"

        val save: Button = findViewById(R.id.buttonsave)
        val cancel: Button = findViewById(R.id.buttoncancel)
        val taskName: TextInputEditText = findViewById(R.id.tasknametextEditText)
        val taskDescription:TextInputEditText = findViewById(R.id.taskdescriptionEditText)
        val duedate: TextInputEditText = findViewById(R.id.duedatetextEditText)
        val rating: RatingBar = findViewById(R.id.ratingBarcreate)
        //live input error detection!
        taskName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) { if (taskName.text.isNullOrEmpty())
                taskName.error = "Task name can not be empty!"
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        taskDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) { if (taskDescription.text.isNullOrEmpty())
                taskDescription.error = "Task description can not be empty!"
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        val regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}"

        duedate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if(duedate.text.toString()=="")
                {
                    duedate.error = "Due date can not be empty!"
                }
            }
            override fun afterTextChanged(s: Editable) {
                if(!duedate.text.toString().matches(regexp.toRegex()))
                {
                    duedate.error = "Due date must be in YYYY-MM-DD format!"
                }
            }
        })

        save.setOnClickListener{

            if(taskName.text.toString() == "")
            {
                Toast.makeText(this, "Please enter a task name!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(taskDescription.text.toString() == "")
            {
                Toast.makeText(this, "Please enter task description!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //simple regex for yyyy-mm-dd

            if(duedate.text.toString()=="")
            {
                Toast.makeText(this, "Please enter due date!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!duedate.text.toString().matches(regexp.toRegex()))
            {
                Toast.makeText(this, "Please check your due date format!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val taskObject = Task(taskName.text.toString(),taskDescription.text.toString(), duedate.text.toString(), rating.rating)
            var db = Handler(applicationContext)
            db.insertData(taskObject)

            finish()
        }
        cancel.setOnClickListener {
            //destroy the current activity and return to main menu
            finish()
        }

    }

}