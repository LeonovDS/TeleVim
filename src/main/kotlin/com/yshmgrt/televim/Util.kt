package com.yshmgrt.televim

import com.yshmgrt.televim.controller.State
import javafx.util.StringConverter

class StateToStringConverter : StringConverter<State>() {
    override fun toString(p0: State?) = p0?.toString() ?: ""

    override fun fromString(p0: String?): State {
        return State.NULL
    }

}