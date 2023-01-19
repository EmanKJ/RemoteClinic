package com.example.remoteclinic_firstdraft.model

data class Doctor(
    var id:String, var username:String, var firstName:String, var lastName:String,
    var email:String, var password:String, var specialization:String,
    var university:String, var gpa: Float, var standerGpa:Int, var yearOfExperiences: Int,
    var imageUrl:String, var rate:Float)