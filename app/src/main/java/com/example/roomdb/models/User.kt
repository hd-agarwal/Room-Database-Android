package com.example.roomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    val name:String,
    val age:String?,
    val address:String?,
    val phone:String,
    @PrimaryKey(autoGenerate = true)
    val id:Long=0L
)