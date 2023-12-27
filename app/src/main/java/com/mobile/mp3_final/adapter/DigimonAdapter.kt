package com.mobile.mp3_final.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.mp3_final.R
import com.mobile.mp3_final.api.ResponseItem
import com.mobile.mp3_final.repository.NoteRepository

class DigimonAdapter(private val agents:List<ResponseItem>, private val noteRepository: NoteRepository):RecyclerView.Adapter<DigimonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_digimon, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return agents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = agents[position]
        holder.name.text = data.name
        holder.level.text = data.level
        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(data.img)
            .into(holder.img)

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val datachecked = agents[position]
                noteRepository.insertIfNotExists(datachecked.name, datachecked.level, datachecked.img)
                Toast.makeText(holder.itemView.context, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
            } else {
                // Lakukan sesuatu jika checkbox tidak dicentang
            }
        }
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_item_name)
        val level: TextView = view.findViewById(R.id.tv_item_description)
        val img: ImageView = view.findViewById(R.id.img_item_photo)
        val checkbox: CheckBox = view.findViewById(R.id.checkbox)

        init {
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val data = agents[position]
                    if (isChecked) {
                        noteRepository.insertIfNotExists(data.name, data.level, data.img)
                    } else {
                        // Lakukan sesuatu jika checkbox tidak dicentang
                    }
                }
            }
        }
    }
}