package com.yshmgrt.televim.controller

import tornadofx.*

object HistoryController {
    private val commandList = observableList<String>()
    private var last: Int = 0

    fun addCommand(cmd: String) {
        commandList.add(cmd)
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