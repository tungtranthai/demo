package com.example.demo.identifyverification.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MotherNameVerificationResponse(
  val data: VidaMotherNameVerificationData
)

data class VidaMotherNameVerificationData(
  val result: Result,
  val status: String,
  @JsonProperty("transaction_id")
  val transactionId: String,
)

data class Result(
  val name: String,
  val score: Double,
  val threshold: Double,
  val match: Boolean,
  val extra: ExtraFields,
)

data class ExtraFields(
  val fields: List<Field>
)

data class Field(
  val score: Double,
  val field: String
)
