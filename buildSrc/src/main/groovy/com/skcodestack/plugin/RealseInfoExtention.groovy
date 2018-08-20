package com.skcodestack.plugin

/**
 * 扩展，用于传参
 */
class RealseInfoExtention {

    String versionCode
    String versionName
    String versionInfo
    String fileName

    RealseInfoExtention() {
    }


    @Override
    public String toString() {
        return "RealseInfoExtention{" +
                "versionCode='" + versionCode + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionInfo='" + versionInfo + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}