package com.example.remoteclinic_firstdraft.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.remoteclinic_firstdraft.databinding.ConssultingRowBinding
import com.example.remoteclinic_firstdraft.databinding.ConsultCardBinding
import com.example.remoteclinic_firstdraft.model.Consultation

class PatientRequestAdapter (var clickLister:ConsultDetailsCl) : ListAdapter<Consultation, PatientRequestAdapter.ViewHolder>(
    ConslutingDiffUtil()
)
{
    class ViewHolder(var binding: ConsultCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ConsultCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var consult=getItem(position)

        holder.binding.apply {
                dateTv.text=consult.date
                stateTv.text=consult.state
                cardView.setOnClickListener {
                    clickLister.goToWritePrescription(consult)
                }
        }
    }

    interface ConsultDetailsCl{
        fun getPatientName(id:String):String
        fun goToWritePrescription(consult:Consultation)
    }


}