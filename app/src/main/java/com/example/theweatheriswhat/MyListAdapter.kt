package com.example.theweatheriswhat

import android.app.Activity
import android.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class MyListAdapter(
    val context: Activity,
    val weatherDatabaseHelper: WeatherDatabaseHelper,
    val arrayOfCityModels: ArrayList<CityModel>
) :
    BaseAdapter() {

    override fun getCount(): Int {
        return arrayOfCityModels.size
    }

    override fun getItem(position: Int): CityModel {
        return arrayOfCityModels.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_cities_list, null, true)

        val tvCityName = rowView.findViewById(R.id.tvCityName) as TextView
        val tvDelete = rowView.findViewById(R.id.tvDelete) as TextView
        val tvEdit = rowView.findViewById(R.id.tvEdit) as TextView

        tvCityName.text = getItem(position).cityName

        tvDelete.setOnClickListener {
            weatherDatabaseHelper.deleteCity(arrayOfCityModels.get(position).cityID)
            arrayOfCityModels.removeAt(position)
            notifyDataSetChanged()
        }
        tvEdit.setOnClickListener {
            val alert = AlertDialog.Builder(context)
            val edittext = EditText(context)
            alert.setTitle("Edit City Name")

            alert.setView(edittext)
            edittext.setText(arrayOfCityModels.get(position).cityName)

            alert.setPositiveButton("Update"
            ) { dialog, whichButton -> //What ever you want to do with the value
                val newName = edittext.text.toString().trim()
                if (newName.isEmpty()) {
                    Toast.makeText(context, "Enter name!", Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }
                if (weatherDatabaseHelper.isCityNameAlreadyExisted(
                        newName,
                        arrayOfCityModels.get(position).cityID
                    )
                ) {
                    Toast.makeText(context, "Name already exists!", Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }
                arrayOfCityModels.get(position).cityName = newName
                weatherDatabaseHelper.updateCity(arrayOfCityModels.get(position))
                notifyDataSetChanged()
            }

            alert.setNegativeButton("Cancel"
            ) { dialog, whichButton ->
                dialog.dismiss()
            }
            alert.show()
        }

        return rowView
    }
}