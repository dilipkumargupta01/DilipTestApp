package com.example.diliptestapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.diliptestapp.data.repositories.UserRepository


@Suppress("UNCHECKED_CAST")
class RecordViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecordViewModel(repository) as T
    }
}