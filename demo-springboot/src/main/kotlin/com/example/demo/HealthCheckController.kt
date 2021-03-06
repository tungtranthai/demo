package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/demo")
class HealthCheckController {
  @GetMapping("/healthcheck")
  fun healthCheck() = "Healthy"
}
