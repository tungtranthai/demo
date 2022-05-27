package com.example.demo.identifyverification.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AsliProfessionalVerificationError(
  val message: String? = null,
  @JsonProperty("identity_photo")
  val identityPhoto: String? = null,
  @JsonProperty("selfie_photo")
  val selfiePhoto: String? = null
)
