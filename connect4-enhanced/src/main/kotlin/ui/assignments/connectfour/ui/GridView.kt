package ui.assignments.connectfour.ui

import javafx.animation.*
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.util.Duration
import ui.assignments.connectfour.controller.Controller
import ui.assignments.connectfour.draggable.DragInfo
import ui.assignments.connectfour.model.DisplayModel
import ui.assignments.connectfour.model.Model
import ui.assignments.connectfour.model.Player

class GridView(private val displayModel: DisplayModel, private val controller: Controller) : Pane(), IView {
    // board
    private var board = Image(GridView::class.java.getResourceAsStream("/ui/assignments/connectfour/grid_8x7.png"))
    private var boardX = 700.0 - board.width / 2
    private var boardY = 300.0

    private var boardDisplay = ImageView(board).apply {
        translateX = boardX
        translateY = boardY
    }

    // button
    private val startButton = Button("Click here to start game!").apply {
        translateX = (1400.0 / 2) - 180.0
        translateY = 150.0
        font = Font.font(30.0)
    }

    private var gameStarted = false


    // players
    private val player1Label = Text("Player #1").apply {
        translateX = 15.0
        translateY = 30.0
        font = Font.font(30.0)
    }
    private val player2Label = Text("Player #2").apply {
        translateX = 1400.0 - 130.0
        translateY = 30.0
        font = Font.font(30.0)
    }

    // pieces
    private var dragInfo = DragInfo()

    private val redPiece = Image(GridView::class.java.getResourceAsStream(
        "/ui/assignments/connectfour/piece_red.png")).apply {

    }
    private val yellowPiece = Image(GridView::class.java.getResourceAsStream(
        "/ui/assignments/connectfour/piece_yellow.png"))

    private val radius = redPiece.width

    private fun inBoard(PosX: Double): Boolean {
        if (PosX <= boardX || PosX >= boardX + board.width) {
            return false
        }
        return true
    }

    private fun boardPos(PosX: Double): Int {
        return (((PosX - boardX) / 100).toInt() % Model.width)
    }


    private fun addDrag(piece: ImageView) {
        piece.apply {
            addEventFilter(MouseEvent.MOUSE_PRESSED) {
                dragInfo = DragInfo(piece, it.sceneX, it.sceneY, translateX, translateY)
            }
            addEventFilter(MouseEvent.MOUSE_DRAGGED) {
                translateX = dragInfo.initialX + it.sceneX - dragInfo.anchorX
                translateY = dragInfo.initialY + it.sceneY - dragInfo.anchorY
            }
            addEventFilter(MouseEvent.MOUSE_RELEASED) {
                if (!inBoard(translateX)) {
                    dragInfo = DragInfo()
                } else {
                    val xPos = boardPos(translateX)
                    translateX = boardX + xPos * 100 + 10.0
                    Model.dropPiece(xPos)
                    val p= Model.onPieceDropped.value
                    if (p == null) {
                        dragInfo = DragInfo()
                    } else {
                        val animation = Timeline(KeyFrame(Duration.millis(1000.0),
                                KeyValue(
                                    this.translateYProperty(), 1000.0 - Model.height * 100 + 10.0
                                            + p!!.y * 100,
                                    Interpolator.EASE_IN
                                )
                            )
                        )
                        animation.play()
                        animation.onFinished = EventHandler {
                            controller.addPiece(translateX.toInt(), (1000.0 - Model.height * 100 + 10.0
                                    + p!!.y * 100).toInt())
                        }
                    }
                }
            }

            // bounding the piece
            addEventHandler(MouseEvent.MOUSE_DRAGGED) {
                if (translateY >= boardY - radius) {
                    if (translateX >= boardX - radius && translateX <= boardX) {
                        translateX = boardX - radius
                    } else if (translateX <= boardX + board.width
                        && translateX >= boardX + board.width - radius){
                        translateX = boardX + board.width
                    } else if (translateX > boardX - radius && translateX < boardX + board.width) {
                        translateY = boardY - radius
                    }
                }

                if (translateX < 0) {
                    translateX = 0.0
                } else if (translateX > 1400.0 - radius) {
                    translateX = 1400.0 - radius
                }

                if (translateY < 0) {
                    translateY = 0.0
                } else if (translateY > 1000.0 - radius) {
                    translateY = 1000.0 - radius
                }
            }
        }
    }

    private val yellowPieceDisplay = ImageView(yellowPiece).apply {
        translateX = 1400.0 - 200.0
        translateY = 100.0
    }

    private val redPieceDisplay = ImageView(redPiece).apply {
        translateX = 100.0
        translateY = 100.0
    }

    // messages
    private val drawMessage = Text ("Game Ended in Draw").apply {
        translateX = 1400.0 / 2 - 100
        translateY = 150.0
        font = Font.font(30.0)
    }

    private var winner = "#1"

    // enhancements
    private val buttonLabel = Label("Grid size:").apply {
        font = Font.font(30.0)
    }

    private val button8x7 = Button("8x7").apply {
        font = Font.font(30.0)
    }

    private val button7x6 = Button("7x6").apply {
        font = Font.font(30.0)
    }

    private val button6x5 = Button("6x5").apply {
        font = Font.font(30.0)
    }

    private val button5x4 = Button("5x4").apply {
        font = Font.font(30.0)
    }

    private val button4x3 = Button("4x3").apply {
        font = Font.font(30.0)
    }

    private val buttonList = mutableListOf(button4x3, button5x4, button6x5, button7x6, button8x7)

    private val gridButtons = HBox(buttonLabel, button8x7, button7x6, button6x5, button5x4, button4x3).apply {
        translateX = 700.0 - 330.0
        spacing = 10.0
        alignment = Pos.CENTER_LEFT
    }


    private fun changeBoard(height: Int) {
        if (height == 7) {
            board = Image(GridView::class.java.getResourceAsStream("/ui/assignments/connectfour/grid_8x7.png"))
        } else if (height == 6) {
            board = Image(GridView::class.java.getResourceAsStream("/ui/assignments/connectfour/grid_7x6.png"))
        } else if (height == 5) {
            board = Image(GridView::class.java.getResourceAsStream("/ui/assignments/connectfour/grid_6x5.png"))
        } else if (height == 4) {
            board = Image(GridView::class.java.getResourceAsStream("/ui/assignments/connectfour/grid_5x4.png"))
        } else if (height == 3) {
            board = Image(GridView::class.java.getResourceAsStream("/ui/assignments/connectfour/grid_4x3.png"))
        }

        boardX = 700.0 - board.width / 2
        boardY = 1000.0 - height * 100
        boardDisplay = ImageView(board).apply {
            translateX = boardX
            translateY = boardY
        }
        (0..4).forEach {
            buttonList[it].isDisable = (it == height - 3)
        }

        Model.height = height
        Model.width = height + 1
        print(Model.height)
        print(" ")
        println(Model.width)
        Model.updataGrid()
    }

    init {
        startButton.onAction = EventHandler {
            gameStarted = true
            Model.startGame()
            controller.startGame()
            addDrag(redPieceDisplay)
            addDrag(yellowPieceDisplay)
        }

        (0..4).forEach {
            val x = it
            buttonList[x].onAction = EventHandler {
                changeBoard((x + 3))
                controller.changeBoard()
            }
        }

        displayModel.addView(this)
    }

    override fun updateView() {
        println("Update View")
        children.clear()

        if (!gameStarted) {
            children.add(startButton)
            children.add(gridButtons)
        }

        yellowPieceDisplay.apply {
            translateX = 1400.0 - 200.0
            translateY = 100.0
        }

        redPieceDisplay.apply {
            translateX = 100.0
            translateY = 100.0
        }

        children.addAll(player1Label, player2Label, redPieceDisplay, yellowPieceDisplay)

        if (Model.onNextPlayer.value == Player.ONE) {
            yellowPieceDisplay.isDisable = true
            redPieceDisplay.isDisable = false
        } else if (Model.onNextPlayer.value == Player.TWO) {
            yellowPieceDisplay.isDisable = false
            redPieceDisplay.isDisable = true
        }

        (0 until displayModel.getCurNumberPieces()).forEach {
            val piece = displayModel.getPiece(it)
            if (it % 2 == 1) {
                val boardPiece = ImageView(yellowPiece).apply {
                    translateX = piece.x.toDouble()
                    translateY = piece.y.toDouble()
                }
                children.add(boardPiece)
            } else {
                val boardPiece = ImageView(redPiece).apply {
                    translateX = piece.x.toDouble()
                    translateY = piece.y.toDouble()
                }
                children.add(boardPiece)
            }
        }

        children.add(boardDisplay)

        if (Model.onGameDraw.value == true) {
            redPieceDisplay.isDisable = true
            yellowPieceDisplay.isDisable = true
            children.add(drawMessage)
        } else if (Model.onGameWin.value == Player.ONE || Model.onGameWin.value == Player.TWO) {
            if (Model.onGameWin.value == Player.TWO) {
                winner = "#2"
            }
            val winMessage = Text ("Player $winner wins!").apply {
                translateX = 1400.0 / 2 - 300.0
                translateY = 150.0
                font = Font.font(100.0)
            }
            redPieceDisplay.isDisable = true
            yellowPieceDisplay.isDisable = true
            children.add(winMessage)
        }

    }
}