package com.example.blog.ui.post_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.blog.R
import com.example.blog.databinding.FragmentPostDetailBinding

class PostDetailFragment : Fragment() {
    private val binding by lazy { FragmentPostDetailBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<PostDetailViewModel>()
    private val args: PostDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            viewModel = this@PostDetailFragment.viewModel
            lifecycleOwner = this@PostDetailFragment
        }
        initUI()
        return binding.root
    }

    private fun initUI() {
        viewModel.loadPostData(args.postId)

        binding.fPostDetailIvBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}