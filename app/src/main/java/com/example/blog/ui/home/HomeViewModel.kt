package com.example.blog.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blog.data.Blog
import com.example.blog.data.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import timber.log.Timber

class HomeViewModel : ViewModel() {
    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>>
        get() = _postList

    private val _blogInfo = MutableLiveData<Blog>()
    val blogInfo: LiveData<Blog>
        get() = _blogInfo

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun loadPostList(userId: Int) {
        val posts = arrayListOf<Post>()

        val getUserPostsQuery =
            db.collection("posts").whereEqualTo("userId", userId)
                .orderBy("date", Query.Direction.DESCENDING)

        getUserPostsQuery.get().addOnSuccessListener { documentSnapshot ->
            documentSnapshot.documents.forEach { document ->
                val post = document.toObject(Post::class.java)
                Timber.d("게시글 정보: $post")
                post?.let { it -> posts.add(it) }
            }
            _postList.value = posts
        }.addOnFailureListener {
            Timber.d("게시글 불러오기 실패 - $it")
        }
    }

    fun getUserId(): Int {
        val getLastUserIdQuery =
            db.collection("users").orderBy("id", Query.Direction.DESCENDING).limit(1)
        var userId = 0

        getLastUserIdQuery.get().addOnSuccessListener {
            Timber.d("유저 정보 ${it.documents[0].id} == ${it.documents[0]["id"]} - ${it.documents}")
            userId = it.documents[0].id.trim().toInt()
        }.addOnFailureListener {
            Timber.d("유저 정보 불러오기 실패 - $it")
        }

        // 새로운 유저 아이디 배정을 위해 마지막 유저 id의 +1 값으로 아이디 생성
        return userId + 1
    }

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
}