interface ChessFigure {
    var id: Int

    fun verifyFigurePosition(position: String?)

    fun initFunc(name: String, position: String?, color: FigureColor) {
        if (position != null) {
            println("Фигура ${color.color} $name была создана на позиции $position")
        } else {
            println("Фигура ${color.color} $name была создана")
        }
    }
}