package com.ns.pu.users.ui.user

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UserInfosAdapter(private val infos: List<Info>) : RecyclerView.Adapter<UserInfoViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): UserInfoViewHolder {
        return UserInfoViewHolder(parent)
    }


    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        val item = infos[position]
        holder.bindTo(item)
    }

    override fun getItemCount() = infos.count()

}
