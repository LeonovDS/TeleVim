package com.yshmgrt.televim.model

class Parameter(val name: String) {
    val args = mutableListOf<String>()
    override fun toString(): String {
        return args.joinToString(" ", "$name: ")
    }
}
