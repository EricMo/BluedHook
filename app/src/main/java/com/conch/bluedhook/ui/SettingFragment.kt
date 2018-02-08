package com.conch.bluedhook.ui

import android.app.Activity
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.*
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import com.amap.api.maps2d.model.LatLng
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
        showLatLng()
    }


    /**
     * when option clicked
     */
    override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
        when (preference?.key) {
            "switch_location" -> {
                if ((preference as SwitchPreference).isChecked) {
                    openMap()
                }
            }
        }
        Snackbar.make(activity.findViewById(R.id.root), R.string.restart_setting, Snackbar.LENGTH_SHORT).show()
        return super.onPreferenceTreeClick(preferenceScreen, preference)
    }

    /**
     * open map
     */
    private fun openMap() {
        AlertDialog.Builder(activity)
                .setTitle(R.string.dialogTitle)
                .setMessage(R.string.openmapHint)
                .setNegativeButton(R.string.noNeed) { dialog, _ -> dialog!!.dismiss() }
                .setPositiveButton(R.string.sure) { dialog, _ ->
                    dialog.dismiss()
                    startActivityForResult(Intent(activity, MapActivity::class.java), 1000)
                }
                .create()
                .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1000 && data != null) {
            val latLng = data.getParcelableExtra<LatLng>("LatLng")
            SPHelper.save("latitude", latLng.latitude.toString())
            SPHelper.save("longitude", latLng.longitude.toString())
            showLatLng()
            Snackbar.make(activity.findViewById(R.id.root), R.string.restart_setting, Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(activity.findViewById(R.id.root), R.string.locationNullHint, Snackbar.LENGTH_SHORT).show()
        }
    }


    /**
     * show user
     */
    private fun showLatLng() {
        val latitude = SPHelper.getString("latitude", "")
        val longitude = SPHelper.getString("longitude", "")
        if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
            findPreference("switch_location").title = getString(R.string.switch_location_title)
        } else {
            findPreference("switch_location").title = "${getString(R.string.switch_location_title)}:$latitude,$longitude"
        }
    }
}