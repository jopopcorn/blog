package com.example.blog.ui.post_detail.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blog.data.Comment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class CommentViewModel : ViewModel() {
    private val _commentList = MutableLiveData<List<Comment>>()
    val commentList: LiveData<List<Comment>>
        get() = _commentList

    private val _commentId = MutableLiveData<Int>()
    val commentId: LiveData<Int>
        get() = _commentId

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickname

    private val db = FirebaseFirestore.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)

    fun getUserNickname(userId: Int) {
        val getUserNicknameQuery =
            db.collection("users").whereEqualTo("id", userId)

        getUserNicknameQuery.get().addOnSuccessListener {
            _nickname.value = it.documents.first().get("nickname").toString()
            Timber.d("유저 닉네임 불러오기 ${_nickname.value}")
        }.addOnFailureListener {
            Timber.d("유저 닉네임 불러오기 실패 - $it")
        }
    }

    fun loadCommentList(postId: Int) {
        val comments = arrayListOf<Comment>()

        val getCommentsQuery =
            db.collection("comments").whereEqualTo("postId", postId)

        getCommentsQuery.get().addOnSuccessListener { documentSnapshot ->
            documentSnapshot.documents.forEach { document ->
                val comment = document.toObject(Comment::class.java)
                Timber.d("댓글 정보: $comment")
                comment?.let { it -> comments.add(it) }
            }
            _commentList.value = comments
        }.addOnFailureListener {
            Timber.d("댓글 목록 불러오기 실패 - $it")
        }
    }

    fun getLastCommentId(postId: Int) {
        val getLastCommentIdQuery =
            db.collection("comments").whereEqualTo("postId", postId)
                .orderBy("id", Query.Direction.DESCENDING).limit(1)

        getLastCommentIdQuery.get().addOnSuccessListener {
            val lastComment: Comment? = it.documents.firstOrNull()?.toObject(Comment::class.java)
            if (lastComment == null) {
                _commentId.value = 1
            } else {
                _commentId.value = lastComment.id + 1
            }
        }.addOnFailureListener {
            Timber.d("마지막 코멘트 아이디 불러오기 실패 - $it")
        }
    }

    fun saveComment(comment: Comment) {
        db.collection("comments").document("${comment.id}")
            .set(comment)
            .addOnSuccessListener {
                Timber.d("댓글 저장 완료")
            }.addOnFailureListener {
                Timber.d("댓글 저장 실패 - $it")
            }
    }

    fun getCurrentDate(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        return dateFormat.format(date)
    }
}