package com.example.demo.common.formatter

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@Tag("small")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DateTest {
  @Test
  fun `Test toLocalDateFormat - given date string - return local date formatted string`() {
    // arrange
    val str = "2021-06-08"

    // act
    val result = str.toLocalDateFormat()

    // assert
    Assertions.assertEquals("June 08, 2021", result)
  }
}
