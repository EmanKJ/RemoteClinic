package com.example.remoteclinic_firstdraft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.remoteclinic_firstdraft.databinding.FragmentConsultationDetailBinding
import com.example.remoteclinic_firstdraft.model.Doctor
import com.example.remoteclinic_firstdraft.view.DoctorPage
import com.example.remoteclinic_firstdraft.view.Prescription
import com.example.remoteclinic_firstdraft.view.PrescriptionAdapter
import com.example.remoteclinic_firstdraft.viewModel.mainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsultationDetail : Fragment() {
    lateinit var binding:FragmentConsultationDetailBinding
    lateinit var viewModel: mainViewModel
    lateinit var adapter: PrescriptionAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentConsultationDetailBinding.inflate(layoutInflater)
        viewModel= ViewModelProvider(this).get(mainViewModel::class.java)
        adapter=PrescriptionAdapter()
        var consult=Prescription.ConsultObject.consult!!
        //Get doctor to update rating value



        binding.apply {
            categoryTv.text=consult.category
            drNameTv.text="Dr. "+consult.doctorName
            symptomsTv.text=consult.symptoms
            diagnosisTv.text=consult.diagnosis
            dateTv.text=consult.date
            stateTv.text=consult.state

            if (consult.state=="seen"){//In this case the doctor wrote the prescription
                table2.visibility=View.VISIBLE
                prescriptionRv.adapter=adapter
                viewModel.getMedicine(consult.id).observe(viewLifecycleOwner){
                    adapter.submitList(it)

                }
                binding.ratingBar.numStars=5




            }

                sendRateBt.setOnClickListener {
                    var newRate = binding.ratingBar.rating.toString().toFloat()
                    println("===============n${newRate}============")
                    viewModel.updateDoctor(consult!!.doctorId,newRate)

                }
                backBt.setOnClickListener {
                    findNavController().navigate(R.id.action_consultationDetail_to_consultingHistory)
                }



        }
        return binding.root
    }


}