package com.ns.pu.users.repository.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "picture")
data class Picture(
    val large: String?,
    val medium: String?,
    val thumbnail: String?,
) {
    @PrimaryKey(autoGenerate = true)
    var id_picture: Int = 0
}