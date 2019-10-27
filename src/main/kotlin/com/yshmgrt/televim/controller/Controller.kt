package com.yshmgrt.televim.controller

import javafx.beans.property.ObjectProperty
import tornadofx.*
import com.yshmgrt.televim.model.Command

class Controller : tornadofx.Controller() {

    val state: ObjectProperty<State> = State.LOGIN.toProperty()

    fun onCommand(command: String) {
        val cmd = Command(command)
        when (cmd.name) {
            "login" -> state.value = State.NULL
            "logout" -> state.value = State.LOGIN
            else -> {
            }
        }
        HistoryController.addCommand(command)
    }
}

enum class State {
    LOGIN,
    NULL,
}
