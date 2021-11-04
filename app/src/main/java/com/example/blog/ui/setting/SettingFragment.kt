package com.example.blog.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.blog.data.Blog
import com.example.blog.data.User
import com.example.blog.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private val binding by lazy { FragmentSettingBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SettingViewModel>()
    private val args: SettingFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            viewModel = this@SettingFragment.viewModel
            lifecycleOwner = this@SettingFragment
        }
        initUI()
        return binding.root
    }

    private fun initUI() {
        viewModel.loadBlogInfo(args.userId)
        viewModel.loadUserInfo(args.userId)

        binding.fSettingTvFinish.setOnClickListener {
            if (binding.fSettingEtNickname.text.toString().trim() == ""
                || binding.fSettingEtBlogName.text.toString().trim() == ""
                || binding.fSettingEtIntroduction.text.toString().trim() == "") {
                Toast.makeText(requireActivity(), "빈칸, 공백 없이 정보를 입력해주세요.", Toast.LENGTH_LONG).show()
            }else{
                val user: User? = viewModel.userInfo.value
                val blog: Blog? = viewModel.blogInfo.value

                user?.let { item ->
                    item.nickname = binding.fSettingEtNickname.text.toString()
                    viewModel.saveUserInfo(item)
                }

                blog?.let { item ->
                    item.nickname = binding.fSettingEtNickname.text.toString()
                    item.name = binding.fSettingEtBlogName.text.toString()
                    item.introduction = binding.fSettingEtIntroduction.text.toString()
                    viewModel.saveBlogInfo(item)
                }
            }
        }

        viewModel.saveBlogData.observe(viewLifecycleOwner, {
            if (it && viewModel.saveUserData.value == true) {
                Toast.makeText(requireActivity(), "프로필 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        })

        viewModel.saveUserData.observe(viewLifecycleOwner, {
            if (it && viewModel.saveBlogData.value == true) {
                Toast.makeText(requireActivity(), "프로필 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}