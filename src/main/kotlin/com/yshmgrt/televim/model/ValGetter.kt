package com.yshmgrt.televim.model

import com.yshmgrt.televim.controller.State
import javafx.application.Platform
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import tornadofx.toProperty


class ValGetter(var state: ObjectProperty<State>, var status: StringProperty):ValuesGetter {
    override fun getPhoneNumber(){
        Platform.runLater {
            state.value = State.ENTER_PHONE
            status.value = "enter your phone number"
        }
    }

    override fun getCode(){
        Platform.runLater {
            state.value = State.ENTER_CODE
            status.value = "enter your code"
        }
    }

    override fun onReady() {
        Platform.runLater{
            state.value  = State.LOGGED_IN
            status.value = "correctly logged in"
        }
    }

}