package com.ns.pu.users.repository.network

import com.ns.pu.users.repository.dto.User
import java.lang.Exception

data class Response(
    val users: List<User>,
    val currentPage: Int?,
)

interface UserProvider {
    suspend fun getUsers(usersByPage: Int, pageNumber: Int): Response
}

class UserProviderImpl(private val userService: UserService) : UserProvider {

    override suspend fun getUsers(usersByPage: Int, pageNumber: Int): Response {
        val result = userService.users(usersByPage, pageNumber)

        // Improve : throw different exception in different case like error 500, 400 etc
        // The best way to deal with network exception is to build our own system of error
        if(!result.isSuccessful) throw Exception()

        val users = result.body()?.results ?: listOf()
        val currentPage = result.body()?.info?.page
        return Response(users, currentPage)
    }
}
