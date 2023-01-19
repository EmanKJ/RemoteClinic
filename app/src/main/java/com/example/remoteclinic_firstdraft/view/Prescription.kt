package com.example.remoteclinic_firstdraft.view

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.remoteclinic_firstdraft.R
import com.example.remoteclinic_firstdraft.databinding.FragmentPrescriptionBinding
import com.example.remoteclinic_firstdraft.model.Consultation
import com.example.remoteclinic_firstdraft.model.Medicine
import com.example.remoteclinic_firstdraft.viewModel.mainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Prescription : Fragment() {
    lateinit var binding:FragmentPrescriptionBinding
    lateinit var contextP:Context
    lateinit var medicines:MutableList<Medicine>
    lateinit var adapter: PrescriptionAdapter
    lateinit var viewModel: mainViewModel
    var written=false//to check if the doctor write prescription or not yet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentPrescriptionBinding.inflate(layoutInflater)
        viewModel= ViewModelProvider(this).get(mainViewModel::class.java)
        contextP=requireActivity()
        medicines= mutableListOf()

        adapter=PrescriptionAdapter()
        binding.apply {
            var note=consult!!.note
            if (note!=""&&note.isNotEmpty()){
                symptomsTv.text=ConsultObject.consult!!.symptoms.toString()+"\n Notes: "+note
            }
            symptomsTv.text=ConsultObject.consult!!.symptoms.toString()
            pIdTv.text="Patient: "+ConsultObject.name
            medicineRv.adapter=adapter
            addMedBt.setOnClickListener {
                //Create a new prescription and update consultation info,
                //Call add medicine dialog
                addMedicineDialog()
            }
            sendPrescBt.setOnClickListener {
                var d=dignosisEt.text.toString()
                if (d==null||d==""|| !written){
                    Toast.makeText(contextP, "Please write the diagnosis and give a treatment to the patient", Toast.LENGTH_LONG).show()
                }
                else
                {       ConsultObject.consult!!.diagnosis=d
                    viewModel.updateConsultation(ConsultObject.consult!!)
                     findNavController().navigate(R.id.action_prescription_to_doctorPage)
                }
            }
        }//end of binding block
        return binding.root
    }
fun addMedicineDialog(){
    val alertDialog= AlertDialog.Builder(contextP)
    val NameEditText= EditText(contextP)
    NameEditText.hint="Enter the name of medicine"
    //......................................................
    val doseUnitEt= EditText(contextP)
    doseUnitEt.hint="Enter the does unit (e.g spoon)"
    //......................................................
    val doseQuantityEt= EditText(contextP)
    doseQuantityEt.inputType= InputType.TYPE_NUMBER_FLAG_DECIMAL
    doseQuantityEt.hint="Enter dose quantity"
    //......................................................
    val repeatEt= EditText(contextP)
    repeatEt.inputType= InputType.TYPE_CLASS_NUMBER
    repeatEt.hint="Enter number of hours between each does"
    //......................................................
    val durationEt= EditText(contextP)
    durationEt.inputType= InputType.TYPE_CLASS_NUMBER
    durationEt.hint="Enter number of days for this treatment"
    //......................................................

    alertDialog.setTitle("Enter Your Recipe")
    val layout = LinearLayout(contextP)
    layout.orientation = LinearLayout.VERTICAL
    layout.addView(NameEditText)
    layout.addView(doseUnitEt)
    layout.addView(doseQuantityEt)
    layout.addView(repeatEt)
    layout.addView(durationEt)

    alertDialog.setView(layout)

    alertDialog.setPositiveButton("Add"){dialog, i->
        //Get what in the text view and store int:

        var name= NameEditText.text.toString()
        var doseUnit=doseUnitEt.text.toString()
        var doseQuantity=doseQuantityEt.text.toString()
        var repeat=repeatEt.text.toString()
        var duration=durationEt.text.toString()
        if (name.isEmpty() || doseUnit.isEmpty()||doseQuantity.isEmpty()||repeat.isEmpty()||duration.isEmpty()){
            Toast.makeText(contextP, "Please fill all fields", Toast.LENGTH_LONG).show()
        }
        else{
            try {
                var newMedicine=Medicine(name,doseUnit,doseQuantity.toFloat(),repeat.toInt(), duration.toInt(),ConsultObject.consult!!.id)
                medicines.add(newMedicine)
                viewModel.addMedicine(newMedicine)
                ConsultObject.consult!!.state="seen"
                adapter.submitList(medicines)
               written=true

            }
            catch (e:Exception){ Toast.makeText(contextP, "Please enter a valid number in does quantity field", Toast.LENGTH_LONG).show()}
        }

    }
        .setNegativeButton("CANCEL", DialogInterface.OnClickListener {
                dialog, id -> dialog.cancel()
        })

    alertDialog.show()

}
    companion object ConsultObject{
        var consult:Consultation? =null
        var name:String= ""

    }

    //****************************************************************
    //The following functions are used for menu


    override fun onPrepareOptionsMenu(menu: Menu) {
        val item1: MenuItem =menu!!.getItem(0)

        item1.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.doctor_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.profile->{
                findNavController().navigate(R.id.action_prescription_to_doctorPage)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}