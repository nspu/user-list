package com.ns.pu.users.ui.user

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.ns.pu.users.repository.database.UserDatabase

class UserInfoViewModelFactory(
    private val id: Int,
    private val application: Application,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserInfosViewModel::class.java)) {

            val userDao = Room.databaseBuilder(
                application,
                UserDatabase::class.java,
                "database-user")
                .allowMainThreadQueries()
                .build()
                .getUserDao()

            @Suppress("UNCHECKED_CAST")
            return UserInfosViewModel(id, userDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}