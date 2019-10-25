package com.yshmgrt.televim.ui

import tornadofx.*

class Style : Stylesheet() {

    companion object {
        val cStatusBar = c(0,0, 0)
        val cStatusText = c(255, 255, 255)

        val sStatusBar by cssclass()
        val sStatusText by cssclass()
    }

    init {
        sStatusBar {
            backgroundColor = multi(cStatusBar)
        }

        sStatusText {
            padding = box(1.px, 10.px)
            textFill = cStatusText
            borderWidth += box(0.px, 1.px, 0.px, 0.px)
            borderColor += box(cStatusText)
        }
    }

}