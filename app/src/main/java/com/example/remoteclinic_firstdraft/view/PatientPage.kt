package com.example.remoteclinic_firstdraft.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.remoteclinic_firstdraft.R
import com.example.remoteclinic_firstdraft.databinding.FragmentPatientPageBinding
import com.example.remoteclinic_firstdraft.model.Patient


class PatientPage : Fragment() {

    lateinit var binding:FragmentPatientPageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentPatientPageBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding.apply {
        welcomeTv.text = "Welcome ${patient?.firstName}"
        nameTv.text = "Name: ${patient?.firstName} ${patient?.lastName}"
        dateOfBirthTv.text =
            " ${patient?.birthDay} / ${patient?.birthMonth} / ${patient?.birthYear}"
        goToConsultPage.setOnClickListener {
                findNavController().navigate(R.id.action_patientPage_to_consultingHistory)
        }
            signOut.setOnClickListener {
                patient =null
                findNavController().navigate(R.id.action_patientPage_to_homePage)
            }
    }//End binding blocck

        return binding.root
    }

    companion object PatientObject{
        var patient: Patient? =null
    }

    //****************************************************************
    //The following functions are used for menu


    override fun onPrepareOptionsMenu(menu: Menu) {
        val item1: MenuItem =menu!!.getItem(3)

        item1.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.patient_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.homePage->{
                findNavController().navigate(R.id.action_patientPage_to_homePage)
                return true
            }
           R.id.consultHistory->{
               findNavController().navigate(R.id.action_patientPage_to_consultingHistory)
           }
            R.id.newConsult->{
                findNavController().navigate(R.id.action_patientPage_to_consultingPage)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}