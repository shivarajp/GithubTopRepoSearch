package com.shivaraj.tilaapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shivaraj.tilaapp.data.models.RepoResponseModel
import com.shivaraj.tilaapp.data.remote.GithubAPIService
import com.shivaraj.tilaapp.data.remote.Resource
import com.shivaraj.tilaapp.data.remote.ResponseHandler
import com.shivaraj.tilaapp.data.remote.Status
import com.shivaraj.tilaapp.repo.Repository
import com.shivaraj.tilaapp.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.net.SocketException

class MainViewModelTest {


    @Mock
    lateinit var repository: Repository
    @Mock
    lateinit var  api: GithubAPIService
    @Mock
    lateinit var responseHandler : ResponseHandler
    @Mock
    lateinit var socketException: SocketException

    lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var searchResultResponseModel: ArrayList<RepoResponseModel>
    lateinit var  failed : Resource<ArrayList<RepoResponseModel>>
    lateinit var  success : Resource<ArrayList<RepoResponseModel>>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(repository)

        CoroutineScope(Dispatchers.IO).launch{
            mockData()
        }
    }

    @Test
    fun get_matching_address_asuccess_test() {

        with(mainViewModel){
            getFreshRepos("java", "weekly")

            assertEquals(success.status, Status.SUCCESS)
        }
    }

    @Test
    fun get_matching_address_failed_test() {

        with(mainViewModel){
            getFreshRepos("jav", ".")
            assertEquals(failed.status, Status.ERROR)
        }
    }




    suspend fun mockData() {

        failed = responseHandler.handleException(socketException)
        success = responseHandler.handleSuccess(searchResultResponseModel)


        Mockito.`when`<ArrayList<RepoResponseModel>>(
            api.getRepos(
                "application/json",
                "airtel",
                ""
            )
        ).thenReturn(searchResultResponseModel)


        Mockito.`when`<Resource<ArrayList<RepoResponseModel>>>(
             repository.getFreshRepos(
                "jz",
                "."
            )
        ).thenReturn(failed)

        Mockito.`when`<Resource<ArrayList<RepoResponseModel>>>(
            repository.getFreshRepos(
                "java",
                "weekly"
            )
        ).thenReturn(success)
    }

}