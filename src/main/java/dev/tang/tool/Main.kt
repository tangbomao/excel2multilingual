package dev.tang.tool

import dev.tang.tool.util.FileUtils
import dev.tang.tool.util.PathUtils

/**
 * 主入口
 *
 * @author TangYing
 */
class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // 删除错误日志
            FileUtils.deleteFile(PathUtils.loggerPath)

            // 新建语言文件夹
            FileUtils.mkdir(PathUtils.langPath)

            // 获得Excel文件
            val fileList = PathUtils.listFile(PathUtils.xlsSuffix)
            for (filePath in fileList) {
                val xlsLangReader = XlsLangReader(filePath)
                xlsLangReader.read()
            }
        }
    }
}