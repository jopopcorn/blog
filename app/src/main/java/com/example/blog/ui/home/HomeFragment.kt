package com.example.blog.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.blog.BlogApplication
import com.example.blog.BlogApplication.Companion.prefs
import com.example.blog.databinding.FragmentHomeBinding
import com.google.firebase.FirebaseApp

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
        homeAdapter = HomeAdapter {
//            findNavController().navigate(HomeFragmentDirections.actionHomeToPostDetail())
        }

        binding.fHomeRcvPostList.apply {
            adapter = homeAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireActivity(), 1))
        }

        binding.fHomeIvWritePost.setOnClickListener {
            if(viewModel.postList.value?.size == 0 || viewModel.postList.value == null){
                findNavController().navigate(HomeFragmentDirections.actionHomeToWritePost(1))
            }
        }

        viewModel.postList.observe(viewLifecycleOwner, {

        })
    }
}