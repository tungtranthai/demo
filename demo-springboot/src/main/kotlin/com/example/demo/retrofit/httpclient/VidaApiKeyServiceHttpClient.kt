package com.example.demo.retrofit.httpclient

import com.example.demo.identifyverification.dto.VidaAuthenticationTokenModel
import com.example.demo.identifyverification.model.VidaAuthenticationModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface VidaApiKeyServiceHttpClient {
  @Headers("Content-Type: application/x-www-form-urlencoded")
  @POST("auth/realms/vida/protocol/openid-connect/token")
  fun getAuthorizationToken(
    @Body
    body: VidaAuthenticationTokenModel
  ): Call<ResponseBody>
}
