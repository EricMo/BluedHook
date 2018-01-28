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

    init {
        SPHelper.init(mContext.applicationContext)
    }

    fun getIsRemoveAds(): Boolean {
        val isAds = SPHelper.getBoolean(switch_ads, false)
        XposedBridge.log("isAds========:" + isAds)
        return isAds
    }

    fun getIsSwitchGame(): Boolean {
        val removeGame = SPHelper.getBoolean(switch_game, false)
        XposedBridge.log("removeGame========:" + removeGame)
        return removeGame
    }

    fun getIsSwitchDeposit(): Boolean {
        val removeDeposit = SPHelper.getBoolean(switch_deposit, false)
        XposedBridge.log("removeDeposit========:" + removeDeposit)
        return removeDeposit
    }

    fun getIsSwitchBurn(): Boolean {
        val removeBurn = SPHelper.getBoolean(switch_burn, false)
        XposedBridge.log("removeBurn========:" + removeBurn)
        return removeBurn
    }

    fun getIsSwitchRecall(): Boolean {
        val removeRecall = SPHelper.getBoolean(switch_recall, false)
        XposedBridge.log("removeRecall========:" + removeRecall)
        return removeRecall
    }

}