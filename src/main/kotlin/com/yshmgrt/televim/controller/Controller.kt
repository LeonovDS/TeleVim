package com.yshmgrt.televim.controller

import javafx.beans.property.ObjectProperty
import tornadofx.*

class Controller : tornadofx.Controller() {

    private val commandList = observableList<String>()
    private var last: Int = 0

    val state: ObjectProperty<State> = State.LOGIN.toProperty()

    fun onCommand(command: String) {
        when (command) {
            ":login" -> state.value = State.NULL
            else -> state.value = State.LOGIN
        }
        commandList.add(command)
        last = commandList.size
    }

    fun getPrevCommand(): String? {
        if (commandList.size == 0)
            return null
        if (last == 0)
            return commandList[last]
        return commandList[--last]
    }

    fun getNextCommand(): String? {
        if (commandList.size == last)
            return null
        if (commandList.size - 1 == last) {
            last = commandList.size
            return ""
        }
        return commandList[++last]
    }
}

enum class State {
    LOGIN,
    NULL,
}