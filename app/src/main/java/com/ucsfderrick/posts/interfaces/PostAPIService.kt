package com.ucsfderrick.posts.interfaces

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import com.ucsfderrick.posts.models.*

interface PostAPIService {
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @GET("posts/")
    fun getAllPosts(): Observable<List<Post>>

    @GET("posts/{id}/comments/")
    fun getComments(@Path("id") id: String?): Observable<List<Comment>>
}