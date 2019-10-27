package com.yshmgrt.televim

import com.yshmgrt.televim.controller.Controller

fun main(args: Array<String>) {
    val c = Controller()
    val res = c.onCommand(":login -p +79118388834 -l -pd asd da f d")
    print(res)
}