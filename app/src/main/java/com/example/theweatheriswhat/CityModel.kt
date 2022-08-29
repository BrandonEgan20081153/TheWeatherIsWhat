package com.example.theweatheriswhat

class CityModel {
    var cityID: Long = -1
    var cityName = ""

    constructor() {}
    constructor(
        cityID: Long,
        cityName: String,
    ) {
        this.cityID = cityID
        this.cityName = cityName
    }
}