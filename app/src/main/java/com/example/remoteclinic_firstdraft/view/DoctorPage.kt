package com.example.remoteclinic_firstdraft.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.remoteclinic_firstdraft.R
import com.example.remoteclinic_firstdraft.databinding.FragmentDoctorPageBinding
import com.example.remoteclinic_firstdraft.model.Consultation
import com.example.remoteclinic_firstdraft.model.Doctor
import com.example.remoteclinic_firstdraft.viewModel.mainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorPage : Fragment(),PatientRequestAdapter.ConsultDetailsCl {
    lateinit var binding: FragmentDoctorPageBinding
    lateinit var viewModel: mainViewModel
    lateinit var adapter:PatientRequestAdapter
    lateinit var consultations:MutableList<Consultation>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentDoctorPageBinding.inflate(layoutInflater)
        viewModel= ViewModelProvider(this).get(mainViewModel::class.java)
        adapter=PatientRequestAdapter(this)
        binding.apply {
            consultRv.adapter=adapter
            signOut.setOnClickListener {
                DoctorObject.doctor = null
                findNavController().navigate(R.id.action_doctorPage_to_homePage)

            }
            welcomeTv.text=" Dr. ${doctor!!.firstName} ${doctor!!.lastName}"
            Glide.with(this.root.context).load(doctor!!.imageUrl).placeholder(R.drawable.defult1).into(imageView)
        }//end binding.apply
        //Get consultation of this doctor:
        consultations= mutableListOf()
        viewModel.getConsultationsD(DoctorObject.doctor!!.id).observe(viewLifecycleOwner) {
            for (c in it) {
                if (c.doctorId == DoctorPage.doctor!!.id) {
                    consultations.add(c)

                }
            }
            adapter.submitList(consultations)
        }
        return binding.root
    }
    companion object DoctorObject{
        var doctor:Doctor? =null
    }

    override fun getPatientName(id: String):String {
//        var name=""
//        var p=viewModel.getPatientObject(id)
//        try {
//            name = p!!.firstName+"  "+p!!.lastName
//        }catch (e:Exception){}

       return viewModel.getPatient(id)
    }

    override fun goToWritePrescription(consultation: Consultation) {
        Prescription.consult=consultation
        var name=getPatientName(consultation.id)
        Prescription.name=name
        findNavController().navigate(R.id.action_doctorPage_to_prescription)
    }

    //****************************************************************
    //The following functions are used for menu


    override fun onPrepareOptionsMenu(menu: Menu) {
        val item1: MenuItem =menu!!.getItem(1)

        item1.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.doctor_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.homePage->{
                findNavController().navigate(R.id.action_doctorPage_to_homePage)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}