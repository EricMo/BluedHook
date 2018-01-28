package com.conch.bluedhook.ui

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.support.design.widget.Snackbar
import com.conch.bluedhook.R
import com.conch.bluedhook.prefs_utils.SPHelper

/**
 * Created by Benjamin on 2018/1/24.
 */
class SettingFragment : PreferenceFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_setting)
        SPHelper.init(activity.application)
    }

    /**
     * when option clicked
     */
    override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
        Snackbar.make(activity.findViewById(R.id.root), R.string.restart_setting, Snackbar.LENGTH_SHORT).show()
        return super.onPreferenceTreeClick(preferenceScreen, preference)
    }
}