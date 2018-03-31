package com.conch.bluedhook.common

import android.content.res.XModuleResources
import com.conch.bluedhook.R


object ResourcesProvider {

    lateinit var extra_burning_pic_hint: String
    lateinit var extra_burning_video_hint: String
    lateinit var extra_recall_hint: String

    fun initProvider(res: XModuleResources) {
        extra_recall_hint = res.getString(R.string.extra_recall_hint)
        extra_burning_pic_hint = res.getString(R.string.extra_burning_pic_hint)
        extra_burning_video_hint = res.getString(R.string.extra_burning_video_hint)
    }
}