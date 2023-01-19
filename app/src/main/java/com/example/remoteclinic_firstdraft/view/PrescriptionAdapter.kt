package com.example.remoteclinic_firstdraft.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.remoteclinic_firstdraft.databinding.PrescriptionRowBinding
import com.example.remoteclinic_firstdraft.model.Medicine
import com.example.remoteclinic_firstdraft.model.Prescription

class PrescriptionAdapter: ListAdapter<Medicine, PrescriptionAdapter.ViewHolder>(
    PrescriptionDiffUtil()
) {
    class ViewHolder(var binding: PrescriptionRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return PrescriptionAdapter.ViewHolder(PrescriptionRowBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       var medicine=getItem(position)
        holder.binding.apply {
            nameTv.text=medicine.name
            doesTv.text =medicine.doesQuantity.toString()+" : "+medicine.doesUnit
            repeatTv.text="Every "+medicine.repeat.toString()+" hours"
            durationTv.text="For "+medicine.duration.toString()+" days"
        }


    }
}