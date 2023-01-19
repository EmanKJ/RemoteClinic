package com.example.remoteclinic_firstdraft.model

data class Patient (var id:String, var username:String, var firstName:String, var lastName:String, var email:String, var password:String,
            val birthYear:Int, val birthMonth:Int, val birthDay:Int)