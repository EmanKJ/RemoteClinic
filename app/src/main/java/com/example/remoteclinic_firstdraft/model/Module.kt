package com.example.remoteclinic_firstdraft.model

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {
    //The following for firebase  data:
    @Provides
    @Singleton
    fun provideDB(context: Context):FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

}