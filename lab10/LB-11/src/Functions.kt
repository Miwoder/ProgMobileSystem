import java.lang.Exception

fun main(args: Array<String>) {
    println("Is valid: " + isValid("user@mail.ru", "pass1234").toString())
    println("Is valid: " + isValid("usernb", "pass1234").toString())
    println("Is valid: " + isValid("user@mail.ru", "123 4435").toString())
    println("Is valid: " + isValid("user@mail.ru", "123").toString())
    println("Is valid: " + isValid("user53@gmail.com", "pass12341").toString())
    println("Is valid: " + isValid(null, "").toString())
    println("Is valid: " + isValid("", null).toString())

    println("\nIs holiday: ${isHoliday("6.5.2020")}")
    println("Is holiday: ${isHoliday("8.3.2020")}")
    println("Is holiday: ${isHoliday("31.12.2020")}")
    println("Is holiday: ${isHoliday("")}")
    println("Is holiday: ${isHoliday(null)}")

    println("\n+: ${doOperation(5, 6, '+')}")
    println("-: ${doOperation(6, 1, '-')}")
    println("*: ${doOperation(5, 6, '*')}")
    println("/: ${doOperation(6, 5, '/')}")
    println("%: ${doOperation(6, 5, '%')}")
    //println("exception: ${doOperation(6, 5, 'E')}")

    val nullIntArray: IntArray? = null
    println("\nMax index: ${nullIntArray.indexOfMax()}")
    println("Max index: ${intArrayOf(1, 2, 3, 5, 78,10).indexOfMax()}")
    println("Max index: ${intArrayOf(1, 78, 5, 78, 4,10).indexOfMax()}")

    println("\ncoincidence: ${"privet".coincidence("poka")}")
    println("coincidence: ${"privet".coincidence("priver")}")
    println("coincidence: ${"kotlin".coincidence("java")}")
    println("coincidence: ${"vuejs".coincidence("angular")}")

    println("\nFactorial cycle: ${factorialCycle(5)}")
    println("Factorial range: ${factorialRange(5)}")
    println("Recursive factorial: ${recursiveFactorial(5)}")

    isPrimeFun(10000)
}

fun isValid(email: String?, password: String?): Boolean {
    fun notNull(email: String?, password: String?): Boolean = email == null || password == null|| email == ""|| password == ""

    if (notNull(email, password)) { return false }
    if (!Regex(pattern = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$").containsMatchIn(email!!)) { return false }
    if (password!!.length !in 6..12 || password.contains(" ")) {return false}
    return true
}


enum class Holidays(var date: String) {
    CHRISTMAS_DAY("25.12.2020"),
    WOMENS_DAY("8.3.2020"),
    NEW_YEAR("31.12.2020"),
    NATIONAL_DAY("3.7.2020"),
}
fun isHoliday(date: String?): Boolean {
    return when (date) {
        Holidays.NEW_YEAR.date -> true
        Holidays.WOMENS_DAY.date -> true
        Holidays.NATIONAL_DAY.date -> true
        Holidays.CHRISTMAS_DAY.date -> true
        else -> false
    }
}


fun doOperation(a: Int, b: Int, operation: Char): Double {
    return when(operation) {
        '+' -> (a + b).toDouble()
        '-' -> (a - b).toDouble()
        '*' -> (a * b).toDouble()
        '%' -> (a % b).toDouble()
        '/' -> (a / b).toDouble()
        else -> throw Exception("Unable to perform operation")
    }
}

fun IntArray?.indexOfMax(): Int? {
    if (this == null) {
        return null
    }

    var maxIndex: Int = 0
    var maxValue: Int = 0
    var countMax: Int = 0

    for (value in this) {
        if (maxValue == value) {
            countMax += 1
        }

        if (maxValue < value) {
            maxValue = value
            countMax = 0
        }
        maxIndex += 1
    }

    return if (countMax == 0) maxIndex else null
}

fun String.coincidence(comparer: String): Int {
    var counter: Int = 0

    for (i in 0 until this.count()) {
        for (j in 0 until comparer.count()) {
            if (i == j && this[i].toLowerCase() == comparer[j].toLowerCase()) {
                counter++
            }
        }
    }

    return counter
}

fun factorialCycle(n: Int): Double {
    var counter: Int = 0
    var result: Double = 1.0
    while (counter != n) {
        counter++
        result *= counter
    }
    return  result
}

fun factorialRange(n: Int): Double {
    var result: Double = 1.0
    for (i in 1..n) {
        result *= i
    }
    return result
}

fun recursiveFactorial(n: Int): Double {
    return if (n == 1) n.toDouble()
    else n * recursiveFactorial(n - 1)
}

fun isPrimeFun(number: Int) {
    fun isPrimeLocal(number: Int): Boolean {
        for (i in 2..number/2) {
            if (number % i == 0) {
                return false
            }
        }
        return true
    }

    val list: MutableList<Int> = mutableListOf()
    val arrayInt: Array<Int?> = arrayOfNulls(10)
    var index = 0

    for (num in 2..number) {
        if (isPrimeLocal(num)) {
            if (list.size < 20) {
                list.add(num)
            } else if (index in 20..29) {
                arrayInt[index - 20] = num
            }
            index++
            }
        }

    println("\nTotal items: $index")
    print("List: ")
    list.forEach { x -> print(" $x") }

    print("\nArray: ")
    arrayInt.forEach { x -> print(" $x") }
}