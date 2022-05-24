package com.example.demo.identifyverification.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class VidaAuthenticationTokenResponse(
  @JsonProperty("access_token")
  val accessToken: String,
  @JsonProperty("expires_in")
  val expiresIn: Long,
  @JsonProperty("refresh_token")
  val refreshToken: String,
  @JsonProperty("refresh_expires_in")
  val refreshExpiresIn: Long,
  @JsonProperty("token_type")
  val tokenType: String
)


