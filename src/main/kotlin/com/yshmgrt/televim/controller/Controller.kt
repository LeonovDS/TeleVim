package com.yshmgrt.televim.controller

import com.yshmgrt.televim.model.*
import com.yshmgrt.televim.model.Command
import javafx.beans.property.ObjectProperty
import tornadofx.*
import javafx.beans.property.StringProperty

class Controller : tornadofx.Controller() {

    val state: ObjectProperty<State> = State.LOGIN.toProperty()
    val status: StringProperty = "Login".toProperty()

    init{
        val client = createClient(ValGetter(state,status))
    }
    fun onCommand(command: String) {
        val cmd = Command(command)
        when (state.value) {
            State.LOGIN -> {
                when (cmd.name) {
                    "login" -> {

                    }
                    else -> {
                        status.value = "command is unsupported"
                    }
                }
            }
            State.ENTER_PHONE ->{
                sendPhone(client!!,command, ValGetter(state,status))
            }
            State.ENTER_CODE ->{
                sendCode(client!!,command, ValGetter(state,status))
            }
        }

        HistoryController.addCommand(command)
    }
}

enum class State {
    LOGIN,
    NULL,
    ENTER_PHONE,
    ENTER_CODE,
    LOGGED_IN
}
