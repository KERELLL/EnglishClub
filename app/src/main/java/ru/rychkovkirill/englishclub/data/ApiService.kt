package ru.rychkovkirill.englishclub.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.rychkovkirill.englishclub.data.models.LoginRequestDTO
import ru.rychkovkirill.englishclub.data.models.LoginResponseDTO
import ru.rychkovkirill.englishclub.data.models.RegisterRequestDTO

interface ApiService {
    @POST("auth/register")
    suspend fun register(
        @Body registerRequestDTO: RegisterRequestDTO
    ) : Response<LoginResponseDTO>

    @POST("auth/login")
    suspend fun login(
        @Body loginRequestDTO: LoginRequestDTO
    ) : Response<LoginResponseDTO>
}