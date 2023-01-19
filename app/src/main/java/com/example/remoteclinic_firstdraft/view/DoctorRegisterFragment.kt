package com.example.remoteclinic_firstdraft.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.remoteclinic_firstdraft.R
import com.example.remoteclinic_firstdraft.databinding.FragmentDoctorRegisterBinding
import com.example.remoteclinic_firstdraft.model.Doctor
import com.example.remoteclinic_firstdraft.viewModel.mainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern
@AndroidEntryPoint

class DoctorRegisterFragment : Fragment() {
    lateinit var binding: FragmentDoctorRegisterBinding
    lateinit var listOfSpecialization: Spinner
    lateinit var contextD: Context
    lateinit var viewModel: mainViewModel
    var validInfo=false
    lateinit var doctors:List<Doctor>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDoctorRegisterBinding.inflate(layoutInflater)
        contextD = requireActivity()
        viewModel= ViewModelProvider(this).get(mainViewModel::class.java)
        //********************************************************************
        //Get existing doctors user to check if the user name exist before or not!
        viewModel.getDoctors().observe(viewLifecycleOwner) {
            doctors = it

        }
        //********************************************************************
        //====================================================================
        //Define variables that hold information:
        var specialization = ""
        var GPA = 0
        //====================================================================
        //Set code of Specialization spinner
        val SpecializationItem = resources.getStringArray(R.array.Specialization_items)
        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item, SpecializationItem
        )
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                specialization = SpecializationItem[position].toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    MainActivity(),
                    "Please select your Specialization",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        //====================================================================
        val GpaItem = resources.getStringArray(R.array.gpa_items)
        val adapterGpa = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item, GpaItem
        )
        binding.spinnerGpa.adapter = adapterGpa
        binding.spinnerGpa.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                GPA = GpaItem[position].toString().toInt()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    MainActivity(),
                    "Please select your Specialization",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        //Now take all values when the user clicked on signUp button:
        binding.apply {
            signUpBt.setOnClickListener {
                var username = userNameEt.text.toString()
                var firstName = firstNameEt.text.toString()
                var lastName = lastNameEt.text.toString()
                var university=universityNameET.text.toString()
                var email =emailET.text.toString()
                var password=passwordET.text.toString()
                var confirm=confirmET.text.toString()
                var gpa =gpaEt.text.toString()
                var yearOfEx= experYearEt.text.toString()
                var imageUrl= imageUrlEt.text.toString()
                validInfo=checkConstrains(username,firstName,lastName ,password,confirm, email,university,gpa,yearOfEx,imageUrl)
               if(validInfo){
                    var newDoctor=Doctor("0",username, firstName,lastName,
                    email, md5Hash(password),specialization,university, gpa.toFloat(),GPA,yearOfEx.toInt(),imageUrl,0.0f)

                    viewModel.addDoctor(newDoctor)
                    Toast.makeText(
                        contextD, "Welcome Dr.$firstName to our app",
                        Toast.LENGTH_SHORT
                    ).show()
                    resetFields()
                   DoctorPage.doctor=newDoctor
                   findNavController().navigate(R.id.action_signUpPage_to_doctorPage)

                }
                else{
                   Toast.makeText(contextD, "Pleas fill all fields correctly",
                       Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    fun checkConstrains(username:String,fName:String,lName:String,password: String,confirm:String,
                        email: String, university:String,gpa:String,experienceYear:String,image:String ):Boolean{

        if ( username.isEmpty()||fName.isEmpty()||lName.isEmpty()||password.isEmpty()||confirm.isEmpty()
            ||image.isEmpty() ||university.isEmpty()||email.isEmpty()||gpa.isEmpty()||experienceYear.isEmpty()){
            Toast.makeText(
                contextD, "Please fill all information",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }//End empty check
        else{
            return newUser(username,email)&&checkEmailConstrain(email)&&correctPassword(password,confirm)&&checkGPA(gpa)
                    &&checkExperinces(experienceYear)
        }
      return true
    }
    private fun checkEmailConstrain(email: String): Boolean {
        val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        if (EMAIL_ADDRESS_PATTERN.matcher(email).matches()) return true
        else Toast.makeText(
            contextD, "The email must follow the pattern example@organization.extention",
            Toast.LENGTH_SHORT
        ).show()
        return false
        //=====================================================================
    }//End of checkEmailConstrain
    //=========================================================================
    fun newUser(username: String,email: String):Boolean{
        var new=true
        for (user in doctors){
            println(" for (user in users), ${user.id}")
            if (username==user.username)
            {
                new=false
                Toast.makeText(contextD,"This user name  already exist, please chose other user name", Toast.LENGTH_LONG).show()
            }
            else if ( email==user.email){
                new=false
                Toast.makeText(contextD, "This user email  already exist, please chose other email address", Toast.LENGTH_LONG).show()
            }

        }//end for
        return new
    }

    //========================================================================
    fun correctPassword(p1: String, p2: String): Boolean {
        return hasLower(p1) && hasNumber(p1) && hasUpper(p1) && match(p1, p2)
    }

    private fun hasUpper(text: String): Boolean {
        for (i in 0..text.length - 1) {
            if (text[i].isUpperCase()) {
                return true
            }
        }
        Toast.makeText(
            contextD, "The password must include Upper case, lower case letter, and number",
            Toast.LENGTH_SHORT
        ).show()
        return false
    }

    private fun hasLower(text: String): Boolean {
        for (i in 0..text.length - 1) {
            if (text[i].isLowerCase()) {
                return true
            }
        }
        Toast.makeText(
            contextD, "The password must include Upper case, lower case letter, and number",
            Toast.LENGTH_SHORT
        ).show()
        return false
    }

    private fun hasNumber(text: String): Boolean {
        for (i in 0..9) {
            if (text.contains(i.toString())) {
                return true
            }
        }
        Toast.makeText(
            contextD, "The password must include Upper case, lower case letter, and number",
            Toast.LENGTH_SHORT
        ).show()
        return false
    }

    fun match(password: String, password2: String): Boolean {
        if (password == password2) {
            return true
        } else {
            Toast.makeText(contextD, "The password must match its confirm", Toast.LENGTH_SHORT)
                .show()

        }
        return false
    }

    //===========================================================
    //This function to hash password before save it in database
    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }

    fun resetFields(){//This function to   rest all fields after user signup successfully
        binding.apply {

            userNameEt.setText("")
            firstNameEt.setText("")
            lastNameEt.setText("")
            universityNameET.setText("")
            emailET.setText("")
            passwordET.setText("")
            gpaEt.setText("")
            experYearEt.setText("")
            imageUrlEt.setText("")
            confirmET.setText("")
        }//End binding block

    }

    fun checkGPA(gpa:String):Boolean{
        var valid:Boolean
        try {
            var newGpa=gpa.toFloat()
            if (newGpa>=1.0f&& newGpa<=5.0f) {
                valid=true
            }
            else{valid=false}

        }catch (e:java.lang.Exception){
            Toast.makeText(
                contextD, "please enter a valid GPA, which is a number less than or equal 5",
                Toast.LENGTH_SHORT
            ).show()
           valid= false

        }
        return valid

    }
    fun checkExperinces(year:String):Boolean{
        try {
            var years=year.toInt()
            if (years>=0){return true}
        }catch (e:java.lang.Exception){}
        Toast.makeText(contextD, "please enter a valid number in year of experiences field",
            Toast.LENGTH_SHORT).show()
        return false
    }


}