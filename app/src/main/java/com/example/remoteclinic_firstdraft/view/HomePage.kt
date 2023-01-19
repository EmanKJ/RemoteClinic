package com.example.remoteclinic_firstdraft.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.remoteclinic_firstdraft.R
import com.example.remoteclinic_firstdraft.databinding.FragmentHomePageBinding
import com.example.remoteclinic_firstdraft.model.Doctor
import com.example.remoteclinic_firstdraft.viewModel.mainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class HomePage : Fragment() {
    lateinit var binding: FragmentHomePageBinding
    lateinit var viewModel:mainViewModel
    lateinit var adapter: DoctorListAdapter
    lateinit var doctors:List<Doctor>

//
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    if(DoctorPage.doctor!=null ||PatientPage.patient!=null){
        setHasOptionsMenu(true)
    }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //var advice= mutableListOf<String>()
        binding=FragmentHomePageBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        viewModel= ViewModelProvider(this).get(mainViewModel::class.java)
        //getting advices, then chose random advice and assign it to the  first text view
        viewModel.getAdvices().observe(viewLifecycleOwner,{
            var index=  Random.nextInt(0, it.size)
            var myAdvice= it.get(index)
            binding.adviceTV.text=myAdvice
        }
        )
        // go to sign fragment when the user click on sign icon:
        binding.loginBt.setOnClickListener{
            SignPage.userData.role="Patients"
            findNavController().navigate(R.id.action_homePage_to_signPage)
        }
        binding.doctorLogBt.setOnClickListener {
            SignPage.userData.role="Doctors"
            findNavController().navigate(R.id.action_homePage_to_signPage)
        }

        //====================================================
        //The following code for adapter to load list od doctors
        adapter= DoctorListAdapter()
        binding.doctorRv.adapter=adapter
        viewModel.getDoctors().observe(viewLifecycleOwner) {
            adapter.submitList(it)

        }
        if(DoctorPage.doctor!=null ||PatientPage.patient!=null){//In this case some user already longed in
            binding.apply {
                logIcons.visibility=View.INVISIBLE
            }

        }


        return binding.root
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        val item1: MenuItem =menu!!.getItem(0)
        val item2: MenuItem =menu!!.getItem(1)
        val item3: MenuItem =menu!!.getItem(2)

        item1.isVisible = false
        item2.isVisible=false
        item3.isVisible=false
        super.onPrepareOptionsMenu(menu)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.patient_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){


            R.id.profile ->{
                if (PatientPage.patient!=null){
                findNavController().navigate(R.id.action_homePage_to_patientPage)
                }else{
                    findNavController().navigate(R.id.action_homePage_to_doctorPage)
                }
            }



        }
        return super.onOptionsItemSelected(item)
    }

}


