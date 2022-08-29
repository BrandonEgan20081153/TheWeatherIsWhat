package com.example.theweatheriswhat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class WeatherDetailsFragment : Fragment() {
    private lateinit var tvCityName: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvSun: TextView
    private lateinit var tvTemp: TextView
    private lateinit var tvTime: TextView
    private lateinit var ivWeather: ImageView
    private lateinit var client: AsyncHttpClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val returnView =
            inflater.inflate(R.layout.activity_weather_details_fragment, container, false)
        tvCityName = returnView.findViewById(R.id.tvCityName)
        tvTemp = returnView.findViewById(R.id.tvTemp)
        tvTime = returnView.findViewById(R.id.tvTime)
        ivWeather = returnView.findViewById(R.id.ivWeather)
        tvSun = returnView.findViewById(R.id.tvSun)
        tvHumidity = returnView.findViewById(R.id.tvHumidity)
        tvStatus = returnView.findViewById(R.id.tvStatus)
        ivWeather.setImageResource(R.mipmap.ic_launcher)
        client = AsyncHttpClient()
        return returnView
    }

    fun getCityNameFromActivityAndRefreshWeatherDetails(cityName: String) {
        client["https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=562ff3771c292af98cc13dfc6a6cdf99&units=metric", object :
            JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, response: JSONObject) {
                super.onSuccess(statusCode, headers, response)
                try {
                    val cityNameFromAPIResponse = response.getString("name")
                    tvCityName.text = cityNameFromAPIResponse
                    val mainJO = response.getJSONObject("main")
                    val tempString = mainJO.getDouble("temp")
                    val humidity = mainJO.getString("humidity")
                    tvTemp.text = "$tempString C°"
                    tvHumidity.text = "Humidity: $humidity%"
                    val weatherJA = response.getJSONArray("weather")
                    val iconJO = weatherJA.getJSONObject(0)
                    val weatherIconCode = iconJO.getString("icon")
                    val weatherDescription = iconJO.getString("description")
                    tvStatus.text = weatherDescription
                    val timeMilliseconds = response.getLong("dt") * 1000
                    val sdf = SimpleDateFormat("hh:mm")
                    val formattedTime = sdf.format(Date(timeMilliseconds))
                    tvTime.text = "Last Update: $formattedTime"
                    Glide.with(activity!!)
                        .load("http://openweathermap.org/img/wn/$weatherIconCode@2x.png").into(
                            ivWeather
                        )
                    val sysJO = response.optJSONObject("sys")
                    val sunsetLong = sysJO.getLong("sunset") * 1000
                    val sunriseLong = sysJO.getLong("sunrise") * 1000
                    val formattedSunRise = sdf.format(Date(sunsetLong))
                    val formattedSunSet = sdf.format(Date(sunriseLong))
                    tvSun.text = "$formattedSunRise / $formattedSunSet"
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseString: String,
                throwable: Throwable
            ) {
                super.onFailure(statusCode, headers, responseString, throwable)
            }
        }]
    }
}