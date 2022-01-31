    fun main(args: Array<String>) {
    val whiteHorse: ChessFigureDesc = ChessFigureDesc(1, horseName, FigureColor.WHITE, "E2")
    val blackHorse: ChessFigureDesc = ChessFigureDesc(2, horseName, FigureColor.BLACK, "E4")
    val secondBlackHorse: ChessFigureDesc = ChessFigureDesc(8, horseName, FigureColor.BLACK, "E8")
    val wrongHorse: ChessFigureDesc = ChessFigureDesc(7, horseName, FigureColor.BLACK, "E8")
    val whiteKing: ChessFigureDesc = ChessFigureDesc(3, kingName, FigureColor.WHITE, "E7")
    val blackKing: ChessFigureDesc = ChessFigureDesc(4, kingName, FigureColor.BLACK, "B3")
    val whiteElephant: ChessFigureDesc = ChessFigureDesc(6, elephantName, FigureColor.WHITE, "A2")
    val whitePawn: ChessFigureDesc = ChessFigureDesc(9, pawnName, FigureColor.WHITE, "D7")
    for (figure in ChessFigureDesc.availableFigures) {
        println("${figure.color} ${figure.name} ${figure.position} ")
    }


    whiteKing.getFigureMovementAbility()
    whiteHorse.getFigureMovementAbility()
    whitePawn.getFigureMovementAbility()

    blackKing.moveToNewPosition("E3")
    whiteElephant.moveToNewPosition("A1")

    println("\nИстория ходов:")
    for (storyRecord in ChessFigureDesc.movementHistory) {
        println(storyRecord)
    }


    whiteHorse.FigureState().canThisFigureMove(false)
    println("\nСостояние движения белой лошади: ${whiteHorse.state}")
    secondBlackHorse.FigureState().canThisFigureMove(true)
    println("Второе состояние движения черной лошади: ${secondBlackHorse.state}\n")

    whiteHorse.FigureState().canAttackOrBeingAttacked(false)
    println("Состояние атаки белой лошади: ${whiteHorse.state}")
    whitePawn.FigureState().canAttackOrBeingAttacked(true)
    println("Состояние атаки белой пешки: ${whitePawn.state}")
    secondBlackHorse.FigureState().canAttackOrBeingAttacked(true)
    println("Второе состояние атаки черной лошади: ${secondBlackHorse.state}\n")

    whiteKing.removeFigureFromBoard()
    for (figure in ChessFigureDesc.availableFigures) {
        println("${figure.name} ${figure.position} ${figure.color}")
    }


    println("\nИные фигуры: ")
    for (figure in ChessBoard.getInitChessBoard()) {
        println("${figure.name} ${figure.color} ${figure.position}")
    }


    var firstUser = User("User1", 1, 28, 11)
    println("\nИнформация о первом игроке:\n${firstUser}")
    var secondUser = User("User2", 2, 20, 1)
    println("\nИнформация о втором игроке:\n${secondUser}")

    val aClass = A()
    aClass.display()

    println("\n% выигрышей пользователей: ${firstUser % secondUser}")
    println("Приращение выигрышей пользователя: ${firstUser++}")
    println("Уменьшение выигрышей пользователя: ${secondUser--}")
    println("Является ли первый пользователь выше второго: ${firstUser > secondUser}")

    val resultSum = converter("+")?.invoke(2.3, 5.5)
    val resultSub = converter("-")?.invoke(2.3, 5.5)
    val resultMult = converter("*")?.invoke(2.3, 5.5)
    val resultDiv = converter("/")?.invoke(2.3, 5.5)
    val resultNull = converter(null)?.invoke(2.3, 5.5)
    println("\nСумма: $resultSum")
    println("Разность: $resultSub")
    println("Умножение: $resultMult")
    println("Деление: $resultDiv")
    println("Null: $resultNull")
}

fun converter(str: String?): ((Double, Double) -> Double)? {
    if (str == null) {
        return null
    }

    when {
        str.contains("+") -> {
            return { firstNumber: Double, secondNumber: Double -> firstNumber + secondNumber }
        }
        str.contains("*") -> {
            return { firstNumber: Double, secondNumber: Double -> firstNumber * secondNumber }
        }
        str.contains("-") -> {
            return { firstNumber: Double, secondNumber: Double -> firstNumber - secondNumber }
        }
        str.contains("/") -> {
            return { firstNumber: Double, secondNumber: Double -> firstNumber / secondNumber }
        }
        else -> return null
    }

}