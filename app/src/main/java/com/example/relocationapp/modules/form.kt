package com.example.relocationapp.modules

import java.util.Date

class Form{
    var pickupLocation:String=""
    var dropOffLocation:String=""
    var selectedDate: Date?=null
    var selectedVehicleType:String=""

    constructor(pickupLocation:String,dropOffLocation:String,selectedDate: Date?,selectedVehicleType:String){
        this.pickupLocation=pickupLocation
        this.dropOffLocation=dropOffLocation
        this.selectedDate=selectedDate
        this.selectedVehicleType=selectedVehicleType
    }
}