package com.example.blog.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blog.data.Post
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel : ViewModel() {
    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>>
        get() = _postList

    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userCollectionRef = db.collection("users").document()
    private val blogDocumentRef = db.collection("blogs").document("1")

}