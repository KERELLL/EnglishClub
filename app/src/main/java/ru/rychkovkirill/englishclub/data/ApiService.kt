package ru.rychkovkirill.englishclub.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import ru.rychkovkirill.englishclub.data.models.*

interface ApiService {

    //auth module

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

    // user module admin

    @GET("user/info/{email}")
    suspend fun getUserInfo(
        @Path("email") email: String,
        @Header("Authorization") token : String
    ) : Response<UserResponseDTO>

    @GET("user/all")
    suspend fun getAllUsers(
        @Header("Authorization") token : String
    ) : Response<List<UserResponseDTO>>

    @PUT("user/update")
    suspend fun updateUser(
        @Header("Authorization") token : String,
        @Query("email") email : String,
        @Body userResponseDTO: UserResponseDTO
    ) : Response<SuccessResponse>

    // module user user
    @PUT("user/update-me")
    suspend fun updateMe(
        @Header("Authorization") token : String,
        @Body userResponseDTO: UserResponseDTO
    ) : Response<SuccessResponse>

    //news module

    @GET("news/all")
    suspend fun getNews(
        @Header("Authorization") token : String
    ) : Response<List<NewsResponseDTO>>

    @GET("news/info/{news_id}")
    suspend fun getNewsInfo(
        @Path("news_id") newsId: Int,
        @Header("Authorization") token : String
    ) : Response<NewsResponseDTO>

    //news module admin

    @POST("news/add")
    suspend fun addNews(
        @Header("Authorization") token : String,
        @Body newsAddRequest: NewsAddRequest
    ) : Response<SuccessResponse>

    //shifts module

    @GET("shifts/upcoming")
    suspend fun getUpcomingShifts(
        @Header("Authorization") token : String
    ) : Response<List<ShiftResponseDTO>>

    @GET("shifts/info/{shift_id}")
    suspend fun getShiftInfo(
        @Header("Authorization") token : String,
        @Path("shift_id") shift_id: Int
    ) : Response<ShiftResponseDTO>

    //shifts module admin

    @POST("shifts/add")
    suspend fun addShift(
        @Header("Authorization") token : String,
        @Body shift: ShiftAddRequest
    ): Response<SuccessResponse>

    @GET("shifts/reservation")
    suspend fun getReservations(
        @Header("Authorization") token : String
    ) : Response<List<ReservationResponseDTO>>

    @PUT("shifts/approve/{shift_reservation_id}")
    suspend fun approveShiftReservation(
        @Header("Authorization") token : String,
        @Path("shift_reservation_id") shift_reservation_id: Int
    ) : Response<SuccessResponse>

    //shifts module user

    @GET("shifts/my")
    suspend fun getUserShifts(
        @Header("Authorization") token : String,
    ) : Response<List<ShiftResponseDTO>>

    @POST("shifts/reserve/{shift_id}")
    suspend fun reserveShift(
        @Header("Authorization") token : String,
        @Path("shift_id") shift_id: Int
    ): Response<SuccessResponse>

    //Task module

    @GET("tasks/all")
    suspend fun getAllTasks(
        @Header("Authorization") token : String
    ) : Response<List<TaskResponse>>

    @GET("tasks/{task_id}")
    suspend fun getTaskById(
        @Header("Authorization") token : String,
        @Path("task_id") task_id: Int
    ) : Response<TaskResponse>

    //Task module admin

    @POST("tasks/add")
    suspend fun addTask(
        @Header("Authorization") token : String,
        @Body taskRequest: TaskRequestDTO
    ) : Response<SuccessResponse>

    @PUT("tasks/responses/approve/{response_id}")
    suspend fun approveResponse(
        @Header("Authorization") token : String,
        @Path("response_id") response_id: Int
    ) : Response<SuccessResponse>

    @GET("tasks/responses")
    suspend fun getNotApprovedResponses(
        @Header("Authorization") token : String
    ) : Response<List<ResponseResponseDTO>>

    @GET("tasks/responses/for_check")
    suspend fun getNotCheckedResponses(
        @Header("Authorization") token : String
    ) : Response<List<ResponseResponseDTO>>

    @PUT("tasks/check/{task_response_id")
    suspend fun checkTask(
        @Header("Authorization") token : String,
        @Path("task_id") task_id: Int
    ) : Response<SuccessResponse>

    //Task module user

    @GET("tasks/my")
    suspend fun getMyTasks(
        @Header("Authorization") token : String
    ): Response<List<TaskResponse>>

    @POST("tasks/response/{task_id}")
    suspend fun responseTask(
        @Header("Authorization") token : String,
        @Path("task_id") task_id: Int
    ) : Response<SuccessResponse>

    @PUT("tasks/submit/{task_id}")
    suspend fun submitTask(
        @Header("Authorization") token : String,
        @Path("task_id") task_id: Int
    ): Response<SuccessResponse>

}