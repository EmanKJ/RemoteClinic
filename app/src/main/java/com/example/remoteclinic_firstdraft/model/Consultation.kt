package com.example.remoteclinic_firstdraft.model

import java.util.Date

data class Consultation(var id:String, var patientId:String, var doctorId:String, var category:String,
                        var doctorName :String,var symptoms:String
                        ,var note:String,var prescriptionID:String, var date:String,
                        var diagnosis:String,var state:String)
