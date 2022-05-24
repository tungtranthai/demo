package com.example.demo.common.handler

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@Tag("small")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KotlinHandlerTest {
  @Test
  fun `Test nullSafetyHandler `() {
    // arrange
    // act
    nullSafetyHandler()
    // assert
    Assertions.assertEquals(true, true)
  }
}
