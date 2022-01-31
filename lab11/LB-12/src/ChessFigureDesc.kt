class ChessFigureDesc constructor(
        _id: Int,
        _name: String,
        _color: FigureColor
) : ChessFigure {

    override var id: Int = 0
    var name: String
    var color: FigureColor
    var position: String? = null
    var state: String = cannotMoveState

    init {
        this.id = _id
        this.name = _name
        this.color = _color
    }

    constructor(_id: Int, _name: String, _color: FigureColor, _position: String?) : this(_id, _name, _color) {
        this.verifyFigurePosition(_position)
        this.verifyCorrectAmountOfFigures()
    }

    inner class FigureState {
        fun canThisFigureMove(isItMove: Boolean) {
            if (isItMove) {
                state = readyToMoveState
                availableFigures[availableFigures.indexOfFirst { x -> x.id == id }] = this@ChessFigureDesc
                return
            }

            state = cannotMoveState
            availableFigures[availableFigures.indexOfFirst { x -> x.id == id }] = this@ChessFigureDesc
        }

        fun canAttackOrBeingAttacked(isItMove: Boolean) {
            val closeFigures = availableFigures.count {
                it.position!![0] == this@ChessFigureDesc.position!![0]
                        && it.color != this@ChessFigureDesc.color
                        && (it.position!![1].toString().toInt() + 1 == this@ChessFigureDesc.position!![1].toString().toInt()
                        || it.position!![1].toString().toInt() -1 == this@ChessFigureDesc.position!![1].toString().toInt())
            }

            state = if (isItMove && closeFigures > 0) {
                isReadyToAttackState
            } else if (isItMove && closeFigures == 0) {
                noEnemiesToAttackedState
            } else if (!isItMove && closeFigures == 0) {
                isSafeState
            } else {
                canBeAttackedState
            }
        }
    }

    companion object {
        val availableFigures: MutableList<ChessFigureDesc> = mutableListOf()
        val movementHistory: MutableList<String> = mutableListOf()
    }



    fun getFigureMovementAbility() {
        when (this.name) {
            kingName -> println("Король может двигаться в любом направлении, но только на одну позицию")
            ferzinName -> println("Ферзь сочетает в себе оба типа движения: башни и слона")
            horseName -> println("Лошадь может двигаться в каждом направлении в символе Г")
            towerName -> println("Башня может двигаться только вперед, назад, влево и вправо в любом положении")
            elephantName -> println("Слон может двигаться только по диагонали в любом положении")
            pawnName -> println("Пешка может идти только вперед и атаковать близких к ней врагов с левой и правой сторон")
            else -> println("Неизвестная фигура")
        }
    }

    override fun verifyFigurePosition(position: String?) {
        if (position == null || position.length !in 1..2) {
            this.position = null
            return
        }

        if (position[0].toLowerCase() !in 'a'..'h' && position[1].toString().toInt() !in 1..8) {
            this.position = null
            return
        }

        this.position = position
    }

    private fun verifyCorrectAmountOfFigures() {
        val figures = availableFigures.groupBy { x -> x.name}[this.name]

        when (this.name) {
            kingName -> {
                if (figures != null && figures.count { x -> x.color == this.color } >= amountOfKings / 2) {
                    println("!! Невозможно добавить ${this.color} ${this.name} на позицию ${this.position} потому что данная позиция уже занята")
                    return
                }
            }
            ferzinName -> {
                if (figures != null && figures.count { x -> x.color == this.color } >= amountOfFerzins / 2) {
                    println("!! Невозможно добавить ${this.color} ${this.name} на позицию ${this.position} потому что данная позиция уже занята")
                    return
                }
            }
            horseName -> {
                if (figures != null && figures.count { x -> x.color == this.color } >= amountOfHorses / 2) {
                    println("!! Невозможно добавить ${this.color} ${this.name} на позицию ${this.position} потому что данная позиция уже занята")
                    return
                }
            }
            elephantName -> {
                if (figures != null && figures.count { x -> x.color == this.color } >= amountOfElephants / 2) {
                    println("!! Невозможно добавить ${this.color} ${this.name} на позицию ${this.position} потому что данная позиция уже занята")
                    return
                }
            }
            towerName -> {
                if (figures != null && figures.count { x -> x.color == this.color } >= amountOfTowers / 2) {
                    println("!! Невозможно добавить ${this.color} ${this.name} на позицию ${this.position} потому что данная позиция уже занята")
                    return
                }
            }
            pawnName -> {
                if (figures != null && figures.count { x -> x.color == this.color } >= amountOfPawns / 2) {
                    println("!! Невозможно добавить ${this.color} ${this.name} на позицию ${this.position} потому что данная позиция уже занята")
                    return
                }
            }
            else -> {
                println("Несуществующая фигура")
                return
            }
        }

        availableFigures.add(this)
        initFunc(this.name, this.position, this.color)
    }
}

fun ChessFigureDesc.moveToNewPosition(position: String) {
    val storyItem: String = "${this.color} ${this.name} сделал ход с ${this.position} на $position"

    this.position = position
    ChessFigureDesc.availableFigures[ChessFigureDesc.availableFigures.indexOfFirst { x -> x.id == this.id }] = this

    ChessFigureDesc.movementHistory.add(storyItem)
}

fun ChessFigureDesc.removeFigureFromBoard() {
    this.position = null
    ChessFigureDesc.availableFigures.removeIf { x -> x.position == null }
}