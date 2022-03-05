package com.printf.shuttle_tracker.Model.Map.data

class Location {
    var isActive = false
    var latitude = 0.0
    var longitude = 0.0

    constructor() {}
    constructor(isActive: Boolean, latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
        this.isActive = isActive
    }
}