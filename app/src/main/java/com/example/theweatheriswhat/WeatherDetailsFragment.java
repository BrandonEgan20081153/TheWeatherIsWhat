package com.example.theweatheriswhat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class WeatherDetailsFragment extends Fragment {

    TextView tvCityName, tvStatus, tvHumidity, tvSun, tvTemp, tvTime;
    ImageView ivWeather;
    AsyncHttpClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View returnView = inflater.inflate(R.layout.activity_weather_details_fragment, container, false);

        tvCityName = returnView.findViewById(R.id.tvCityName);
        tvTemp = returnView.findViewById(R.id.tvTemp);
        tvTime = returnView.findViewById(R.id.tvTime);
        ivWeather = returnView.findViewById(R.id.ivWeather);
        tvSun = returnView.findViewById(R.id.tvSun);
        tvHumidity = returnView.findViewById(R.id.tvHumidity);
        tvStatus = returnView.findViewById(R.id.tvStatus);

        ivWeather.setImageResource(R.mipmap.ic_launcher);
        client = new AsyncHttpClient();
        return returnView;
    }

    public void getCityNameFromActivityAndRefreshWeatherDetails(String cityName) {
        client.get("https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=562ff3771c292af98cc13dfc6a6cdf99", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String cityNameFromAPIResponse = response.getString("name");
                    tvCityName.setText(cityNameFromAPIResponse);

                    JSONObject mainJO = response.getJSONObject("main");
                    double tempString = mainJO.getDouble("temp");

                    String humidity = mainJO.getString("humidity");

                    tvTemp.setText(tempString + " C");
                    tvHumidity.setText("Humidity: " + humidity + "%");

                    JSONArray weatherJA = response.getJSONArray("weather");
                    JSONObject iconJO = weatherJA.getJSONObject(0);

                    String weatherIconCode = iconJO.getString("icon");
                    String weatherDescription = iconJO.getString("description");
                    tvStatus.setText(weatherDescription);

                    long timeMilliseconds = response.getLong("dt") * 1000;

                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                    String formattedTime = sdf.format(new Date(timeMilliseconds));

                    tvTime.setText("Last Update: " + formattedTime);

                    Glide.with(getActivity()).load("http://openweathermap.org/img/wn/" + weatherIconCode + "@2x.png").into(ivWeather);

                    JSONObject sysJO = response.optJSONObject("sys");
                    long sunsetLong = sysJO.getLong("sunset") * 1000;
                    long sunriseLong = sysJO.getLong("sunrise") * 1000;
                    String formattedSunRise = sdf.format(new Date(sunsetLong));
                    String formattedSunSet = sdf.format(new Date(sunriseLong));

                    tvSun.setText(formattedSunRise + " / " + formattedSunSet);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}