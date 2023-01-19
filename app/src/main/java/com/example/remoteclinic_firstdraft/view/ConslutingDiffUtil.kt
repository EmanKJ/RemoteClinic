package com.example.remoteclinic_firstdraft.view
import androidx.recyclerview.widget.DiffUtil
import com.example.remoteclinic_firstdraft.model.Consultation
class ConslutingDiffUtil: DiffUtil.ItemCallback<Consultation>() {
    override fun areItemsTheSame(oldItem: Consultation, newItem: Consultation): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: Consultation, newItem: Consultation): Boolean {
        return oldItem==newItem
    }
}