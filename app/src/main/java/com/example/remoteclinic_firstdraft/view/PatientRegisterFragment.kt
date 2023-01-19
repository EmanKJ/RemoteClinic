package com.example.remoteclinic_firstdraft.view
//==================================================================================
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
import com.example.remoteclinic_firstdraft.databinding.FragmentPatientRegisterBinding
import com.example.remoteclinic_firstdraft.model.Patient
import com.example.remoteclinic_firstdraft.viewModel.mainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigInteger
import java.security.MessageDigest
import java.util.regex.Pattern
//==================================================================================
@AndroidEntryPoint
class PatientRegisterFragment : Fragment() {
    lateinit var binding:FragmentPatientRegisterBinding
    lateinit var contextP: Context
    lateinit var viewModel: mainViewModel
    lateinit var Patients:List<Patient>
    var validInfo=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPatientRegisterBinding.inflate(layoutInflater)
        contextP=requireActivity()
        viewModel= ViewModelProvider(this).get(mainViewModel::class.java)
        viewModel.getPatients().observe(viewLifecycleOwner){
            Patients=it
        }
        //**************************************************************************
        binding.apply {
            signUpBt.setOnClickListener {
                var username = userNameEt.text.toString()
                var firstName = firstNameEt.text.toString()
                var lastName = lastNameEt.text.toString()
                var email =emailET.text.toString()
                var password=passwordET.text.toString()
                var confirm=confirmET.text.toString()
                var year=yearEt.text.toString()
                var month=monthEt.text.toString()
                var day = dayEt.text.toString()
                validInfo=checkConstrains(username,firstName,lastName ,password,confirm, email,year,month,day)
                if(validInfo){
                    var newPatient= Patient("0",username, firstName,lastName,
                        email, md5Hash(password),year.toInt(),month.toInt(),day.toInt())

                    viewModel.addPatient(newPatient)
                    Toast.makeText(
                        contextP, "Welcome $firstName to our app",
                        Toast.LENGTH_SHORT
                    ).show()
                    resetFields()
                    PatientPage.patient=newPatient
                    findNavController().navigate(R.id.action_signUpPage_to_patientPage)
                }
                else{
                    Toast.makeText(contextP, "Pleas fill all fields correctly",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
        //**************************************************************************
        // Inflate the layout for this fragment
        return binding.root
    }
//The following functions is to check the constrains of username, password, and email:
//************************************************************************
fun checkConstrains(
    username:String, fName:String, lName:String, password: String, confirm:String,
    email: String, year: String, month: String, day: String ):Boolean{

    if ( username.isEmpty()||fName.isEmpty()||lName.isEmpty()||password.isEmpty()||confirm.isEmpty()
        ||year.isEmpty()||month.isEmpty()||day.isEmpty()){
        Toast.makeText(
            contextP, "Please fill all information",
            Toast.LENGTH_SHORT
        ).show()
        return false
    }//End empty check
    else{
        return newUser(username,email)&&checkEmailConstrain(email)&&
                correctPassword(password,confirm)&&validDate(year,month,day)
    }
    return true
}
//************************************************************************
//========================================================================
private fun checkEmailConstrain(email: String): Boolean {
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+")

    if(EMAIL_ADDRESS_PATTERN.matcher(email).matches()) return true
    else  Toast.makeText(contextP,"The email must follow the pattern example@organization.extention",
        Toast.LENGTH_SHORT).show()
    return false
    //=====================================================================

}//End of checkEmailConstrain
    //========================================================================
    fun correctPassword(p1:String,p2:String):Boolean{
        return hasLower(p1)&&hasNumber(p1)&&hasUpper(p1)&&match(p1,p2)
    }
    private fun hasUpper(text: String): Boolean{
        for (i in 0..text.length-1){
            if (text[i].isUpperCase())
            {
                return true
            }
        }
        Toast.makeText(contextP,"The password must include Upper case, lower case letter, and number",
            Toast.LENGTH_SHORT).show()
        return false
    }
    private fun hasLower(text: String): Boolean{
        for (i in 0..text.length-1){
            if (text[i].isLowerCase())
            {
                return true
            }
        }
        Toast.makeText(contextP,"The password must include Upper case, lower case letter, and number",
            Toast.LENGTH_SHORT).show()
        return false
    }
    private fun hasNumber(text: String): Boolean{
        for(i in 0..9){
            if(text.contains(i.toString())){
                return true
            }
        }
        Toast.makeText(contextP,"The password must include Upper case, lower case letter, and number",
            Toast.LENGTH_SHORT).show()
        return false
    }
    fun match(password: String,password2: String):Boolean
    {
        if(password==password2){
            return true
        }
        else
        {
            Toast.makeText(contextP,"The password must match its confirm", Toast.LENGTH_SHORT).show()

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
    fun newUser(username: String,email: String):Boolean{
        var new=true
        for (user in  Patients){
            println(" for (user in users), ${user.id}")
            if (username==user.username)
            {
                new=false
                Toast.makeText(contextP,"This user name  already exist, please chose other user name", Toast.LENGTH_LONG).show()
            }
            else if ( email==user.email){
                new=false
                Toast.makeText(contextP, "This user email  already exist, please chose other email address", Toast.LENGTH_LONG).show()
            }

        }//end for
        return new
    }
    //--------------------------------------------------------------------------------------
    fun resetFields() {//This function to   rest all fields after user signup successfully
        binding.apply {
            userNameEt.setText("")
            firstNameEt.setText("")
            lastNameEt.setText("")
            emailET.setText("")
            passwordET.setText("")
            confirmET.setText("")
            yearEt.setText("")
            monthEt.setText("")
            dayEt.setText("")
        }//End binding block
    }//End of the function
    //======================================================================================
    fun validDate(year:String, month: String, day:String):Boolean{
        var valid=true
        try {
            var yearB=year.toInt()
            if (yearB<1900||yearB> 2023)
            {
                Toast.makeText(contextP,"Please enter a valid  year number (e.g 1999)", Toast.LENGTH_LONG).show()
                valid=false

            }
            var monthB=month.toInt()
            if (monthB<1 ||monthB>12){
                Toast.makeText(contextP,"Please enter a valid  month value (from 1 to 12)", Toast.LENGTH_LONG).show()
                valid=false
            }

            var dayB=day.toInt()
            if (dayB<1 ||dayB>31){
                Toast.makeText(contextP,"Please enter a valid  day value (from 1 to 12)", Toast.LENGTH_LONG).show()
                valid=false
            }
        }catch (e:java.lang.Exception){
            Toast.makeText(contextP,"Please enter your  birth date correctly, only integer accepted", Toast.LENGTH_LONG).show()
            valid=false

        }
        return valid
    }

}//End of the class