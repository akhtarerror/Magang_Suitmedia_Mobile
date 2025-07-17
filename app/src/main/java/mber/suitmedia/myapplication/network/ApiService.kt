package mber.suitmedia.myapplication.network

import UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit API interface
interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10
    ): Response<UserResponse>
}