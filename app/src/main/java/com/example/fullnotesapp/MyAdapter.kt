package com.example.fullnotesapp


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_row.view.*

class MyAdapter(
    private val activity: MainActivity,
    private val items: ArrayList<NoteData>): RecyclerView.Adapter<MyAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyAdapter.ItemViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.apply {
            tv.text = item.noteText
            if (position % 2 == 0) {
                notes.setBackgroundColor(Color.GRAY)
            }
            editIcon.setOnClickListener {
                activity.raiseDialog(item.id)
            }
            delIcon.setOnClickListener {
                activity.deleteNote(item.id)
            }
        }
    }

    override fun getItemCount() = items.size
}