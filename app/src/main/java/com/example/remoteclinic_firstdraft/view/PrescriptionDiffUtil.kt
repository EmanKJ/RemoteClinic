package com.example.remoteclinic_firstdraft.view

import androidx.recyclerview.widget.DiffUtil
import com.example.remoteclinic_firstdraft.model.Medicine
import com.example.remoteclinic_firstdraft.model.Prescription

class PrescriptionDiffUtil: DiffUtil.ItemCallback<Medicine>(){
    override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
        return oldItem==newItem
    }
}