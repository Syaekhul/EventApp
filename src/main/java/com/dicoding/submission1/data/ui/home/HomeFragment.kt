package com.dicoding.submission1.data.ui.home

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
import com.dicoding.submission1.data.ui.UpcomingEventAdapter
import com.dicoding.submission1.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Set up RecyclerViews
        binding.listFinishedHome.layoutManager = LinearLayoutManager(requireContext())
        binding.listUpComingHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Load data for both sections
        getFinishedEventHome()
        getUpComingEventHome()

        return binding.root
    }

    private fun getFinishedEventHome() {
        showLoading(true, isUpcoming = false)
        val client = ApiConfig.getApiService().getFinishedEvents()

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (isAdded) {  // Check if the fragment is still attached
                    showLoading(false, isUpcoming = false)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            val latestEvent = responseBody.listEvents.take(5)
                            setFinishedData(latestEvent)
                        } else {
                            Toast.makeText(activity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                if (isAdded) {
                    showLoading(false, isUpcoming = false)
                    Toast.makeText(activity, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getUpComingEventHome() {
        showLoading(true, isUpcoming = true)
        val client = ApiConfig.getApiService().getActiveEvents()

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (isAdded) {
                    showLoading(false, isUpcoming = true)
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && responseBody.listEvents.isNotEmpty()) {
                            val upcomingEvents = responseBody.listEvents.take(5)
                            setUpcomingData(upcomingEvents)
                        } else {
                            Toast.makeText(activity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                if (isAdded) {
                    showLoading(false, isUpcoming = true)
                    Toast.makeText(activity, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setFinishedData(finished: List<ListEventsItem>) {
        val adapter = FinishedEventAdapter(finished) { eventId ->
            toDetailEvent(eventId)
        }
        binding.listFinishedHome.adapter = adapter
    }

    private fun setUpcomingData(upcoming: List<ListEventsItem>) {
        val adapter = UpcomingEventAdapter(upcoming) { eventId ->
            toDetailEvent(eventId)
        }
        binding.listUpComingHome.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean, isUpcoming: Boolean) {
        _binding?.let { binding ->
            if (isUpcoming) {
                binding.progressBarUpcoming.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            } else {
                binding.progressBarFinish.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    private fun toDetailEvent(eventId: Int) {
        val intent = Intent(requireContext(), DetailEventActivity::class.java)
        intent.putExtra("EVENT_ID", eventId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
