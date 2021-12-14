package com.ns.pu.users.ui.users

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

class UserAdapter(diffCallback: DiffUtil.ItemCallback<UserModel>, private val onItemClick: (id: Int) -> Unit) : PagingDataAdapter<UserModel, UserViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): UserViewHolder {
        return UserViewHolder(parent)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindTo(item, onItemClick)
    }
}
