package com.conch.bluedhook.common

/**
 * Created by Benjamin on 2017/7/3.
 */

object HookConstant {
    //the main process
    val processName = "com.soft.blued"
    // msgFragment
    val msgFragment = "$processName.ui.msg.MsgFragment"
    //msg adapter
    val msgAdapter = "$processName.ui.msg.adapter.MessageChatAdapter"
    //chat model
    val chatModel = "com.blued.android.chat.model.ChattingModel"
    //chatting ui
    val chatUI = "$processName.ui.msg.MsgChattingFragment"
    //welcomeUI
    val welcomeUI = "$processName.ui.welcome.WelcomeFragmentNew"
    //NearByWithAds
    val nearByWithAds = "$processName.ui.find.model.NearByWithAds"
    //DistanceUI{Grid}
    val peopleGridQuickAdapter = "$processName.ui.find.adapter.PeopleGridQuickAdapter"
    //BaseViewHolder
    val baseViewHolder = "com.chad.library.adapter.base.BaseViewHolder"
    //find
    val userFindResult = "$processName.ui.find.model.UserFindResult"
    //discoverSquareUI
    val discoverSquareFragment = "$processName.ui.discover.fragment.DiscoverySquareFragment"
    //BluedEntityA
    val bluedEntityA = "com.blued.android.similarity.http.parser.BluedEntityA"
    //homePageMore
    val mineFragment = "$processName.ui.discover.fragment.MineFragment"
    //basefragment
    val baseFragment = "com.blued.android.ui.BaseFragment"
    //chatHelper
    val chatHelpler = "$processName.ui.msg.controller.tools.ChatHelperV4"
    //chatManager
    val chatManager = "com.blued.android.chat.ChatManager"
    //chatfragment
    val chatFragment = "$processName.ui.msg.MsgChattingFragment"
    //
    val albumManager = "$processName.ui.user.adapter.AlbumDataManager"

    val visitorFragment="$processName.ui.find.fragment.MyVisitorFragment"

    val visitorAdapter="$processName.ui.find.adapter.VisitorListAdapter"

    //Preferences
    val preferences = "$processName.utils.BluedPreferences"

    //userInfo
    val userInfo="$processName.user.UserInfo"

    //_more_columns
    val _more_columns="$processName.ui.discover.model.MineEntryInfo\$_more_columns"

    //
    val colAdapter="$processName.ui.discover.adapter.MineEntryAdapter"

    val colViewHolder="$colAdapter\$ViewHolder"

    //md5Key-BenjaminKeyLastType
    val key = "61C0A240C4AF5F16DA0738512255BA16"

}

object SelfHookConstant {
    //self processName
    val processName = "com.conch.bluedhook"
    //main activity
    val mainActivity = "$processName.ui.MainActivity"
}
