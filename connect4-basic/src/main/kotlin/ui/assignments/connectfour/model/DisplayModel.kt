package ui.assignments.connectfour.model

import ui.assignments.connectfour.ui.IView

class DisplayModel {
    private val views: ArrayList<IView> = ArrayList()

    fun addView(view: IView) {
        views.add(view)
        view.updateView()
    }

    fun removeView(view: IView?) {

    }

    fun startGame() {
        notifyObservers()
    }

    private val pieces = mutableListOf<Piece>()



    fun addPiece(x: Int, y:Int) {
        val piece = Piece(x, y)
        pieces.add(piece)
        notifyObservers()
    }

    fun getPiece(index: Int) : Piece {
        return pieces[index]
    }

    fun getCurNumberPieces() : Int {
        return pieces.size
    }

    private fun notifyObservers() {
        for (view in views) {
            println("Model: notify View")
            view.updateView()
        }
    }
}