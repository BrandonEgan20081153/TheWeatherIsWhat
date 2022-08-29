package com.example.theweatheriswhat

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class WeatherDatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "Create Table " + CITIES_TABLE + "(" + CITY_ID + " integer primary key autoincrement," +
                    CITY_NAME + " text)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + CITIES_TABLE)
        onCreate(db)
    }

    fun insertCity(cityModel: CityModel): Long {
        val db = writableDatabase
        val values = ContentValues()
        var result: Long = 0
        values.put(CITY_NAME, cityModel.cityName)
        result = db.insert(CITIES_TABLE, null, values)
        db.close()
        return result
    }

    fun updateCity(cityModel: CityModel): Long {
        val db = writableDatabase
        val values = ContentValues()
        var result: Long = 0
        values.put(CITY_NAME, cityModel.cityName)
        result = db.update(CITIES_TABLE, values, CITY_ID + "= ?", arrayOf(cityModel.cityID.toString()))
            .toLong()
        db.close()
        return result
    }

    fun isCityNameAlreadyExisted(cityName: String, cityID: Long): Boolean {
        val db = writableDatabase
        val itemsCursor = db.rawQuery(
            "SELECT * FROM " + CITIES_TABLE + " WHERE " + CITY_NAME + "='" + cityName + "'" +
                    " AND " + CITY_ID + "!=" + cityID, null
        )
        val count = itemsCursor.count
        db.close()
        return count > 0
    }

    val allCities: ArrayList<CityModel>
        get() {
            val db = writableDatabase
            val cityModelArrayList = ArrayList<CityModel>()
            var cityModel: CityModel? = null
            val itemsCursor = db.rawQuery(
                "SELECT * FROM " + CITIES_TABLE + " Order BY " + CITY_ID + " DESC",
                null
            )
            while (itemsCursor.moveToNext()) {
                cityModel = CityModel()
                cityModel.cityID = itemsCursor.getLong(0)
                cityModel.cityName = itemsCursor.getString(1)
                cityModelArrayList.add(cityModel)
            }
            itemsCursor.close()
            db.close()
            return cityModelArrayList
        }

    fun deleteCity(cityID: Long): Boolean {
        val db = writableDatabase
        val effectedRowsCount = db.delete(CITIES_TABLE, CITY_ID + "= ?", arrayOf(cityID.toString()))
        db.close()
        return effectedRowsCount > 0
    }

    companion object {
        //database
        private const val DATABASE_NAME = "WeatherCities.db"

        //tables
        private const val CITIES_TABLE = "cities"

        //columns
        private const val CITY_ID = "cityID"
        private const val CITY_NAME = "cityName"
    }
}