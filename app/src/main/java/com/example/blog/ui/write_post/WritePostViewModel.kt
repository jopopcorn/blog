package com.example.blog.ui.write_post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blog.data.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class WritePostViewModel : ViewModel() {
    private val _isCompleted = MutableLiveData<Boolean>()
    val isCompleted: LiveData<Boolean>
        get() = _isCompleted

    private val _postId = MutableLiveData<Int>()
    val postId: LiveData<Int>
        get() = _postId

    private val db = FirebaseFirestore.getInstance()
    private val postCollection = db.collection("posts")

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)

    // 수정할 게시글 아이디를 지정
    fun setPostId(id: Int) {
        _postId.value = id
    }

    fun getLastPostId() {
        val getLastPostIdQuery =
            db.collection("posts").orderBy("id", Query.Direction.DESCENDING).limit(1)

        getLastPostIdQuery.get().addOnSuccessListener {
            val lastPost: Post? = it.documents.firstOrNull()?.toObject(Post::class.java)
            lastPost?.let { post ->
                // 새로운 게시글 아이디 배정을 위해 마지막 게시글 id의 +1 값으로 아이디 생성
                _postId.value = post.id + 1
            }
        }.addOnFailureListener {
            Timber.d("마지막 게시글 아이디 불러오기 실패 - $it")
        }
    }

    fun savePost(post: Post) {
        postCollection.document("${post.id}")
            .set(post)
            .addOnSuccessListener {
                _isCompleted.value = true
                Timber.d("게시글 저장 완료")
            }.addOnFailureListener {
                _isCompleted.value = false
                Timber.d("게시글 저장 실패 - ${it.message}")
            }
    }

    fun getCurrentDate(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        return dateFormat.format(date)
    }
}