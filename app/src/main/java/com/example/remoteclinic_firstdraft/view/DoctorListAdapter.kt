package com.example.remoteclinic_firstdraft.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.remoteclinic_firstdraft.R
import com.example.remoteclinic_firstdraft.databinding.DoctorRowBinding
import com.example.remoteclinic_firstdraft.model.Doctor

class DoctorListAdapter : ListAdapter<Doctor, DoctorListAdapter.ViewHolder>(
    DoctorDiffUtil()
)
{
    class ViewHolder(var binding: DoctorRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DoctorRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var doctor=getItem(position)
        holder.binding.apply {
            var imageLink=doctor.imageUrl
//            if (imageLink.isNotEmpty()&& imageLink!="")
                try {
                    //place holder  set aa default image in case the imageUrl return wrong valu
                    val glide = Glide.with(this.root.context).load(imageLink).placeholder(R.drawable.defult1).into(profileImage)
                    if (glide.view.drawable==null)
                    {
                        profileImage.setImageResource(R.drawable.profile)
                    }
//
                }
                catch (e:Exception){
//                    Glide.with(this.root.context).load("https://t4.ftcdn.net/jpg/02/29/53/11/360_F_229531197_jmFcViuzXaYOQdoOK1qyg7uIGdnuKhpt.jpg").into(profileImage)

                }





            doctorNameTv.text="Dr. ${doctor.firstName} ${doctor.lastName}"
            specialityTv.text=doctor.specialization
            ratingBar.rating=doctor.rate



        }

    }
}