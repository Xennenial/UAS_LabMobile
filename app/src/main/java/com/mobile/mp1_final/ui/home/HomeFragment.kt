package com.mobile.mp1_final.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.mp1_final.R
import com.mobile.mp1_final.adapter.DigimonAdapter
import com.mobile.mp1_final.api.ApiConfig
import com.mobile.mp1_final.api.HarryPotterResponseItem
import com.mobile.mp1_final.databinding.HomeFragmentBinding
import com.mobile.mp1_final.repository.NoteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var agentsAdapter: DigimonAdapter
    private lateinit var binding: HomeFragmentBinding
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
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = view.findViewById(R.id.digimon_recycler_view)
        val application = requireNotNull(this.activity).application
        noteRepository = NoteRepository(application)
        agentsAdapter = DigimonAdapter(ArrayList(), noteRepository)
        recyclerView.adapter = agentsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        getAgents()

        return view
    }

    private fun getAgents() {
        val client = ApiConfig.getApiService().getAgents()

        client.enqueue(object:Callback<List<HarryPotterResponseItem>>{
            override fun onResponse(
                call: Call<List<HarryPotterResponseItem>>,
                response: Response<List<HarryPotterResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val adapter = responseBody?.let { DigimonAdapter(it, noteRepository) }
                    binding.digimonRecyclerView.adapter = adapter
                }
                else {
                    Log.e("Home Fragment" , "network call failure on response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<HarryPotterResponseItem>>, t: Throwable) {
                Log.e("Home Fragment","network call failure: ${t.message}")
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}