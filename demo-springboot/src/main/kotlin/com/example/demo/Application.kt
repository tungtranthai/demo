package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.Generated

@SpringBootApplication
class Application {

  companion object {
    @Generated
    @JvmStatic
    fun main(args: Array<String>) {
      runApplication<Application>(*args)
    }
  }
}

/**
 * When to use @JvmStatic
When you know that your Kotlin code won't be used in Java,
you don't have to worry about adding the @JvmStatic annotation.
This keeps your code cleaner. However, if your Kotlin code is called from Java,
it makes sense to add the annotation.
This will prevent your Java code from polluting with the name Companion everywhere.

It's not like an additional keyword on either side.
If you add @JvmStatic in one place,
you can prevent writing the extra Companion word in thousands of places,wherever you call that function.
This is especially useful for library creators,
if they add @JvmStatic in their Kotlin library,
the users of that library won't have to use the Companion word in their Java code.
 */
