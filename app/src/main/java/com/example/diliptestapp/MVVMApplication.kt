package com.example.diliptestapp

import android.app.Application
import com.example.diliptestapp.data.db.AppDatabase
import com.example.diliptestapp.data.network.MyUserApi
import com.example.diliptestapp.data.network.NetworkConnectionInterceptor
import com.example.diliptestapp.data.preferences.PreferenceProvider
import com.example.diliptestapp.data.repositories.UserRepository
import com.example.diliptestapp.ui.RecordViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }


        bind() from singleton { MyUserApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }

        bind() from provider { RecordViewModelFactory(instance()) }

        bind() from singleton { UserRepository(instance(), instance(), instance()) }


    }

}