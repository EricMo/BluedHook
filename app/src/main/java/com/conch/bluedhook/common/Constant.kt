package com.conch.bluedhook.common

/**
 * Created by Benjamin on 2017/7/3.
 */

object HookConstant {
    //the main process
    val processName = "com.soft.blued"
    //msg adapter
    val msgAdapter = "$processName.ui.msg.adapter.MessageChatAdapter"
    //chat model
    val chatModel = "com.blued.android.chat.model.ChattingModel"
    //chatting ui
    val chatUI = "$processName.ui.msg.MsgChattingFragment"
    //welcomeUI
    val welcomeUI = "$processName.ui.welcome.WelcomeFragment"
    //NearByWithAds
    val nearByWithAds = "$processName.ui.find.model.NearByWithAds"
    //DistanceUI{Grid}
    val distanceGrid4Adapter = "$processName.ui.find.adapter.FriendsGrid4Adapter"
    //GridViewHolder
    val distanceGrid4AdapterHolder = "$distanceGrid4Adapter\$ViewHolder"
    //DistanceUI{List}
    val distanceListAdapter = "$processName.ui.find.adapter.FriendsListAdapter"
    //ListViewHolder
    val distanceListAdapterHolder = "$distanceListAdapter\$ViewHolder"
    //discoverSquareUI
    val discoverSquareFragment = "$processName.ui.discover.fragment.DiscoverySquareFragment"
    //BluedEntityA
    val bluedEntityA = "com.blued.android.http.parser.BluedEntityA"
    //my tab
    val homePageMore = "$processName.ui.discover.fragment.HomePageMore"

    val flashPic = "$processName.ui.video.ShowVideoDestroyFragment"

    //basefragment
    val baseFragment = "com.blued.android.ui.BaseFragment"
    //chatHelper
    val chatHelpler = "$processName.ui.msg.controller.tools.ChatHelperV4"
    //chatManager
    val chatManager = "com.blued.android.chat.ChatManager"

    //chatfragment
    val chatFragment = "$processName.ui.msg.MsgChattingFragment"

    //md5Key-BenjaminKeyLastType
    val key = "61C0A240C4AF5F16DA0738512255BA16"

}

object SelfHookConstant {
    //self processName
    val processName = "com.conch.bluedhook"
    //main activity
    val mainActivity = "$processName.ui.MainActivity"
}