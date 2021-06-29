package repository.service

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RedditService {
    private val moshi = Moshi.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.reddit.com/r/formuladank/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val service = retrofit.create(RedditAPI::class.java)
}