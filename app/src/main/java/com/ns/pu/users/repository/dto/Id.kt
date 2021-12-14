package com.ns.pu.users.repository.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "id")
data class Id(
    val name: String?,
    val value: String?,
) {
    @PrimaryKey(autoGenerate = true)
    var id_id: Int = 0
}