package com.yshmgrt.televim.ui

import javafx.scene.layout.Priority
import tornadofx.*

class StatusView : View() {

    override val root = hbox {
        addClass(Style.sStatusBar)
        textfield {
            hgrow = Priority.ALWAYS
            addClass(Style.sStatusText)
            addClass(Style.sStatusBar)
        }
        label {
            addClass(Style.sStatusText)
            text = ":televim"
        }
   }
}
