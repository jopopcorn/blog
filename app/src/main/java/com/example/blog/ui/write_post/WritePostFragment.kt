package com.example.blog.ui.write_post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.blog.BlogApplication.Companion.USER_ID
import com.example.blog.data.Post
import com.example.blog.databinding.FragmentWritePostBinding

class WritePostFragment : Fragment() {
    private val binding by lazy { FragmentWritePostBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<WritePostViewModel>()
    private val args: WritePostFragmentArgs by navArgs()
    private var postId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            viewModel = this@WritePostFragment.viewModel
            lifecycleOwner = this@WritePostFragment
        }
        postId = args.postId
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.fWritePostIvBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fPostDetailTvFinish.setOnClickListener {
            if(postId == -1){
                viewModel.getLastPostId()
            }else{
                viewModel.setPostId(postId)
            }
        }

        viewModel.isCompleted.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(requireActivity(), "게시글 작성이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        })

        viewModel.postId.observe(viewLifecycleOwner, {
            viewModel.savePost(
                Post(
                    id = it,
                    userId = USER_ID,
                    title = binding.fWritePostEtTitle.text.toString(),
                    content = binding.fWritePostEtContent.text.toString(),
                    date = viewModel.getCurrentDate()
                )
            )
        })
    }

}