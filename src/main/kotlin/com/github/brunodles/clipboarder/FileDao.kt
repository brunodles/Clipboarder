package com.github.brunodles.clipboarder

import java.io.File
import java.util.*

/**
 * Created by bruno on 14/08/16.
 */
class FileDao(val dirPath : String) {

    fun save(name: String, txt: String) {
        val dir = File(dirPath)
        if (!dir.exists()) dir.mkdir()
        File("$dirPath/$name").printWriter().use { out ->
            out.print(txt)
        }
    }

    fun list(): HashMap<String, String> {
        val list = File(dirPath).list()
        val result = HashMap<String, String>()
        list.forEach { f ->
            result.put(f, File("$dirPath/$f").readLines().joinToString(separator = " "))
        }
        return result
    }
}