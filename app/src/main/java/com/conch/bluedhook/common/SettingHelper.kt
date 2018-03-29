package com.conch.bluedhook.common

import android.content.Context
import com.conch.bluedhook.prefs_utils.SPHelper
import de.robv.android.xposed.XposedBridge


class SettingHelper(mContext: Context) {
    private val switch_ads = "switch_ads"
    private val switch_game = "switch_game"
    private val switch_deposit = "switch_deposit"
    private val switch_burn = "switch_burn"
    private val switch_recall = "switch_recall"
    private val switch_location = "switch_location"

    object LatLng {
        val latitude = "latitude"
        val longitude = "longitude"
    }

    init {
        SPHelper.init(mContext.applicationContext)
    }

    fun getIsRemoveAds(): Boolean {
//        val isAds = SPHelper.getBoolean(switch_ads, false)
//        XposedBridge.log("isAds========:" + isAds)
        return true
    }

    fun getIsSwitchGame(): Boolean {
//        val removeGame = SPHelper.getBoolean(switch_game, false)
//        XposedBridge.log("removeGame========:" + removeGame)
        return true
    }

    fun getIsSwitchDeposit(): Boolean {
//        val removeDeposit = SPHelper.getBoolean(switch_deposit, false)
//        XposedBridge.log("removeDeposit========:" + removeDeposit)
        return true
    }

    fun getIsSwitchBurn(): Boolean {
//        val removeBurn = SPHelper.getBoolean(switch_burn, false)
//        XposedBridge.log("removeBurn========:" + removeBurn)
        return true
    }

    fun getIsSwitchRecall(): Boolean {
//        val removeRecall = SPHelper.getBoolean(switch_recall, false)
//        XposedBridge.log("removeRecall========:" + removeRecall)
        return true
    }

    fun getIsFakeLocation(): Boolean {
//        val fakeLocation = SPHelper.getBoolean(switch_location, false)
//        XposedBridge.log("fakeLocation========:" + fakeLocation)
        return true
    }

    fun getLatitude(): String {
        val latitude = SPHelper.getString(LatLng.latitude, "")
        XposedBridge.log("latitude========:" + latitude)
        return latitude
    }

    fun getLongitude(): String {
        val longitude = SPHelper.getString(LatLng.longitude, "")
        XposedBridge.log("longitude========:" + longitude)
        return longitude
    }

}