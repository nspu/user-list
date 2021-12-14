package com.ns.pu.users.repository.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(
    val city: String?,
    val postcode: String?,
    val state: String?,
    val street: String?,
) {
    @PrimaryKey(autoGenerate = true)
    var id_location: Int = 0
}