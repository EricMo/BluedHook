package com.conch.bluedhook.ui

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import com.conch.bluedhook.R

/**
 * Created by Benjamin on 2018/1/24.
 */
class SettingFragment : PreferenceFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_setting)
    }

    /**
     * when option clicked
     */
    override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
        preference?.let {
            when (preference!!.key) {
                "switch_ads" -> {

                }
                "switch_deposit" -> {

                }
                "switch_game" -> {

                }
                "switch_recall" -> {

                }
                "switch_snaps" -> {

                }
            }
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference)
    }
}