package com.example.demo

import com.example.demo.identifyverification.service.VidaApiClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/demo")
class ApplicationController(
  private val vidaApiClient: VidaApiClient
) {

  @GetMapping("/healthcheck")
  fun healthCheck() = "Healthy"

  @GetMapping("/verify")
  fun getToken(){
    val verify = vidaApiClient.verifyMotherName(
      "", "3512000101806300", "tung")
  }
}
