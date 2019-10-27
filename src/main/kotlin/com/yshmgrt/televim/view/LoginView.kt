package com.yshmgrt.televim.view

import com.yshmgrt.televim.controller.State
import com.yshmgrt.televim.controller.Controller
import javafx.scene.Node
import javafx.scene.layout.Priority
import tornadofx.*

class LoginView : View() {

    private val controller: Controller by inject()

    override val root = hbox {
        addClass(Style.sLogin)

        visibleWhen { controller.state.isEqualTo(State.LOGIN) }

        region {
            hgrow = Priority.ALWAYS
        }

        vbox {
            label {
                addClass(Style.sLogin)
                text = ":TeleVim"
            }
        }

        region {
            hgrow = Priority.ALWAYS
        }
    }
}

fun Node.loginview(op: LoginView.() -> Unit) {
    this += LoginView().apply(op)
}