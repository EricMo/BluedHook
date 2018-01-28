package com.conch.bluedhook.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.conch.bluedhook.R
import kotlinx.android.synthetic.main.include_toolbar.*


class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        toolbar.title = title.toString()
        fragmentManager.beginTransaction().replace(R.id.fragment, SettingFragment()).commit()
    }
}
