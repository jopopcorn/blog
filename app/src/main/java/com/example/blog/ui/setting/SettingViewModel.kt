package com.example.blog.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blog.data.Blog
import com.example.blog.data.Post
import com.example.blog.data.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import timber.log.Timber

class SettingViewModel : ViewModel() {
    private val _blogInfo = MutableLiveData<Blog>()
    val blogInfo: LiveData<Blog>
        get() = _blogInfo

    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User>
        get() = _userInfo

    private val _isCompleted = MutableLiveData<Boolean>()
    val isCompleted: LiveData<Boolean>
        get() = _isCompleted

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun loadBlogInfo(userId: Int) {
        val getUserBlogInfoQuery =
            db.collection("blogs").whereEqualTo("id", userId).limit(1)

        getUserBlogInfoQuery.get().addOnSuccessListener {
            val blog: Blog? = it.documents.firstOrNull()?.toObject(Blog::class.java)
            Timber.d("블로그 정보: $blog")
            blog?.let {
                _blogInfo.value = blog!!
            }
        }.addOnFailureListener {
            Timber.d("블로그 정보 불러오기 실패 - $it")
        }
    }

    fun loadUserInfo(userId: Int) {
        db.collection("users").document("$userId")
            .get()
            .addOnSuccessListener {
                val user: User? = it.toObject(User::class.java)
                user?.let {
                    _userInfo.value = user!!
                    Timber.d("유저 정보 불러오기 $it")
                }
            }.addOnFailureListener {
                Timber.d("유저 정보 불러오기 실패 - $it")
            }
    }

    fun saveBlogInfo(blog: Blog) {
        db.collection("blogs").document("${blog.id}")
            .set(blog)
            .addOnSuccessListener {
                Timber.d("블로그 정보 저장 완료")
                _isCompleted.value = true
            }.addOnFailureListener {
                Timber.d("블로그 정보 저장 실패 - $it")
            }
    }

    fun saveUserInfo(user: User) {
        db.collection("users").document("${user.id}")
            .set(user)
            .addOnSuccessListener {
                Timber.d("유저 정보 저장 성공")
            }.addOnFailureListener {
                Timber.d("유저 정보 저장 실패 - $it")
            }
    }
}