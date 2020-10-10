package com.example.taskplannercustomapp

import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.view.MenuItem
import android.view.ContextMenu
import android.os.Bundle
import android.view.View

class Adapter(private val data: List<Task>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
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
        /* Another approach
        holder.itemView.setOnCreateContextMenuListener(){ contextMenu, _, _ ->
            contextMenu.add("Add").setOnMenuItemClickListener {
                true
            }
        }

         */
    }


    //add inner
    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v), View.OnCreateContextMenuListener {
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
            v.setOnCreateContextMenuListener(this)
            // status.setOnCreateContextMenuListener(status)
            v.setOnClickListener{
                //Toast.makeText(v.context, "${item.y}  ${item.y}", Toast.LENGTH_LONG).show()

            }

        }
        override fun onContextItemSelected(item: MenuItem?): Boolean{
            return when (item!!.itemId) {
                1 ->{
                    Toast.makeText(v.context, "Modify", Toast.LENGTH_LONG).show()
                    true
                }
                2 ->{
                    Toast.makeText(v.context, "Delete", Toast.LENGTH_LONG).show()
                    true
                }
                else -> true
            }
        }
        // https://www.youtube.com/watch?v=fl5BB3I3MvQ&ab_channel=PRABEESHRK
        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            //Set Header of Context Menu
            menu!!.setHeaderTitle("Select Option")
            // position, itemId, order, title
            menu.add(this.adapterPosition, 1, 0, "Modify")
            menu.add(this.adapterPosition, 2, 1, "Delete")
        }


    }
}