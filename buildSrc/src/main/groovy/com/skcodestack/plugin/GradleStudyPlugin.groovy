package com.skcodestack.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleStudyPlugin implements Plugin<Project> {

    /**
     * 插件引入执行的方法
     * @param target The target object
     */
    @Override
    void apply(Project project) {
        project.extensions.create("studyRealseinfo", RealseInfoExtention)
        project.tasks.create("studyRealseInfoTask", RealseInfoTask)

    }
}