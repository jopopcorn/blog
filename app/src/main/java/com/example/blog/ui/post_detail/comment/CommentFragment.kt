package com.example.blog.ui.post_detail.comment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.blog.BlogApplication.Companion.USER_ID
import com.example.blog.data.Comment
import com.example.blog.databinding.FragmentCommentBinding

class CommentFragment : Fragment() {
    private val binding by lazy { FragmentCommentBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CommentViewModel>()
    private val args: CommentFragmentArgs by navArgs()
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            viewModel = this@CommentFragment.viewModel
            lifecycleOwner = this@CommentFragment
        }
        initUI()
        return binding.root
    }

    private fun initUI() {
        viewModel.getUserNickname(USER_ID)
        viewModel.loadCommentList(args.postId)

        commentAdapter = CommentAdapter {

        }

        binding.fCommentIvBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.fCommentRcvCommentList.apply {
            adapter = commentAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireActivity(), 1))
        }

        binding.fCommentTvRegister.setOnClickListener {
            if (binding.fCommentEtComment.text.toString().trim() != "") {
                viewModel.getLastCommentId()
            }
        }

        viewModel.commentId.observe(viewLifecycleOwner, { commentId ->
            viewModel.saveComment(
                Comment(
                    id = commentId,
                    postId = args.postId,
                    userId = USER_ID,
                    nickname = viewModel.nickname.value!!,
                    date = viewModel.getCurrentDate(),
                    content = binding.fCommentEtComment.text.toString()
                )
            )
        })

        viewModel.isCompleted.observe(viewLifecycleOwner, {
            if (it) {
                binding.fCommentEtComment.setText("")
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}