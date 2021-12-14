package com.ns.pu.users.ui.user

data class Info(val name: String, val value: String)
data class UserInfoModel(val infos: List<Info>, val imageUrl: String, val fullName: String)
