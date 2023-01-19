package com.example.remoteclinic_firstdraft.view

import androidx.recyclerview.widget.DiffUtil
import com.example.remoteclinic_firstdraft.model.Doctor




class DoctorDiffUtil: DiffUtil.ItemCallback<Doctor>() {
    override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
        return oldItem==newItem
    }
}