package com.example.demo.identifyverification.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MotherNameVerificationData(
  val result: Boolean,
  val motherName: String? = null
)
