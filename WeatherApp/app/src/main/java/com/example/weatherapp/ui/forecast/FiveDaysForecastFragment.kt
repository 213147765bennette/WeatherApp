package com.example.weatherapp.ui.forecast

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.ui.home.HomeViewModel

class FiveDaysForecastFragment : Fragment() {

    companion object {
        fun newInstance() = FiveDaysForecastFragment()
    }

    private lateinit var viewModel: FiveDaysForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel =
            ViewModelProvider(this).get(FiveDaysForecastViewModel::class.java)
        val root = inflater.inflate(R.layout.five_days_forecast_fragment, container, false)

        

        return root
    }


}