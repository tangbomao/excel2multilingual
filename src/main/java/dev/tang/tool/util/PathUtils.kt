package dev.tang.tool.util

import java.io.File
import java.util.*

object PathUtils {

    val rootPath: String = File("").absolutePath

    val langPath: String = rootPath + File.separator + "locale" + File.separator

    val loggerPath: String = rootPath + File.separator + "error"

    const val xlsSuffix: String = "xls"

    /**
     * List file with suffix in root path
     *
     * @param suffix
     * @return
     */
    fun listFile(suffix: String?): List<String> {
        val list = ArrayList<String>()

        val file = File(rootPath)
        if (file.isDirectory) {
            val files = file.listFiles()
            for (i in files.indices) {
                val filePath = files[i].name
                if (suffix != null) {
                    val begIndex = filePath.lastIndexOf(".")
                    var tempSuffix = ""
                    if (begIndex != -1) {
                        tempSuffix = filePath.substring(begIndex + 1, filePath.length)
                    }
                    if (tempSuffix == suffix) {
                        list.add(filePath)
                    }
                } else {
                    list.add(filePath)
                }
            }
        }
        return list
    }
}
