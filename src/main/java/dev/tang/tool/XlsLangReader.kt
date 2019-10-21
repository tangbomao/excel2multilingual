package dev.tang.tool

import dev.tang.tool.export.LangExport
import dev.tang.tool.logger.Logger
import jxl.Workbook
import java.io.File
import java.util.*

/**
 * 语言文本处理
 *
 * 处理规则如下:
 * 第一列为文本代号
 * 第二列开始为每个语言的语言文本
 *
 * @author TangYing
 */
class XlsLangReader(private val filePath: String) {

    companion object {
        private val LOG = Logger(XlsLangReader::class.java)
    }

    /**
     * 语言文本数据
     *
     * 格式如下
     * 语言文本名称->数据（key, value）
     */
    private val datas = HashMap<String, LinkedList<Map<String, Any>>>()

    /**
     * 导出文件
     */
    fun read() {
        val fileName = filePath.substring(0, filePath.lastIndexOf("."))
        val file = File("$fileName.xls")

        val wwb: Workbook
        try {
            // 获取内容
            wwb = Workbook.getWorkbook(file)

            // 获取分页第一页
            val sheet = wwb.getSheet(0)

            // 获取第一行，第一行为名称行
            val firstRows = sheet.getRow(0)

            // 获取总行数
            val totalRowNum = sheet.rows
            val totalColumnNum = firstRows.size

            // 从第二行开始循环加载数据
            for (i in 1 until totalRowNum) {
                // 获取当前行所有数据
                val cells = sheet.getRow(i)
                if (cells == null || cells.isEmpty()) {
                    continue
                }

                // 每一行第一列为多语言文本替换名称
                val nameKey = cells[0].contents

                // 从第二列开始加载数据
                for (j in 1 until totalColumnNum) {
                    // 所属语言文本
                    val lang = firstRows[j].contents
                    if ("" == lang) {
                        continue
                    }

                    // 多语言数据列表
                    val langList = datas.getOrPut(lang) { LinkedList() }

                    // 获取当前单元格文本
                    val value = if (cells.size <= j) "" else if (cells[j] == null) "" else cells[j].contents

                    // 临时数据存储器
                    val map = LinkedHashMap<String, Any>()
                    map["msg"] = nameKey
                    map["value"] = value

                    // 放入数据列表
                    langList.add(map)
                }
            }

            // 批量导出数据
            for ((name, data) in datas) {

                val langExport = LangExport(name, data)
                langExport.export()
            }

            wwb.close()
        } catch (e: Exception) {
            LOG.error("{0}, {1}", fileName, e.toString())
            e.printStackTrace()
        }

    }
}
