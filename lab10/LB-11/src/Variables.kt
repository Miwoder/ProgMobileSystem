val imInt = 14
val imDouble = 14.11
val imString = "This is a Big string"

var exInt: Int = 10
var exDouble: Double = 12.34
var exString: String = "This is a large explicit string"

val byteVal: Byte = 9
val intValForByte: Int = byteVal.toInt()

val intValForString: Int = 14
val stringVal: String = intValForString.toString()

const val constantString: String = "This string is constant"

var nullableInt: Int? = null

fun main(args: Array<String>) {
    println("ImplicitInt: $imInt")
    println("ImplicitDouble: $imDouble")
    println("ImplicitString: $imString")

    println("\nExplicitInt: $exInt")
    println("ExplicitDouble: $exDouble")
    println("ExplicitString: $exString")

    println("\nByte to Int: $byteVal - $intValForByte")
    println("Int to String: $intValForString - $stringVal")

    println("Constant string: $constantString")
    println("NullableInt value: $nullableInt")
    print("Enter number: ")
    nullableInt = readLine()?.toInt()
    println("NullableInt value: $nullableInt")
}