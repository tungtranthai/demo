package com.example.demo.common.formatter

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.toLocalDateFormat(): String {
  val date = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)

  return date.format(DateTimeFormatter.ofPattern("MMMM dd, YYYY"))
}
