package com.ns.pu.users.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ns.pu.users.repository.dto.*

@Database(entities = [User::class, Picture::class, Name::class, Login::class, Location::class, Id::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}

