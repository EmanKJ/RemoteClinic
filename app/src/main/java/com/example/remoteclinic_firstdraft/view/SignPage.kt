package com.example.remoteclinic_firstdraft.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.remoteclinic_firstdraft.R
import com.example.remoteclinic_firstdraft.databinding.FragmentSignPageBinding
import com.example.remoteclinic_firstdraft.model.Doctor
import com.example.remoteclinic_firstdraft.model.Patient
import com.example.remoteclinic_firstdraft.viewModel.mainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigInteger
import java.security.MessageDigest

@AndroidEntryPoint
class SignPage : Fragment() {
    lateinit var binding: FragmentSignPageBinding
    lateinit var viewModel: mainViewModel
    lateinit var Patients:List<Patient>
    lateinit var doctors:List<Doctor>
    lateinit var contextS:Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentSignPageBinding.inflate(layoutInflater)
        viewModel= ViewModelProvider(this).get(mainViewModel::class.java)
        contextS=requireActivity()
        binding.signUpBt.setOnClickListener {
            findNavController().navigate(R.id.action_signPage_to_signUpPage)
        }
        if(userData.role=="Patients"){
            binding.fistWelcome.text="Welcome, we hope you are in a good health our dear patient"
            //get all patents data from database
            viewModel.getPatients().observe(viewLifecycleOwner){
                Patients=it
            }
            binding.apply {
                loginBt.setOnClickListener {
                    var exixst=false//if  the user exisit or not
                    var username=userNameEt.text.toString()
                    var password=passwordEt.text.toString()
                    if (username.isEmpty()||password.isEmpty()){
                        Toast.makeText(contextS, "Pleas enter your username and password",
                            Toast.LENGTH_SHORT).show()
                    }
                    else {
                        for (user in Patients) {

                            if (user.username==username){
                                exixst=true
                                if (user.password==md5Hash(password))//log in
                                {
                                       PatientPage.PatientObject.patient=user
                                    findNavController().navigate(R.id.action_signPage_to_patientPage)
                                }
                                    else
                                {
                                    Toast.makeText(contextS, "Pleas enter correct user name and password",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }//End for loop
                        if (!exixst){
                            Toast.makeText(contextS, "Sorry, this user did not exist",
                                Toast.LENGTH_SHORT).show()
                        }
                    }//end of else
                }//End of button click listener
            }//end of binding
        }
        else{
            binding.fistWelcome.text="Welcome, we hope you in a good health our dear doctor"
            viewModel.getDoctors().observe(viewLifecycleOwner) {

                doctors = it

            }

            binding.apply {
                loginBt.setOnClickListener {
                    var exixst=false//if  the user exisit or not
                    var username=userNameEt.text.toString()
                    var password=passwordEt.text.toString()
                    if (username.isEmpty()||password.isEmpty()){
                        Toast.makeText(contextS, "Pleas enter your username and password",
                            Toast.LENGTH_SHORT).show()
                    }
                    else {
                        for (user in doctors) {

                            if (user.username==username){
                                exixst=true
                                if (user.password==md5Hash(password))//log in
                                {
                                    DoctorPage.doctor=user
                                    findNavController().navigate(R.id.action_signPage_to_doctorPage)
                                }
                                else
                                {
                                    Toast.makeText(contextS, "Pleas enter correct user name and password",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }//End for loop
                        if (!exixst){
                            Toast.makeText(contextS, "Sorry, this user did not exist",
                                Toast.LENGTH_SHORT).show()
                        }
                    }//end of else
                }//End of button click listener
            }//end of binding
        }
        return binding.root
    }
    //Set the role of the user -> if the user clicked on doctor icon, then the role is doctor,
    // else if the user clicked on normal login icon-> the role is patient
    companion object userData{
        var role: String? =null
    }

    //===========================================================
    //This function to hash password before save it in database
    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }


}