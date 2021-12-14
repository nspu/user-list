package com.ns.pu.users.repository.dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    val cell: String?,
    val dob: Int?,
    val email: String?,
    val gender: String?,
    @Embedded
    val id: Id?,
    @Embedded
    val location: Location?,
    @Embedded
    val login: Login?,
    @Embedded
    val name: Name?,
    val nat: String?,
    val phone: String?,
    @Embedded
    val picture: Picture?,
    val registered: Int?,
) {
    @PrimaryKey(autoGenerate = true)
    var id_user: Int = 0
}