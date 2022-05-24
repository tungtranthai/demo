package com.example.demo.common.handler

/**
 *  Ref: https://www.geeksforgeeks.org/kotlin-null-safety/
 */


/**
 * NullSafetyHandler
 * Ref: https://www.geeksforgeeks.org/kotlin-null-safety/
 */
fun nullSafetyHandler() {
  //Nullable and Non-Nullable Types in Kotlin –
  var sample1: String = "Kotlin"
  //sample1 = null //gives compiler error
  println(sample1.length)

  var sample2: String? = "Kotlin"
  sample2 = null // no compiler error
  // val l2 = sample2.length // compiler error because string can be null
  println(sample2?.length)

  //Checking for null in conditions –

  //Safe Call operator(?.) –
  var firstName: String? = "Praveen"
  var lastName: String? = null
  println(firstName?.toUpperCase())
  println(firstName?.length)
  println(lastName?.toUpperCase())

  //let() method –
  var stringlist: List<String?> = listOf("Geeks", "for", null, "Geeks")
  var newlist = listOf<String?>()
  for (item in stringlist) {
    // executes only for non-nullable values
    item?.let { newlist = newlist.plus(it) }
  }
  // to print the elements stored in newlist
  for (items in newlist) {
    println(items) // Geeks for Geeks
  }

  //also() method chain with let() –
  //run() method –
  //Elvis Operator(?:) –
  //Not null assertion : !! Operator
}

/**
 * Type Checking and Smart Casting
 */
fun smartCasting(anyObject: Any) = when (anyObject) {
  is String -> anyObject.length
  is List<*> -> anyObject.size
  else -> 0
}

/**
 * Kotlin Data Classes
 */
data class User(
  val id:String,
  val name:String,
  val email: String?
)
//The compiler automatically derives the following functions :
//equals()
//hashCode()
//toString()
//copy()

/**
 * Kotlin Interfaces
 */

/**
 *
 */
