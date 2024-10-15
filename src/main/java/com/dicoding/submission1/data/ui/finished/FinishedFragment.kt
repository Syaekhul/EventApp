package com.dicoding.submission1.data.ui.finished

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission1.data.response.EventResponse
import com.dicoding.submission1.data.response.ListEventsItem
import com.dicoding.submission1.data.retrofit.ApiConfig
import com.dicoding.submission1.data.ui.DetailEventActivity
import com.dicoding.submission1.data.ui.FinishedEventAdapter
import com.dicoding.submission1.databinding.FragmentFinishedBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.listFinished.layoutManager = layoutManager
        getFinishedEvent()
        return binding.root
    }

    private fun getFinishedEvent(){
        showLoading(true)
        val client = ApiConfig.getApiService().getFinishedEvents()
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (isAdded) { // Check if fragment is still added to its activity
                    showLoading(false)
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody != null){
                            setFinishedData(responseBody.listEvents)
                        } else {
                            Toast.makeText(activity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                if (isAdded) {
                    showLoading(false)
                    Toast.makeText(activity, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setFinishedData(finished: List<ListEventsItem>){
        val adapter = FinishedEventAdapter(finished){ eventId ->
            toDetailEvent(eventId)
        }
        _binding?.listFinished?.adapter = adapter // Using safe call with _binding
    }

    private fun showLoading(isLoading: Boolean) {
        _binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun toDetailEvent(eventId: Int){
        val intent = Intent(requireContext(), DetailEventActivity::class.java)
        intent.putExtra("EVENT_ID", eventId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
