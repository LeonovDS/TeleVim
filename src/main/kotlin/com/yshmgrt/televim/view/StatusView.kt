package com.yshmgrt.televim.view

import com.yshmgrt.televim.StateToStringConverter
import com.yshmgrt.televim.controller.Controller
import javafx.beans.property.Property
import javafx.beans.property.SimpleStringProperty
import javafx.scene.input.KeyCode
import javafx.scene.layout.Priority
import tornadofx.*

class StatusView : View() {

    private val controller: Controller by inject()
    private val mode = SimpleStringProperty()
    private val statusList = listOf<Property<String>>(
            mode,
            SimpleStringProperty(":televim")
    )

    init {
        mode.bindBidirectional(controller.state, StateToStringConverter())
    }

    override val root = hbox {
        addClass(Style.sStatusBar)
        textfield {
            hgrow = Priority.ALWAYS
            addClass(Style.sStatusText)
            addClass(Style.sStatusBar)
            setOnKeyPressed {
                when (it.code) {
                    KeyCode.ENTER -> controller.onCommand(text ?: "")
                    else -> {
                    }
                }
            }
        }
        statusList.forEach {
            label(it) {
                addClass(Style.sStatusText)
            }
        }
    }
}
