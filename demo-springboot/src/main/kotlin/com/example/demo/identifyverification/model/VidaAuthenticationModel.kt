package com.example.demo.identifyverification.model

import com.fasterxml.jackson.annotation.JsonProperty

data class VidaAuthenticationModel(
  @JsonProperty("client_id")
  val clientId:String,
  @JsonProperty("client_secret")
  val clientSecret: String,
  @JsonProperty("grant_type")
  val grantType: String,
  @JsonProperty("scope")
  val scope: String
)
