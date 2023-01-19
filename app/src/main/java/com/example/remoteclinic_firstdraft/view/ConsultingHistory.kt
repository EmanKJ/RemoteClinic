package com.example.remoteclinic_firstdraft.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.remoteclinic_firstdraft.R
import com.example.remoteclinic_firstdraft.databinding.FragmentConsultingHistoryBinding
import com.example.remoteclinic_firstdraft.model.Consultation
import com.example.remoteclinic_firstdraft.viewModel.mainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsultingHistory : Fragment(),ConsultingAdapter.ConsultClickListener{
    lateinit var binding: FragmentConsultingHistoryBinding
    lateinit var adapter:ConsultingAdapter
    lateinit var prescriptionAdapter:PrescriptionAdapter
    lateinit var viewModel: mainViewModel
     lateinit var consultations:MutableList<Consultation>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentConsultingHistoryBinding.inflate(layoutInflater)
        viewModel= ViewModelProvider(this).get(mainViewModel::class.java)
        adapter= ConsultingAdapter(this)
        prescriptionAdapter=PrescriptionAdapter()
        consultations= mutableListOf()
        binding.consultingRv.adapter=adapter
        //Get the data
        viewModel.getConsultationsP(PatientPage.patient!!.id).observe(viewLifecycleOwner){
            for(c in it){
                if (c.patientId== PatientPage.patient!!.id){
                    consultations.add(c)

                }
            }
            adapter.submitList(consultations)
        }
        binding.goToConsultPage.setOnClickListener {
            findNavController().navigate(R.id.action_consultingHistory_to_consultingPage)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onRowClick(consultation: Consultation) {
        Prescription.ConsultObject.consult=consultation
        findNavController().navigate(R.id.action_consultingHistory_to_consultationDetail)
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        val item1: MenuItem =menu!!.getItem(0)
        val item2:MenuItem=menu!!.getItem(1)

        item1.isVisible = false
        item2.isVisible=false
        super.onPrepareOptionsMenu(menu)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.patient_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.profile ->{
                findNavController().navigate(R.id.action_consultingHistory_to_patientPage)
            }


            R.id.newConsult->{
                findNavController().navigate(R.id.action_consultingHistory_to_consultingPage)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}