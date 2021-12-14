package com.ns.pu.users.ui.user

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.ns.pu.users.R
import com.ns.pu.users.databinding.FragmentFullUserBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class UserInfosFragment : Fragment() {

    private lateinit var _binding: FragmentFullUserBinding

    private val binding get() = _binding
    private val viewModel by viewModels<UserInfosViewModel> {

        val key = requireContext().getString(R.string.user_id_key)
        UserInfoViewModelFactory(arguments?.getInt(key) ?: -1, requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFullUserBinding.inflate(inflater, container, false)
        startLoading()
        configureList()
        var isToolbarShown = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.recyclerView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                val shouldShowToolbar = scrollY > binding.toolbar.height
                if (isToolbarShown != shouldShowToolbar) {
                    isToolbarShown = shouldShowToolbar
                    binding.appbar.isActivated = shouldShowToolbar
                    binding.toolbarLayout.isTitleEnabled = shouldShowToolbar
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun configureList() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            viewModel.userInfoFlow.collectLatest { user ->
                endLoading()
                recyclerView.adapter = UserInfosAdapter(user?.infos ?: listOf())
                //Need placeholder
                Picasso.with(context).load(user?.imageUrl).into(binding.detailImage)
                binding.toolbar.title = user?.fullName
            }
        }
    }

    private fun startLoading() {
        binding.recyclerView.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE
    }

    private fun endLoading() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.progress.visibility = View.GONE
    }
}