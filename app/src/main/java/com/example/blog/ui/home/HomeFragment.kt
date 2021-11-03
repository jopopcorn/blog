package com.example.blog.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.blog.BlogApplication.Companion.USER_ID
import com.example.blog.BlogApplication.Companion.prefs
import com.example.blog.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding.apply {
            viewModel = this@HomeFragment.viewModel
            lifecycleOwner = this@HomeFragment
        }
        initUI()
        return binding.root
    }

    private fun initUI(){
        loadUserInfo()

        homeAdapter = HomeAdapter {
            findNavController().navigate(HomeFragmentDirections.actionHomeToPostDetail(it.id))
        }

        binding.fHomeRcvPostList.apply {
            adapter = homeAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireActivity(), 1))
        }

        binding.fHomeIvWritePost.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToWritePost(-1))
        }
    }

    private fun loadUserInfo(){
        if(USER_ID == 0) {
            USER_ID = viewModel.getUserId()
            prefs.setInt("userId", USER_ID)
        }else{
            viewModel.loadBlogInfo(USER_ID)
            viewModel.loadPostList(USER_ID)
        }
    }
}