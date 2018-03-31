package com.conch.bluedhook.common

/**
 * @Description notify
 * @Author Benjamin
 * @CreateDate 2018-03-30 17:31
 **/
object NotifyConstant {

    private var RECALL_BURNING_PIC = 1
    private var RECALL_BURNING_VIDEO = 2
    private val RECALL_MESSAGE = 0

    fun getRECALL_BURNING_PIC(): Int {
        return NotifyConstant.RECALL_BURNING_PIC
    }

    fun getRECALL_BURNING_VIDEO(): Int {
        return NotifyConstant.RECALL_BURNING_VIDEO
    }

    fun getRECALL_MESSAGE(): Int {
        return NotifyConstant.RECALL_MESSAGE
    }
}