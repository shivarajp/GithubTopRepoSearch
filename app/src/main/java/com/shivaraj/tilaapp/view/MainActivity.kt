package com.shivaraj.tilaapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.shivaraj.tilaapp.R
import com.shivaraj.tilaapp.data.local.RepoDAO
import com.shivaraj.tilaapp.data.local.RepoDatabase
import com.shivaraj.tilaapp.data.local.RepoModel
import com.shivaraj.tilaapp.data.remote.Status
import com.shivaraj.tilaapp.repo.Repository
import com.shivaraj.tilaapp.viewmodel.MainViewModel
import com.shivaraj.wednesdayapp.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnRowClickListener {


    lateinit var viewMode: MainViewModel
    lateinit var dataRepository: Repository
    lateinit var db: RepoDAO
    lateinit var adapter: RepoAdapter
    val songsList = ArrayList<RepoModel>()
    var searchString = ""
    var language = "java"
    var since = "weekly"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataRepository = Repository(application)

        val viewModeFact = ViewModelFactory(dataRepository)
        db = RepoDatabase.getDatabase(this).songsDao()
        viewMode = ViewModelProviders.of(this, viewModeFact).get(MainViewModel::class.java)

        initView()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(queryString: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(queryString: String?): Boolean {

                queryString?.let {
                    searchString = it
                    showRepos(searchString)
                }
                return true
            }
        })

        loadData(language, since)

    }


    private fun initView() {
        adapter = RepoAdapter(songsList, this)
        val layout = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layout
        recyclerView.adapter = adapter

    }

    private fun loadData(language: String, since: String) {

        viewMode.getFreshRepos(language, since).observe(this, Observer {

            when (it.status) {

                Status.SUCCESS -> {
                    showRepos(searchString)
                }

                Status.ERROR -> {
                    Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {
                    //Toast.makeText(this, "Loading..", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun showRepos(search: String) {

        viewMode.showRepos(search).observe(this, Observer {

            adapter.clear()
            adapter.addTasks(it as ArrayList<RepoModel>)
            adapter.notifyDataSetChanged()


        })
    }


    override fun onCardClicked(repoModel: RepoModel) {

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_REPO, repoModel)
        startActivity(intent)

    }
}
