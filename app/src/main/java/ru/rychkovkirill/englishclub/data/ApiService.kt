package ru.rychkovkirill.englishclub.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import ru.rychkovkirill.englishclub.data.models.*

interface ApiService {
    @POST("auth/register")
    suspend fun register(
        @Body registerRequestDTO: RegisterRequestDTO
    ) : Response<RegisterResponseDTO>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Response<LoginResponseDTO>

    @GET("user/info/{email}")
    suspend fun getUserInfo(
        @Path("email") email: String,
        @Header("Authorization") token : String
    ) : Response<UserResponseDTO>

    @GET("user/all")
    suspend fun getAllUsers(
        @Header("Authorization") token : String
    ) : Response<List<UserResponseDTO>>

    @GET("news/all")
    suspend fun getNews(
        @Header("Authorization") token : String
    ) : Response<List<NewsResponseDTO>>

    @POST("news/add")
    suspend fun addNews(
        @Header("Authorization") token : String,
        @Body newsAddRequest: NewsAddRequest
    ) : Response<NewsAddResponseDTO>

    @GET("news/info/{news_id}")
    suspend fun getNewsInfo(
        @Path("news_id") newsId: Int,
        @Header("Authorization") token : String
    ) : Response<NewsResponseDTO>
}