package com.example.blog.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blog.data.Blog
import com.example.blog.data.Post
import com.example.blog.data.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import timber.log.Timber

class HomeViewModel : ViewModel() {
    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>>
        get() = _postList

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int>
        get() = _userId

    private val _blogInfo = MutableLiveData<Blog>()
    val blogInfo: LiveData<Blog>
        get() = _blogInfo

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun loadPostList(userId: Int) {
        val posts = arrayListOf<Post>()

        val getUserPostsQuery =
            db.collection("posts").whereEqualTo("userId", userId)
                .orderBy("id", Query.Direction.DESCENDING)

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

    fun createUserId() {
        val getLastUserIdQuery =
            db.collection("users").orderBy("id", Query.Direction.DESCENDING).limit(1)
        var lastUserId: Long

        getLastUserIdQuery.get().addOnSuccessListener {
            if(it.documents.firstOrNull() != null){
                lastUserId = it.documents.first().get("id") as Long

                // 새로운 유저 아이디 배정을 위해 마지막 유저 id의 +1 값으로 아이디 생성
                _userId.value = lastUserId.toInt() + 1
                Timber.d("새로운 유저 아이디 생성 ${_userId.value}")
            }
        }.addOnFailureListener {
            Timber.d("유저 정보 불러오기 실패 - $it")
        }
    }

    fun createNewUser(newUserId: Int) {
        val newUser = User(newUserId)
        db.collection("users").document("$newUserId")
            .set(newUser)
            .addOnSuccessListener {
                Timber.d("새로운 유저 생성 완료 $newUser")
            }.addOnFailureListener {
                Timber.d("새로운 유저 생성 실패 - $it")
            }
    }

    fun createUserBlog(newUserId: Int) {
        val newUserBlog = Blog(newUserId)
        db.collection("blogs").document("$newUserId")
            .set(newUserBlog)
            .addOnSuccessListener {
                _blogInfo.value = newUserBlog
                Timber.d("새로운 유저의 블로그 생성 완료 $newUserBlog")
            }.addOnFailureListener {
                Timber.d("새로운 유저의 블로그 생성 실패 - $it")
            }
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