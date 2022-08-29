package com.example.theweatheriswhat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ListCitiesFragment : Fragment() {
    private lateinit var etCityName: EditText
    private lateinit var lvCities: ListView
    private var citiesArrayList = ArrayList<CityModel>()
    private lateinit var arrayAdapter: MyListAdapter
    private lateinit var btnAdd: Button
    private lateinit var databaseHelper: WeatherDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentView =
            inflater.inflate(R.layout.activity_list_cities_fragment, container, false)
        lvCities = fragmentView.findViewById(R.id.lvCities)
        btnAdd = fragmentView.findViewById(R.id.btnAdd)
        etCityName = fragmentView.findViewById(R.id.etCityName)

        databaseHelper = WeatherDatabaseHelper(activity)
        citiesArrayList = databaseHelper.allCities

        arrayAdapter =
            MyListAdapter(
                requireActivity(),
                databaseHelper, citiesArrayList
            )
        lvCities.adapter = arrayAdapter
        lvCities.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            (context as CitiesWeatherActivity?)!!.getClickedCityName(citiesArrayList.get(position).cityName)
        }
        btnAdd.setOnClickListener {
            var nameStr = etCityName.text.toString().trim()
            if (nameStr.isEmpty()) {
                Toast.makeText(context, "Please enter a city!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (databaseHelper.isCityNameAlreadyExisted(
                    nameStr, -1
                )
            ) {
                Toast.makeText(context, "City has already been added!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val newCityModel = CityModel(-1, etCityName.text.toString())
            val newID = databaseHelper.insertCity(newCityModel)
            newCityModel.cityID = newID
            citiesArrayList.add(0, newCityModel)
            arrayAdapter.notifyDataSetChanged()
            etCityName.setText("")
        }
        return fragmentView
    }
}