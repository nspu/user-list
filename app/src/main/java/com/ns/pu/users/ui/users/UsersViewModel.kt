package com.ns.pu.users.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.ns.pu.users.repository.dto.User
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UsersViewModel(pagingSource: PagingSource<Int, User>) : ViewModel() {

    val usersFlow = Pager(PagingConfig(pageSize = 10)) { pagingSource }
        .flow
        .catch { _ -> TODO("Notify error") }
        .map { pagingSource ->
            pagingSource.map { user -> user.toUserModel() }
        }
        .cachedIn(viewModelScope)

    private fun User.toUserModel() = UserModel(
        id = this.id_user,
        fullName = listOfNotNull(name?.first, name?.last).joinToString(" "),
        email = email ?: ""
    )
}


