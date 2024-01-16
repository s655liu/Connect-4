package ui.assignments.connectfour

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import ui.assignments.connectfour.controller.Controller
import ui.assignments.connectfour.model.DisplayModel
import ui.assignments.connectfour.ui.GridView


class ConnectFourApp : Application() {
    override fun start(stage: Stage) {

        val displayModel = DisplayModel()

        val controller = Controller(displayModel)

        val display = GridView(displayModel, controller)




        val scene = Scene(display, 1400.0, 1000.0)
        stage.title = "CS349 - A3 Connect Four - s655liu"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }
}