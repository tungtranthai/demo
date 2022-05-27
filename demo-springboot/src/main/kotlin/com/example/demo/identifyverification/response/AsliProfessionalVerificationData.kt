package com.example.demo.identifyverification.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AsliProfessionalVerificationData(
  val name: Boolean,
  val address: String?,
  @JsonProperty("birthplace")
  val birthPlace: Boolean,
  @JsonProperty("birthdate")
  val birthDate: Boolean,
  @JsonProperty("selfie_photo")
  val selfiePhoto: Double?
)
