package com.example.theweatheriswhat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CitiesWeatherActivity extends AppCompatActivity {

    TabLayout tlWeather;
    ViewPager2 vpWeather;
    ListCitiesFragment listCitiesFragment;
    WeatherDetailsFragment weatherDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_weather);

        tlWeather = findViewById(R.id.tlWeather);
        vpWeather = findViewById(R.id.vpWeather);

        listCitiesFragment = new ListCitiesFragment();
        weatherDetailsFragment = new WeatherDetailsFragment();

        WeatherFragmentAdapter myWeathersFragmentsAdapter = new WeatherFragmentAdapter(this);
        vpWeather.setAdapter(myWeathersFragmentsAdapter);
        vpWeather.setOffscreenPageLimit(1);

        new TabLayoutMediator(tlWeather, vpWeather, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText("Cities List");
                } else if (position == 1) {
                    tab.setText("Weather Detail");
                }
            }
        }).attach();

    }

    public void getClickedCityName(String cityName) {
        vpWeather.setCurrentItem(1);
        weatherDetailsFragment.getCityNameFromActivityAndRefreshWeatherDetails(cityName);
    }

    public class WeatherFragmentAdapter extends FragmentStateAdapter {

        public WeatherFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return listCitiesFragment;
            } else if (position == 1) {
                return weatherDetailsFragment;
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}