package com.example.remoteclinic_firstdraft.view

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.remoteclinic_firstdraft.databinding.FragmentSignPageBinding
import com.example.remoteclinic_firstdraft.databinding.FragmentSignUpPageBinding
import com.google.android.material.tabs.TabLayout


class SignUpPage : Fragment() {

    lateinit var binding: FragmentSignUpPageBinding
    lateinit var tabLayout:TabLayout
    lateinit var viewPager2: ViewPager2
    lateinit var adpter:PageAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSignUpPageBinding.inflate(layoutInflater)
        tabLayout=binding.tabLayout
        viewPager2=binding.viewpager
        adpter= PageAdapter(requireActivity().supportFragmentManager,lifecycle)
        tabLayout.addTab(tabLayout.newTab().setText("Doctor"))
        tabLayout.addTab(tabLayout.newTab().setText("Patient"))
        viewPager2.adapter=adpter

        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab!=null){
                    viewPager2.currentItem=tab.position
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        viewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })



        return binding.root

    }


}