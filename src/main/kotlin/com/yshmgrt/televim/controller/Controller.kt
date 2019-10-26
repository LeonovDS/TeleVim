package com.yshmgrt.televim.controller

import javafx.beans.property.ObjectProperty
import tornadofx.*

class Controller : tornadofx.Controller() {

    val state : ObjectProperty<State> = State.LOGIN.toProperty()
    fun onCommand(command : String) {
        when (command)
        {
            ":login" -> state.value = State.NULL
            else -> state.value = State.LOGIN
        }
    }
}

enum class State {
    LOGIN,
    NULL,
}