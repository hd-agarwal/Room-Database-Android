package com.example.roomdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdb.models.User

@Database(entities = [User::class],version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}