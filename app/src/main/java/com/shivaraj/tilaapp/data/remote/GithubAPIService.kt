package com.shivaraj.tilaapp.data.remote
import com.shivaraj.tilaapp.data.models.RepoResponseModel
import okhttp3.ResponseBody
import retrofit2.http.*


interface GithubAPIService {

    @Headers("Accept: application/json")
    @GET("developers")
    suspend fun getRepos(
        @Header("Content-Type") contentType: String,
        @Query("language") language : String,
        @Query("since") since : String
    ): ArrayList<RepoResponseModel>

}