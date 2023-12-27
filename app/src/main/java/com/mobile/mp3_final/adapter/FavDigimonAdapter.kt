package com.mobile.mp3_final.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.mp3_final.database.Note
import com.mobile.mp3_final.database.NoteDao
import com.mobile.mp3_final.databinding.ItemRowFavDigimonBinding
import com.mobile.mp3_final.repository.NoteRepository

class FavDigimonAdapter(private val noteRepository: NoteRepository) : RecyclerView.Adapter<FavDigimonAdapter.NoteViewHolder>() {

    private val listNotes = ArrayList<Note>()

    inner class NoteViewHolder(private val binding: ItemRowFavDigimonBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvItemName.text = note.name
                tvItemDescription.text = note.level

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
                        noteToDelete.level?.let { level ->
                            noteToDelete.img?.let { img ->
                                noteRepository.deleteExistingNote(name, level, img)

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
        val binding = ItemRowFavDigimonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
