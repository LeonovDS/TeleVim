package com.yshmgrt.televim.view

import tornadofx.*

class MainView : View() {
    override val root = borderpane {
        center {
            loginview { }
        }
        bottom {
            vbox {
                statusview { }
            }
        }
    }
}