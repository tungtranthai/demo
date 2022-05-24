package com.example.demo.identifyverification.service.impl

import com.example.demo.identifyverification.model.VidaMotherNameVerificationModel
import com.example.demo.identifyverification.response.MotherNameVerificationResponse
import com.example.demo.identifyverification.service.VidaApiClient
import com.example.demo.retrofit.httpclient.VidaVerificationServiceHttpClient
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.ResponseBody
import org.springframework.stereotype.Service
import retrofit2.Call

@Service
class VidaApiClientImpl(
  private val vidaVerificationServiceHttpClient: VidaVerificationServiceHttpClient,
) : VidaApiClient {
  override fun verifyMotherName(
    trxId: String,
    idCardNumber: String,
    motherName: String
  ): MotherNameVerificationResponse {
    val callBody = VidaMotherNameVerificationModel(trxId, idCardNumber, motherName)

    val call: Call<ResponseBody> = vidaVerificationServiceHttpClient.verifyMotherName(
      body = callBody
    )
    val result = call.execute()
    val body = result.body()?.string()

    return jacksonObjectMapper().readValue(body, MotherNameVerificationResponse::class.java)
  }
}
