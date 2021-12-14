package com.ns.pu.users.ui.users;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ns.pu.users.R
import com.ns.pu.users.databinding.UserItemBinding

class UserViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
) {
    var user: UserModel? = null
        private set

    private val binding = UserItemBinding.bind(itemView)
    fun bindTo(item: UserModel?, onItemClick: (id: Int) -> Unit) {
        binding.name.text = item?.fullName
        binding.email.text = item?.email
        binding.root.setOnClickListener {
            onItemClick(item?.id ?: -1)
        }
        user = item
    }

}

