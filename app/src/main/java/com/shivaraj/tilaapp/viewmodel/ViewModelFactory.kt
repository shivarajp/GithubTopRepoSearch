package com.shivaraj.wednesdayapp

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shivaraj.tilaapp.repo.Repository
import com.shivaraj.tilaapp.viewmodel.MainViewModel


class ViewModelFactory(private val mDataSource: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java!!)) {
            return MainViewModel(mDataSource) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}