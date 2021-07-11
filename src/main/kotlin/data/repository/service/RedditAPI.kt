package data.repository.service

import domain.model.reddit.RedditPost
import retrofit2.http.GET

interface RedditAPI {

    @GET("hot.json")
    suspend fun fetchHotPosts() : RedditPost

    @GET("new.json")
    suspend fun fetchNewPosts() : RedditPost
}