package com.example.demo.retrofit.httpclient

import com.example.demo.identifyverification.model.VidaAuthenticationModel
import com.example.demo.identifyverification.model.VidaMotherNameVerificationModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface VidaVerificationServiceHttpClient {
//  @Headers("Content-Type: application/x-www-form-urlencoded")
//  @POST("auth/realms/vida/protocol/openid-connect/token")
//  fun getAuthorizationToken(
//    @Body
//    body: VidaAuthenticationModel
//  ): Call<ResponseBody>

  @Headers("Content-Type: appBearerlication/json")
  @POST("/verify/v1/demog-lite/transaction")
  fun verifyMotherName(
    @Body
    body: VidaMotherNameVerificationModel
  ): Call<ResponseBody>
}
