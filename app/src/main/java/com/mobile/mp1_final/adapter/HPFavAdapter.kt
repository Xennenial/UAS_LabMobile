package com.mobile.mp1_final.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.mp1_final.database.Note
import com.mobile.mp1_final.databinding.ItemRowHpBinding
import com.mobile.mp1_final.databinding.ItemRowFavHpBinding
import com.mobile.mp1_final.repository.NoteRepository

class HPFavAdapter(private val noteRepository: NoteRepository) : RecyclerView.Adapter<HPFavAdapter.NoteViewHolder>() {

    private val listNotes = ArrayList<Note>()

    inner class NoteViewHolder(private val binding: ItemRowFavHpBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvItemName.text = note.name
                tvItemDescription.text = note.species

                Glide.with(itemView.context)
                    .load(note.img)
                    .into(imgItemPhoto)
            }
        }

        init {
            binding.deleteBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val noteToDelete = listNotes[position]

                    // Memastikan nilai yang nullable tidak null sebelum mengirimkannya ke fungsi
                    noteToDelete.name?.let { name ->
                        noteToDelete.species?.let { species ->
                            noteToDelete.img?.let { img ->
                                noteRepository.deleteExistingNote(name, species, img)

                                listNotes.removeAt(position)
                                notifyItemRemoved(position)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemRowFavHpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    // Fungsi untuk mengatur data ke adapter
    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(notes: List<Note>) {
        listNotes.clear()
        listNotes.addAll(notes)
        notifyDataSetChanged()
    }
}
