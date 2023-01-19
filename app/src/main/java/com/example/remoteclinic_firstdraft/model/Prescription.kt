package com.example.remoteclinic_firstdraft.model

import com.google.android.material.shape.RoundedCornerTreatment
import java.util.Date

//data class Prescription(var prescriptionID:String, var date: Date,var treatment:List<Medicine>)
data class Prescription(var prescriptionID:String, var date: Date,var consultationID:String)
