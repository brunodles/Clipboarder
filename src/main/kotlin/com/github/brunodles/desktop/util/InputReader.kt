package com.github.brunodles.desktop.util

import java.util.*

/**
 * Created by bruno on 14/08/16.
 */
class InputReader(val exitCommand: String) {

    private val scanner = Scanner(System.`in`)
    private val commands: HashMap<Regex, () -> Unit> = HashMap()

    fun add(command: Regex, action: () -> Unit): InputReader {
        commands.put(command, action)
        return this
    }

    fun start() {
        while (true) {
            val input = scanner.nextLine()
            if ("$exitCommand".equals(input)) break
            commands.forEach { command, action ->
                if (command.matches(input)) action.invoke()
            }
        }
    }
}