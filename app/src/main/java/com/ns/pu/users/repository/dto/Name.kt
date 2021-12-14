package com.ns.pu.users.repository.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "name")
data class Name(
    val first: String?,
    val last: String?,
    val title: String?,
) {
    @PrimaryKey(autoGenerate = true)
    var id_name: Int = 0
}