package com.example.remoteclinic_firstdraft.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.remoteclinic_firstdraft.R
import com.example.remoteclinic_firstdraft.databinding.FragmentConsultingPageBinding
import com.example.remoteclinic_firstdraft.model.Consultation
import com.example.remoteclinic_firstdraft.model.Doctor
import com.example.remoteclinic_firstdraft.viewModel.mainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class ConsultingPage : Fragment() {
    lateinit var binding: FragmentConsultingPageBinding
    lateinit var viewModel: mainViewModel
    lateinit var doctors:List<Doctor>
    lateinit var contectC:Context
    lateinit var categoryDoctor: MutableList<Doctor>
    lateinit var DoctorsName:MutableList<String>
    lateinit var doctor:Doctor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentConsultingPageBinding.inflate(layoutInflater)
        contectC=requireActivity()
        viewModel= ViewModelProvider(this).get(mainViewModel::class.java)
        categoryDoctor= mutableListOf()
        DoctorsName= mutableListOf()
        doctors= listOf()
        //********************************************************************
        //Get existing doctors user to check if the user name exist before or not!
        viewModel.getDoctors().observe(viewLifecycleOwner) {
            doctors = it

        }

        var specialization = ""
        //====================================================================
        //Set code of Specialization spinner
        val SpecializationItem = resources.getStringArray(R.array.Specialization_items)
        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item, SpecializationItem
        )
        binding.spinnerGategory.adapter = adapter
        binding.spinnerGategory.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                categoryDoctor.clear()
                DoctorsName.clear()
                specialization = SpecializationItem[position].toString()
                for (d in doctors){
                    if (d.specialization==specialization){
                        categoryDoctor.add(d)
                        DoctorsName.add("Dr. ${d.firstName} ${d.lastName}")


                    }
                }

                //====================================================================
                //Set value of doctors spinner


                if (categoryDoctor.size >=1) {
                    binding.layout2.visibility=View.VISIBLE
                    binding.sendBt.isEnabled=true
                    val adapterdoctor = ArrayAdapter(
                        requireActivity(),
                        android.R.layout.simple_spinner_item, DoctorsName
                    )
                    binding.spinnerDoctors.adapter = adapterdoctor
                    binding.spinnerDoctors.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View, position: Int, id: Long
                        ) {
                            doctor = categoryDoctor[position]



                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            Toast.makeText(
                                MainActivity(),
                                "Please select a doctor",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }
                else
                { binding.layout2.visibility=View.GONE
                    Toast.makeText(
                        contectC,
                        "There is no doctor in ${specialization} department, we apologize from you",
                        Toast.LENGTH_LONG
                    ).show()
                       binding.sendBt.isEnabled=false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    contectC,
                    "Please select a Category",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.apply {
            sendBt.setOnClickListener {
                var symptoms= symptomsEt.text.toString()
                var note= noteEt.text.toString()
                if (doctor==null)//Chech if the patient fill all fields
             {  Toast.makeText(
                 contectC,
                 "Please select doctor name",
                 Toast.LENGTH_LONG
             ).show()
                }
                else if (symptomsEt.text.isEmpty()||symptoms==""){
                    Toast.makeText(
                        contectC,
                        "Please write the symptoms",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else//in this case the patient selected  doctor name and wrote the symptoms, so we will send the consulting
                {
                    /*
             (var id:Int, var patientId:String, var doctorId:String, var category:String,
                      var doctorName :String,var symptoms:String
                      ,var note:String,var prescriptionID:String, var date:Date,
                      var diagnosis:String,var state:String
              */
                    val c: Date = Calendar.getInstance().getTime()
                    println("Current time => $c")
                    val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
                    val formattedDate: String = df.format(c)

                    var newConsultation=Consultation("0", PatientPage.patient!!.id,doctor.id,
                        specialization,doctor.firstName+" "+doctor.lastName,symptoms,
                        note,"0", formattedDate,
                        "Not specified","Pending"
                    )
                    Toast.makeText(
                        contectC,
                        "The consulting added successfully",
                        Toast.LENGTH_LONG
                    ).show()

                    viewModel.addConsultation(newConsultation)
                    findNavController().navigate(R.id.action_consultingPage_to_consultingHistory)
                }
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}