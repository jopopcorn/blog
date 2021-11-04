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
    private var editMode : Boolean = false

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
        if(postId != -1){
            editMode = true
            viewModel.getPostInfo(postId)
        }

        binding.fWritePostIvBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fPostDetailTvFinish.setOnClickListener {
            if (binding.fWritePostEtTitle.text.toString().trim() == "" || binding.fWritePostEtContent.text.toString().trim() == "") {
                Toast.makeText(requireActivity(), "빈칸, 공백 없이 글을 작성해주세요.", Toast.LENGTH_LONG).show()
            } else {
                if(postId == -1){
                    viewModel.getLastPostId()
                }else{
                    viewModel.setPostId(postId)
                }
            }
        }

        viewModel.isCompleted.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(requireActivity(), "게시글 작성이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        })

        viewModel.postId.observe(viewLifecycleOwner, {
            if(editMode){
                val post : Post? = viewModel.postData.value
                post?.let { item ->
                    item.title = binding.fWritePostEtTitle.text.toString()
                    item.content = binding.fWritePostEtContent.text.toString()
                    viewModel.savePost(item)
                }
            }else{
                viewModel.savePost(
                    Post(
                        id = it,
                        userId = USER_ID,
                        title = binding.fWritePostEtTitle.text.toString(),
                        content = binding.fWritePostEtContent.text.toString(),
                        date = viewModel.getCurrentDate()
                    )
                )
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}