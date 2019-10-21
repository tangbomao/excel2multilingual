package dev.tang.tool.export

import dev.tang.tool.util.PathUtils

/**
 * GSON数据格式文本导出
 *
 * @author TangYing
 */
class LangExport(fileName: String, private val datas: List<Map<String, Any>>) : AbstractExport(fileName, "txt") {

    init {

        this.fileName = fileName
    }

    override fun export() {
        val exportUrl = PathUtils.langPath + fileName + suffix

        // Generate
        val sb = StringBuffer()

        for (map in datas) {
            val msg = map["msg"].toString()
            val value = map["value"].toString()

            if (msg.startsWith("#")) {
                sb.append(msg)
            } else if ("" != msg) {
                sb.append(msg).append("=").append(value)
            }
            sb.append("\r\n")
        }

        // Write
        writeJsonFile(exportUrl, sb)
    }
}
