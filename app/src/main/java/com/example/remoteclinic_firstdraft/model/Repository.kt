package com.example.remoteclinic_firstdraft.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.remoteclinic_firstdraft.view.DoctorPage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class Repository @Inject constructor(){
    val db = Firebase.firestore
    var name=""
    private val currentAdvice: MutableLiveData<List<String>> = MutableLiveData()
    private val existingPatients: MutableLiveData<List<Patient>> = MutableLiveData()
    val doctorsLiveDate  = MutableLiveData<List<Doctor>>()
    val MedicineLiveDate  = MutableLiveData<List<Medicine>>()
    private val existingConsultation: MutableLiveData<List<Consultation>> = MutableLiveData()
    fun addPatient(patient: Patient){
        CoroutineScope(Dispatchers.IO).launch {
            val newPatient = hashMapOf(
                "id" to patient.id,
                "username" to patient.username,
                "firstName" to patient.firstName,
                "lastName" to patient.lastName,
                "email" to patient.email,
                "password" to patient.password,
                "birthYear" to patient.birthYear,
                "birthMonth" to patient.birthMonth,
                "birthDay" to patient.birthDay
            )
            db.collection("Patients").add(newPatient)
            getPatients()

        }
    }
    //=========================================================================================
    fun addDoctor(doctor: Doctor){
        CoroutineScope(Dispatchers.IO).launch {
            val newDoctor = hashMapOf(
                "id" to doctor.id,
                "username" to doctor.username,
                "firstName" to doctor.firstName,
                "lastName" to doctor.lastName,
                "email" to doctor.email,
                "password" to doctor.password,
                "specialization" to doctor.specialization,
                "university" to doctor.university,
                "gpa" to doctor.gpa,
                "standerGpa" to doctor.standerGpa,
                "yearOfExperiences" to doctor.yearOfExperiences,
                "imageUrl" to doctor.imageUrl,
                 "rate" to doctor.rate
            )

            db.collection("Doctors").add(newDoctor)

        }
    }
    //======================================================================================
    /*
    Medicine(var name:String, var doesUnit: String, var doesQuantity:Float, var repeat:Number,
                    var duration:Int, var consultationID:String)
     */
    fun addMedicine(medicine: Medicine){
        CoroutineScope(Dispatchers.IO).launch {
            val newMedicine = hashMapOf(
                "name" to medicine.name,
                "doesUnit" to medicine.doesUnit,
                "doesQuantity" to medicine.doesQuantity,
                "repeat" to medicine.repeat,
                "duration" to medicine.duration,
                "consultationID" to medicine.consultationID,

            )
            db.collection("Prescriptions").add(newMedicine)
            getPatients()

        }
    }
    //Get Medicine for specific consultation
    fun getMedicine(consultationID:String):LiveData<List<Medicine>>{
        var medicines= mutableListOf<Medicine>()
        db.collection("Prescriptions")
            .whereEqualTo("consultationID",consultationID)
            .get()
            .addOnSuccessListener {
                    result ->

                for (document in result) {
                    var id=document.id
                    var name=document.get("name").toString()
                    var doesUnit=document.get("doesUnit").toString()
                    var doesQuantity=document.get("doesQuantity").toString().toFloat()
                    var repeat=document.get("repeat").toString().toInt()
                    val duration=document.get("duration").toString().toInt()
                    medicines.add(Medicine(name,doesUnit,doesQuantity,repeat, duration, consultationID))
                }
                MedicineLiveDate.postValue(medicines)

            }

            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }

        return MedicineLiveDate


    }
    //======================================================================================
    //create function to add and get Consultations
    //Add
    fun addConsultation(consultation:Consultation){
        CoroutineScope(Dispatchers.IO).launch {
            val newConsultation = hashMapOf(
               /*
               (var id:Int, var patientId:String, var doctorId:String, var category:String,
                        var doctorName :String,var symptoms:String
                        ,var note:String,var prescriptionID:String, var date:Date,
                        var diagnosis:String,var state:String
                */
            "id" to consultation.id,
            "patientId" to consultation.patientId,
                "doctorId"  to consultation.doctorId,
                "category" to consultation.category,
                "doctorName" to consultation.doctorName,
                "symptoms" to consultation.symptoms,
                "note" to consultation.note,
                "prescriptionID" to consultation.prescriptionID,
                "date" to consultation.date,
                "diagnosis"  to consultation.diagnosis,
                "state" to consultation.state
            )

            db.collection("Consultation").add(newConsultation)

        }
    }
    //......................................................................................
    fun getConsultationsP(PatientId: String):LiveData<List<Consultation>>{
        var consultations= mutableListOf<Consultation>()
        db.collection("Consultation")
            .whereEqualTo("patientId",PatientId)
            .get()
            .addOnSuccessListener {
                    result ->

                for (document in result) {
                    var id=document.id
                    var patientId=document.get("patientId").toString()
                    var doctorId=document.get("doctorId").toString()
                    var category=document.get("category").toString()
                    var doctorName=document.get("doctorName").toString()
                    val symptoms=document.get("symptoms").toString()
                    val note=document.get("note").toString()
                    val prescriptionID=document.get("prescriptionID").toString()
                    val date=document.get("date").toString()
                    val diagnosis=document.get("diagnosis").toString()
                    val state=document.get("state").toString()
                    consultations.add(Consultation(id,patientId,doctorId,category,
                    doctorName,symptoms,note,prescriptionID,date,diagnosis,state))


                }
                existingConsultation.postValue(consultations)

            }

            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }

        return existingConsultation

    }
    //*************************************************************************************
    fun getConsultationsD(doctorID: String):LiveData<List<Consultation>>{
        var consultations= mutableListOf<Consultation>()
        db.collection("Consultation")
            .whereEqualTo("doctorId",doctorID)
            .get()
            .addOnSuccessListener {
                    result ->

                for (document in result) {
                    var id=document.id
                    var patientId=document.get("patientId").toString()
                    var doctorId=document.get("doctorId").toString()
                    var category=document.get("category").toString()
                    var doctorName=document.get("doctorName").toString()
                    val symptoms=document.get("symptoms").toString()
                    val note=document.get("note").toString()
                    val prescriptionID=document.get("prescriptionID").toString()
                    val date=document.get("date").toString()
                    val diagnosis=document.get("diagnosis").toString()
                    val state=document.get("state").toString()
                    consultations.add(Consultation(id,patientId,doctorId,category,
                        doctorName,symptoms,note,prescriptionID,date,diagnosis,state))


                }
                existingConsultation.postValue(consultations)

            }

            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }

        return existingConsultation

    }
    //======================================================================================

    fun getAdvice(): LiveData<List<String>> {

        var Advices= mutableListOf<String>()
        db.collection("Advices")
            .get()
            .addOnSuccessListener {
                    result ->

                for (document in result) {
                    var advice=document.get("Advice").toString()
                    println(advice)
                    Advices.add(advice)
                    println(Advices)

                }
                    currentAdvice.postValue(Advices)

            }

            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }


        return currentAdvice
    }

    fun getPatients():  LiveData<List<Patient>>{
        val patients= mutableListOf<Patient>()

        db.collection("Patients")
            .get()
            .addOnSuccessListener {
                    result ->
                for (document in result) {
                    val username=document.get("username").toString()
                    val firstName=document.get("firstName").toString()
                    val lastName=document.get("lastName").toString()
                    val emails=document.get("email").toString()
                    val password=document.get("password").toString()
                    val year=document.get("birthYear").toString().toInt()
                    val month=document.get("birthMonth").toString().toInt()
                    val  day = document.get("birthDay").toString().toInt()
                    patients.add(Patient(document.id,username,firstName,lastName,emails,password,year,month,day))

                }
                existingPatients.postValue(patients)

            }

            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }


        return existingPatients
    }
    //Return specific patient based on his/her id:
    suspend fun getPatient(id:String): Patient? {

        var p:Patient?=null
        db.collection("Patients").document(id)
            .get()
            .addOnSuccessListener {
                    result ->

                val username=result.get("username").toString()
                println("****************************** $username *****************************")
                val firstName=result.get("firstName").toString()
                val lastName=result.get("lastName").toString()
                val emails=result.get("email").toString()
                val password=result.get("password").toString()
                val year=result.get("birthYear").toString().toInt()
                val month=result.get("birthMonth").toString().toInt()
                val  day = result.get("birthDay").toString().toInt()
                p=Patient(result.id,username,firstName,lastName,emails,password,year,month,day)
            }

            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }
        return p
    }
    //=++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    fun getPatientName(id:String):String{

        db.collection("Patients").document(id)
             .get()
            .addOnSuccessListener {
                    result ->

                    val firstName=result.get("firstName").toString()
                    val lastName=result.get("lastName").toString()
                        name=firstName+"  "+lastName

                }

            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }
        return name
    }

    //==================================================================
    fun getDoctors(): LiveData<List<Doctor>>{
        val doctors:List<Doctor>
        doctors= arrayListOf()
        doctors.clear()

        db.collection("Doctors")
            .get()
            .addOnSuccessListener {
                    result ->
                for (document in result) {
                    val username=document.get("username").toString()
                    val firstName=document.get("firstName").toString()
                    val lastName=document.get("lastName").toString()
                    val emails=document.get("email").toString()
                    val password=document.get("password").toString()
                    val specialization=document.get("specialization").toString()
                    val university=document.get("university").toString()
                    val  gpa=document.get("gpa").toString().toFloat()
                    val standerGpa=document.get("standerGpa").toString().toInt()
                    val yearOfExperiences=document.get("yearOfExperiences").toString().toInt()
                    val imageUrl=document.get("imageUrl").toString()
                    val rate = document.get("rate").toString().toFloat()


                    doctors.add(Doctor(document.id,username,firstName,lastName,emails,password,specialization,
                        university,gpa,standerGpa, yearOfExperiences,imageUrl,rate))

                }
                doctorsLiveDate.postValue(doctors)
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }

        return doctorsLiveDate
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //===========================================================
    //This function to update consultation after doctor add the prescription:
    suspend fun updateConsultation(consult:Consultation){

    db.collection("Consultation").document(consult.id).update("prescriptionID",consult.prescriptionID)
    db.collection("Consultation").document(consult.id).update("diagnosis",consult.diagnosis)
    db.collection("Consultation").document(consult.id).update("state",consult.state)


}
    //This function to update doctor rate after patient rate them:
    suspend fun updateDoctor(id:String,rate:Float){
        var oldRate=0f
        var newRate=rate
         db.collection("Doctors").document(id).get()
            .addOnSuccessListener {
                    result ->
                oldRate=result.get("rate").toString().toFloat()
                var total=oldRate+rate
                if (total>5){newRate=total-total%5}
                else newRate=total
                db.collection("Doctors").document(id).update("rate",newRate)
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }

    }//End of  updateDoctor



}