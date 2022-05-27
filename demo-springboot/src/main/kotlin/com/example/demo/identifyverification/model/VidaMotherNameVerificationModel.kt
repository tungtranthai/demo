package com.example.demo.identifyverification.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VidaMotherNameVerificationModel(
  @JsonProperty("trx_id")
  val trxId: String,
  @JsonProperty("nik")
  val idCardNumber: String,
  @JsonProperty("mother_maiden_name")
  val motherName: String
)
