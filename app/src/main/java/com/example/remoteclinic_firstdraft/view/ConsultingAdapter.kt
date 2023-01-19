package com.example.remoteclinic_firstdraft.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.remoteclinic_firstdraft.databinding.ConssultingRowBinding
import com.example.remoteclinic_firstdraft.databinding.DoctorRowBinding
import com.example.remoteclinic_firstdraft.model.Consultation
import com.example.remoteclinic_firstdraft.model.Doctor

class ConsultingAdapter (var consultClickListener: ConsultClickListener) : ListAdapter<Consultation, ConsultingAdapter.ViewHolder>(
    ConslutingDiffUtil()
)
{
    class ViewHolder(var binding: ConssultingRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ConsultingAdapter.ViewHolder(ConssultingRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var consult=getItem(position)
        holder.binding.apply {
            categoryTv.text=consult.category
            nameTv.text= "Dr. ${consult.doctorName}"
            dateTv.text=consult.date.toString()
            dignosisTv.text=consult.diagnosis.toString()
            stateTv.text=consult.state
            consultationRow.setOnClickListener{
                consultClickListener.onRowClick(consult)
            }
        }
    }


    interface ConsultClickListener{
        fun onRowClick(consultation: Consultation)
    }

}