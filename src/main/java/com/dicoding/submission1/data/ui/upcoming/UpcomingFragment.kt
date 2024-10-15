package com.dicoding.submission1.data.ui.upcoming

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
import com.dicoding.submission1.data.ui.UpcomingEventAdapter
import com.dicoding.submission1.databinding.FragmentUpcomingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.listUpcoming.layoutManager = layoutManager
        getUpcomingEvent()

        return binding.root
    }

    private fun getUpcomingEvent() {
        showLoading(true)
        val client = ApiConfig.getApiService().getActiveEvents()

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (isAdded) { // Check if the fragment is still added to its activity
                    showLoading(false)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            setUpComingData(responseBody.listEvents)
                        }
                    } else {
                        Toast.makeText(activity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
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

    private fun setUpComingData(upComing: List<ListEventsItem>) {
        val adapter = UpcomingEventAdapter(upComing) { eventId ->
            toDetailEvent(eventId)
        }
        _binding?.listUpcoming?.adapter = adapter // Safe access using _binding
    }

    private fun showLoading(isLoading: Boolean) {
        _binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE // Safe access using _binding
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
