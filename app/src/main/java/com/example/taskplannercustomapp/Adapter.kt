package com.example.taskplannercustomapp

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import android.view.ContextMenu;
import android.view.View;
import android.view.MenuItem
import android.widget.RatingBar
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Adapter(private val data: List<Task>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    //  private val listener:(Int) -> Unit)
    //sync the list list
    var list = mutableListOf<Task>()
    fun Adapter(list: List<Task>)
    {
        //list.sortedBy { it.dueDate}
        this.list=list.sortedBy { it.dueDate }.toMutableList()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.layout_row, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }


    //add inner
    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v), View.OnCreateContextMenuListener {
        private val taskName: TextView = v.findViewById(R.id.taskname)
        private val dueDate: TextView = v.findViewById(R.id.duedate)
        private val ratingBar: RatingBar = v.findViewById(R.id.ratingBar)
        private val dayLeft: TextView = v.findViewById(R.id.days)
        private val status: ImageView = v.findViewById(R.id.status)

        fun bind(item: Task) {


            taskName.text = item.taskName
            dueDate.text = item.dueDate
            ratingBar.rating = item.difficulty
            val date1 = LocalDate.parse(item.dueDate)
            val date2 = LocalDate.now()
            val days = ChronoUnit.DAYS.between(date2, date1).toString()
            //get the time remaining for the tasks and change icon
           if(days.toInt()>=0)
            {
                dayLeft.text = days
              //  status.setImageResource(R.drawable.ic_create_24px)

            }
            else
            {
                dayLeft.text = "Due"
                status.setImageResource(R.drawable.ic_baseline_access_time_24)
            }
            //UI idea from mobile game Arknights
            val taskdone: ImageView = v.findViewById(R.id.taskdone)
            if(item.done==1)
            {
                taskdone.setImageResource(R.drawable.cropped2)
                v.setBackgroundColor(ContextCompat.getColor(v.context,R.color.colorGrey))

            }
            //context menu for each item
            v.setOnCreateContextMenuListener(this)
            v.setOnClickListener{
              // Toast.makeText(v.context, "Task Description: "+item.taskDescription, Toast.LENGTH_LONG).show()
                val snackBar = Snackbar.make(
                    it, item.taskDescription ,
                    Snackbar.LENGTH_LONG)
                snackBar.show()
            }
        }
        // https://www.youtube.com/watch?v=fl5BB3I3MvQ&ab_channel=PRABEESHRK
        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            //Set Header of Context Menu
            menu!!.setHeaderTitle("Select Option")
            // position, itemId, order, title
            menu.add(this.adapterPosition, 1, 0, "Mark as complete")
            menu.add(this.adapterPosition, 2, 1, "Delete")
        }
    }

    fun removeItem (position: Int)
    {
        list.removeAt(position)
    }

}