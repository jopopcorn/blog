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
    val postInfo: LiveData<Post>
        get() = _postInfo

    private val _blogInfo = MutableLiveData<Blog>()
    val blogInfo: LiveData<Blog>
        get() = _blogInfo

    private val _deletedPost = MutableLiveData<Boolean>()
    val deletePost: LiveData<Boolean>
        get() = _deletedPost

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

    fun updateNumberOfLike() {
        val post: Post? = _postInfo.value
        Timber.d("게시글 좋아요의 현재 클릭 상태 ${post?.isPressedLike}")
        post?.let {
            it.isPressedLike = !it.isPressedLike

            if (it.isPressedLike) {
                it.numberOfLike += 1
            } else {
                it.numberOfLike -= 1
            }

            db.collection("posts").document("${it.id}")
                .set(it)
                .addOnSuccessListener {
                    Timber.d("좋아요 숫자 갱신 완료")
                    loadPostData(post.id)
                }
                .addOnFailureListener { exception ->
                    Timber.d("좋아요 숫자 갱신 실패 - $exception")
                }
        }
    }

    fun deletePost(postId: Int) {
        db.collection("posts").document("$postId")
            .delete()
            .addOnSuccessListener {
                _deletedPost.value = true
                Timber.d("게시글 삭제 완료")
            }.addOnFailureListener {
                Timber.d("게시글 삭제 실패 - $it")
            }
    }

}