package com.shivaraj.tilaapp.data.local

import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.Glide


@Entity(tableName = "songs_table")

class RepoModel (
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    var id: Int = 0,
    @field:ColumnInfo(name = "username")
    val username: String,
    @field:ColumnInfo(name = "name")
    val name: String,
    @field:ColumnInfo(name = "url")
    val url: String,
    @field:ColumnInfo(name = "avatar")
    val avatar: String,
    @field:ColumnInfo(name = "reponame")
    val reponame: String,
    @field:ColumnInfo(name = "description")
    val description: String,
    @field:ColumnInfo(name = "repourl")
    val repourl: String

): Serializable{


    companion object{


    }


}

