package com.example.theweatheriswhat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CitiesWeatherActivity : AppCompatActivity() {
    private lateinit var tlWeather: TabLayout
    private lateinit var vpWeather: ViewPager2
    lateinit var listCitiesFragment: ListCitiesFragment
    lateinit var weatherDetailsFragment: WeatherDetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities_weather)
        tlWeather = findViewById(R.id.tlWeather)
        vpWeather = findViewById(R.id.vpWeather)
        listCitiesFragment = ListCitiesFragment()
        weatherDetailsFragment = WeatherDetailsFragment()
        val myWeathersFragmentsAdapter = WeatherFragmentAdapter(this)
        vpWeather.adapter = myWeathersFragmentsAdapter
        vpWeather.offscreenPageLimit = 1
        TabLayoutMediator(tlWeather, vpWeather) { tab, position ->
            if (position == 0) {
                tab.text = "Cities List"
            } else if (position == 1) {
                tab.text = "Weather Detail"
            }
        }.attach()
    }

    fun getClickedCityName(cityName: String?) {
        vpWeather.currentItem = 1
        cityName?.let { weatherDetailsFragment.getCityNameFromActivityAndRefreshWeatherDetails(it) }
    }

    inner class WeatherFragmentAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {
            return if (position == 0) {
                listCitiesFragment
            } else {
                weatherDetailsFragment
            }
        }

        override fun getItemCount(): Int {
            return 2
        }
    }
}