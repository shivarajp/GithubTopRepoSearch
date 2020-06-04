package com.shivaraj.tilaapp.repo

import android.app.Application
import com.shivaraj.tilaapp.data.local.RepoDatabase
import com.shivaraj.tilaapp.data.local.RepoModel
import com.shivaraj.tilaapp.data.models.RepoResponseModel
import com.shivaraj.tilaapp.data.remote.GithubAPIService
import com.shivaraj.tilaapp.data.remote.Resource
import com.shivaraj.tilaapp.data.remote.ResponseHandler
import com.shivaraj.tilaapp.data.remote.RetrofitGenerator

open class Repository(val app: Application) {

    val api: GithubAPIService = RetrofitGenerator.createService(
        GithubAPIService::class.java
    )
    private val responseHandler: ResponseHandler = ResponseHandler()
    private val db = RepoDatabase.getDatabase(app).songsDao()

    private val CONTENT_TYPE = "application/json"

    suspend fun getFreshRepos(
        language: String,
        since: String
    ): Resource<ArrayList<RepoResponseModel>> {

        return try {

            val list = api.getRepos(CONTENT_TYPE, language, since)
            storeInRoom(list)

            responseHandler.handleSuccess(list)

        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    private fun storeInRoom(responseData: ArrayList<RepoResponseModel>) {

        if (responseData.size > 0) {
            db.deleteAll()
            responseData.forEach {
                val eachTrack = RepoModel(
                    username = it.username,
                    name = it.username,
                    url = it.url,
                    avatar = it.avatar,
                    reponame = it.repo.name,
                    description = it.repo.description,
                    repourl = it.repo.url
                )
                db.insertAll(eachTrack)
            }
        }
    }

    fun showRepos(search: String): List<RepoModel> {

        return if (search.isNotEmpty()) {
            searchAll("%$search%")
        } else {
            getAllRepos()
        }
    }

    private fun searchAll(search: String): List<RepoModel> {

        return db.search(search)
    }

    private fun getAllRepos(): List<RepoModel> {

        return db.getAll()
    }

}