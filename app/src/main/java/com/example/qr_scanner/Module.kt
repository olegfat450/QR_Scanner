package com.example.qr_scanner

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.qr_scanner.data.MainDb
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn (SingletonComponent::class)
object Module {

   @Provides
   @Singleton
    fun provideMainDb(app: Application): MainDb {
        return Room.databaseBuilder(app,MainDb::class.java,"products2.db").build()




    }



}