package com.ns.pu.users.ui.user;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ns.pu.users.R
import com.ns.pu.users.databinding.InfoUserItemBinding

class UserInfoViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.info_user_item, parent, false)
) {
    var info: Info? = null
        private set

    private val binding = InfoUserItemBinding.bind(itemView)
    fun bindTo(item: Info?) {
        binding.infoName.text = item?.name
        binding.infoValue.text = item?.value
        info = item
    }
}

