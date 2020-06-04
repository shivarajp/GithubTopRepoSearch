package com.shivaraj.tilaapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.shivaraj.tilaapp.data.local.RepoModel
import com.shivaraj.tilaapp.data.models.RepoResponseModel
import com.shivaraj.tilaapp.data.remote.Resource
import com.shivaraj.tilaapp.repo.Repository
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val dataRepository: Repository) : ViewModel() {

    fun getFreshRepos(language: String, since: String): LiveData<Resource<ArrayList<RepoResponseModel>>> {

        return liveData(Dispatchers.IO) {
            val data =
                dataRepository.getFreshRepos(language, since)
            emit(data)
        }

    }

    fun showRepos(search: String): LiveData<List<RepoModel>> {

        return liveData(Dispatchers.IO) {
            val data =
                dataRepository.showRepos(search)
            emit(data)
        }
    }
}
