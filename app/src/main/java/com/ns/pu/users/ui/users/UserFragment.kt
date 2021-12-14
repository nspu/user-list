package com.ns.pu.users.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ns.pu.users.R
import com.ns.pu.users.databinding.FragmentUsersBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null

    private val binding get() = _binding
    private val viewModel by viewModels<UsersViewModel> { UsersViewModelFactory(requireActivity().application) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        startLoading()
        configureList()
        return binding?.root
    }

    private fun configureList() {
        val pagingAdapter = UserAdapter(diffCallback = UserComparator) { id ->
            val key = requireContext().getString(R.string.user_id_key)
            val bundle = bundleOf(key to id)
            findNavController().navigate(R.id.action_userFragment_to_fullUserFragment, bundle)
        }
        val recyclerView = binding?.recyclerView
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = pagingAdapter

        lifecycleScope.launch {
            viewModel.usersFlow.collectLatest { pagingData ->
                endLoading()
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun startLoading() {
        binding?.recyclerView?.visibility = View.GONE
        binding?.progress?.visibility = View.VISIBLE
    }

    private fun endLoading() {
        binding?.recyclerView?.visibility = View.VISIBLE
        binding?.progress?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}