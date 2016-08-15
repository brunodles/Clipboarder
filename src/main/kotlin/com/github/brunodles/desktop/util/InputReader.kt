package com.github.brunodles.desktop.util

import java.util.*

/**
 * Created by bruno on 14/08/16.
 */
class InputReader(val exitCommand: String) {

    private val scanner = Scanner(System.`in`)
    private val commands: HashMap<Regex, (result : MatchResult) -> Unit> = HashMap()

    fun add(command: Regex, action: (result : MatchResult) -> Unit): InputReader {
        commands.put(command, action)
        return this
    }

    fun start() {
        while (true) {
            val input = scanner.nextLine()
            if ("$exitCommand".equals(input)) break
            commands.forEach { command, action ->
                val find = command.find(input)
                if (find != null) action.invoke(find)
            }
        }
    }
}