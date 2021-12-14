package com.ns.pu.users.repository.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login")
data class Login(
    val md5: String?,
    val password: String?,
    val salt: String?,
    val sha1: String?,
    val sha256: String?,
    val username: String?,
) {
    @PrimaryKey(autoGenerate = true)
    var id_login: Int = 0
}