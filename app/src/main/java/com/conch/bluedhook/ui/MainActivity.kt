package com.conch.bluedhook.ui

import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.conch.bluedhook.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MainActivity : AppCompatActivity() {

    private var askSupport: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //check activated state
        if (isActivated()) {
            state.text = getString(R.string.success)
            state.setTextColor(ContextCompat.getColor(this, R.color.colorGreen))
        } else {
            state.text = getString(R.string.failed)
            state.setTextColor(ContextCompat.getColor(this, R.color.colorRed))
        }
        setSupportActionBar(toolbar)
        toolbar.title = title.toString()
    }

    /**
     * get isActivated state
     * We must hook this method in {@link SelfModule} and Return true
     */
    private fun isActivated(): Boolean {
        return false
    }

    /**
     * create OptionsMenu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mian_menu, menu)
        return true
    }

    /**
     * when menu selected
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.link_me -> {
                linkMe()
                return@onOptionsItemSelected true
            }
        }
        return@onOptionsItemSelected false
    }

    override fun onResume() {
        super.onResume()
        if (askSupport) {
            askSupport = false
            Snackbar.make(root, R.string.openApplication, Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * open blued and go to user's profile
     */
    private fun linkMe() {
        AlertDialog.Builder(this)
                .setTitle(R.string.dialogTitle)
                .setMessage(R.string.supportTips)
                .setPositiveButton(R.string.sure) { _, _ ->
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_LAUNCHER)
                    intent.action = "android.intent.action.VIEW"
                    val cn = ComponentName("com.soft.blued", "com.soft.blued.ui.welcome.FirstActivity")
                    intent.component = cn
                    intent.data = Uri.parse("blued://native.blued.cn?action=profile&enc=1&uid=aOvL2v")
                    startActivity(intent)
                    askSupport = true
                }
                .create()
                .show()

    }
}
