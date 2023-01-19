package com.example.remoteclinic_firstdraft.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.remoteclinic_firstdraft.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class mainViewModel @Inject constructor(val repository: Repository): ViewModel() {


    public fun getAdvices(): LiveData<List<String>> {
        return repository.getAdvice()

    }

    fun addPatient(patient: Patient) {
        repository.addPatient(patient)
    }

    fun addDoctor(doctor: Doctor) {
        repository.addDoctor(doctor)
    }

    fun getDoctors(): LiveData<List<Doctor>> {
        return repository.getDoctors()
    }

    fun getPatients(): LiveData<List<Patient>> {
        return repository.getPatients()
    }

    fun addConsultation(consultation:Consultation){
        repository.addConsultation(consultation)

    }
    fun getConsultationsP(PID:String):LiveData<List<Consultation>>{
        return repository.getConsultationsP(PID)
    }
    fun getConsultationsD(DID:String):LiveData<List<Consultation>>{
        return repository.getConsultationsD(DID)
    }
    fun getPatient(id:String):String{
        return repository.getPatientName(id)
    }
    fun getPatientObject(id:String):Patient?{
        var P:Patient?=null

      CoroutineScope(Dispatchers.IO).launch {
          async {
              P = repository.getPatient(id)
          }.await()
        }
        return P
    }
    //Update Consultation:
    fun updateConsultation(consultation: Consultation){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateConsultation(consultation)
        }
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    fun addMedicine(medicine: Medicine){
        repository.addMedicine(medicine)
    }
    fun getMedicine(consultationId:String):LiveData<List<Medicine>>{
        return repository.getMedicine(consultationId)
    }

    fun updateDoctor(id: String,rate:Float){
        CoroutineScope(Dispatchers.IO).launch {
             repository.updateDoctor(id,rate)
        }
    }

}//End of the class