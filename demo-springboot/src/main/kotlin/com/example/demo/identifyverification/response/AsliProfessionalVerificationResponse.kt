package com.example.demo.identifyverification.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AsliProfessionalVerificationResponse(
  val timestamp: Int,
  val status: Int,
  val errors: AsliProfessionalVerificationError?,
  val data: AsliProfessionalVerificationData?,
  @JsonProperty("trx_id")
  val trxId: String,
  @JsonProperty("ref_id")
  val refId: String
)

