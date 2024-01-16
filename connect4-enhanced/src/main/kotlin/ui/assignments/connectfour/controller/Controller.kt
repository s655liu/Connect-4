package ui.assignments.connectfour.controller

import ui.assignments.connectfour.model.DisplayModel
import ui.assignments.connectfour.model.Model

class Controller ( var display: DisplayModel){
    fun startGame() {
        // model.startGame()
        display.startGame()
    }

    fun addPiece(x: Int, y:Int) {
        display.addPiece(x, y)
    }

    fun changeBoard() {
        display.changeBoard()
    }
}