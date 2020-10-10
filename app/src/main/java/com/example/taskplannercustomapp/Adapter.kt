package com.example.taskplannercustomapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val data: List<Task>): RecyclerView.Adapter<Adapter.ViewHolder>(){
    //  private val listener:(Int) -> Unit)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.layout_row, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // val item:Int =
        holder.bind(data[position])

    }

    //add inner
    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val taskName: TextView = v.findViewById(R.id.taskname)
        private val dueDate: TextView = v.findViewById(R.id.duedate)
        private val status: ImageView = v.findViewById(R.id.status)

        fun bind(item: Task) {
            taskName.text = item.taskName
            dueDate.text = item.dueDate
            if(item.status == "pending")
            {
                status.setImageResource(R.drawable.ic_create_24px)
            }
            else if(item.status == "done")
            {
                status.setImageResource(R.drawable.ic_done_24px)
            }

            v.setOnClickListener{
                //Toast.makeText(v.context, "${item.y}  ${item.y}", Toast.LENGTH_LONG).show()
            }
        }

    }

}