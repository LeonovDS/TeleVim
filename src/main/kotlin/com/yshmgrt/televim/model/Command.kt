package com.yshmgrt.televim.model

class Command(src: String) {
    var name = ""
    val param = mutableListOf<Parameter>()

    init {
        val terms = src.trim().split(" ")
        if (terms.isNotEmpty())
            if (terms[0].startsWith(':')) {
                name = terms[0].slice(1 until terms[0].length)
                for (term in terms) {
                    if (term.startsWith(':'))
                        continue
                    if (term.startsWith('-'))
                        param += Parameter(term.slice(1 until term.length))
                    else
                        param.last().args += term
                }
            }
    }

    override fun toString(): String {
        return "Cmd: " + param.joinToString("\n", name + "\n")
    }
}
