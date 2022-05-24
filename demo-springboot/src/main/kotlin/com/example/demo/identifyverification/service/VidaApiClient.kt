package com.example.demo.identifyverification.service

import com.example.demo.identifyverification.response.MotherNameVerificationResponse

interface VidaApiClient {
  fun verifyMotherName(
    trxId: String,
    idCardNumber: String,
    motherName: String
  ): MotherNameVerificationResponse
}
