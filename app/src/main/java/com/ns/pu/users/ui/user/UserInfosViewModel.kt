package com.ns.pu.users.ui.user

import androidx.lifecycle.ViewModel
import com.ns.pu.users.repository.database.UserDao
import com.ns.pu.users.repository.dto.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlin.reflect.full.memberProperties

class UserInfosViewModel(id: Int, userDao: UserDao) : ViewModel() {

    val userInfoFlow: Flow<UserInfoModel?> = userDao.findById(id)
        .catch { TODO("Notify error") }
        .filterNotNull()
        .map { it.toFullUserModel() }

    //Not the time to implement all the informations
    private fun User.toFullUserModel() = UserInfoModel(
        infos = User::class.memberProperties.map { kProperty1 ->
            Info(name = kProperty1.name, value = kProperty1.get(this).toString())
        },
        imageUrl = this.picture?.large ?: "",
        fullName = listOfNotNull(name?.first, name?.last).joinToString(" ")
    )
}


