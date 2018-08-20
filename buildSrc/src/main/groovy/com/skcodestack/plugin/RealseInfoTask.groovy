
package com.skcodestack.plugin

import groovy.xml.MarkupBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 自定义task
 */
class RealseInfoTask extends DefaultTask{

    RealseInfoTask(){
        group = 'fastec'
        description = 'update realse version info'
    }


    //dofrist-->doaction-->dolast
    /**
     * 执行于gradle执行阶段的代码
     */
    @TaskAction
    void doAction(){
        updateInfo()
    }


    private void updateInfo(){

        String versionCodeMsg = project.extensions.studyRealseinfo.versionCode
        String versionNameMsg = project.extensions.studyRealseinfo.versionName
        String versionInfoMsg = project.extensions.studyRealseinfo.versionInfo
        String fileName = project.extensions.studyRealseinfo.fileName
        def file = project.file(fileName)
        if (file != null && !file.exists()) {
            file.createNewFile()
        }
        def sw = new StringWriter()
        def xmlBuild = new MarkupBuilder(sw)
        if (file.text != null && file.size() <= 0) {
            xmlBuild.release {
                realse {
                    versionCode(versionCodeMsg)
                    versionName(versionNameMsg)
                    versionInfo(versionInfoMsg)
                }
            }
            file.withWriter { writer ->
                writer.append(sw.toString())
            }
        } else {
            xmlBuild.release {
                versionCode(versionCodeMsg)
                versionName(versionNameMsg)
                versionInfo(versionInfoMsg)
            }
            def lines = file.readLines()
            def lengths = lines.size() - 1

            file.withWriter { writer ->
                lines.eachWithIndex { String entry, int i ->
                    if (i != lengths) {
                        writer.append(entry + '\r\n')
                    } else if (i == lengths) {
                        writer.append('\r\r\n' + sw.toString() + '\r\n')
                        writer.append(entry)
                    }

                }
            }
        }
    }
}