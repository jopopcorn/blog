package com.example.blog.ui.post_detail

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            findNavController().navigateUp()
        }

        binding.fPostDetailTvCommentCount.setOnClickListener {
            findNavController().navigate(PostDetailFragmentDirections.actionPostDetailToComment(args.postId))
        }

        binding.fPostDetailTvLikeCount.setOnClickListener {
            viewModel.updateNumberOfLike()
        }

        binding.fPostDetailIvMore.setOnClickListener {
            binding.fPostDetailClPostMenuContainer.visibility = View.VISIBLE
            binding.fPostDetailClPostInfoContainer.visibility = View.INVISIBLE
        }

        binding.fPostDetailIvEdit.setOnClickListener {
            findNavController().navigate(
                PostDetailFragmentDirections.actionPostDetailToWritePost(
                    args.postId
                )
            )
        }

        binding.fPostDetailIvDelete.setOnClickListener {
            showDeletePostDialog()
        }

        binding.fPostDetailIvMoreHoriz.setOnClickListener {
            binding.fPostDetailClPostMenuContainer.visibility = View.INVISIBLE
            binding.fPostDetailClPostInfoContainer.visibility = View.VISIBLE
        }

        viewModel.postInfo.observe(viewLifecycleOwner, {
            binding.fPostDetailTvLikeCount.isPressed = it.isPressedLike
        })

        viewModel.deletePost.observe(viewLifecycleOwner, {
            if(it){
                Toast.makeText(requireActivity(), "게시글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        })
    }

    private fun showDeletePostDialog() {
        val dialog: AlertDialog.Builder =
            AlertDialog.Builder(
                requireActivity(),
                R.style.CustomDialogTheme
            )

        dialog.apply {
            setMessage("게시글을 삭제하시겠습니까?")
            setPositiveButton("삭제") { dialog, _ ->
                viewModel.deletePost(args.postId)
                dialog.dismiss()
            }
            setNegativeButton("취소") { dialog, _ ->
                dialog.cancel()
            }
        }

        dialog.show()
    }
}