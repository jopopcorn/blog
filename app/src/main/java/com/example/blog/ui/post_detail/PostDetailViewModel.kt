package com.example.blog.ui.post_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blog.data.Blog
import com.example.blog.data.Post
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class PostDetailViewModel : ViewModel() {
    private val _postInfo = MutableLiveData<Post>()
    val postInfo : LiveData<Post>
        get() = _postInfo

    private val _blogInfo = MutableLiveData<Blog>()
    val blogInfo : LiveData<Blog>
        get() = _blogInfo

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun loadPostData(postId: Int) {
        val getPostDataQuery =
            db.collection("posts").whereEqualTo("id", postId)

        getPostDataQuery.get().addOnSuccessListener { documentSnapshot ->
            val post = documentSnapshot.documents.first().toObject(Post::class.java)
            Timber.d("게시글 정보: $post")
            post?.let {
                _postInfo.value = it
                loadBlogInfo(it.userId)
            }
        }.addOnFailureListener {
            Timber.d("게시글 불러오기 실패 - $it")
        }
    }

    private fun loadBlogInfo(userId: Int) {
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