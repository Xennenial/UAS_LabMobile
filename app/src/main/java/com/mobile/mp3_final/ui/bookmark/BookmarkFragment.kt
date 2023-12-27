package com.mobile.mp3_final.ui.bookmark

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.mp3_final.adapter.FavDigimonAdapter
import com.mobile.mp3_final.database.NoteRoomDatabase
import com.mobile.mp3_final.databinding.BookmarkFragmentBinding
import com.mobile.mp3_final.repository.NoteRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: BookmarkFragmentBinding
    private lateinit var favDigimonAdapter: FavDigimonAdapter
    private lateinit var noteRepository: NoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = BookmarkFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteRepository = NoteRepository(requireActivity().application)

        favDigimonAdapter = FavDigimonAdapter(noteRepository)

        binding.favDigimonRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favDigimonAdapter
        }

        loadDataFromDB()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadDataFromDB() {
        GlobalScope.launch(Dispatchers.IO) {
            val noteDao = NoteRoomDatabase.getDatabase(requireContext()).noteDao()
            val notes = noteDao.getAllNotes() // Ambil semua catatan dari database lokal

            withContext(Dispatchers.Main) {
                // Set data ke dalam adapter untuk ditampilkan di RecyclerView
                favDigimonAdapter.setNotes(notes)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookmarkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookmarkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}