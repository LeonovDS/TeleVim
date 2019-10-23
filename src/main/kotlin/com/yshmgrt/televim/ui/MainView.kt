package com.yshmgrt.televim.ui

import tornadofx.*

class MainView : View() {
    override val root = borderpane {
        top {
            vbox {
                label {
                    text = ":TeleVim"
                }
            }
        }
   }
}