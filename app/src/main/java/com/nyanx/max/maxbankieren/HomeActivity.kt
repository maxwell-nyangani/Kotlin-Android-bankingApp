package com.nyanx.max.maxbankieren

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.nyanx.max.maxbankieren.customViews.BadgedButton
import com.nyanx.max.maxbankieren.fragments.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    inner class HomeActivityUI(
        private var layout: View,
        private var accountsFrag: AccountsFragment,
        private var selfServiceFrag: SelfServiceFragment,
        private var bankingMailFrag: BankMailFragment,
        private var settingsFrag: SettingsFragment,
        private var taskListFrag: TaskListFragment
    ) : BadgedButton.BadgedButtonEventHandlers {
        var mContext = layout.context
        private lateinit var manager: FragmentManager
        private var selectedTab:BadgedButton = accountsTabBtn

        init {
            //initialize manager
            manager = supportFragmentManager
            //set event handlers on the  tab buttons
            accountsTabBtn.setSelectedState(true)
            accountsTabBtn.setEventHandlers(this)
            selfServiceTabBtn.setEventHandlers(this)
            bankMailTabBtn.setEventHandlers(this)
            settingsTabBtn.setEventHandlers(this)
            taskListTabBtn.setEventHandlers(this)
            showFragmentForTab(1)
        }

        override fun onBadgedButtonClicked(v: View) {
            when(v.id){
                R.id.accountsTabBtn->{
                    showFragmentForTab(1)
                }
                R.id.selfServiceTabBtn->{
                    showFragmentForTab(2)
                }
                R.id.bankMailTabBtn->{
                    showFragmentForTab(3)
                }
                R.id.settingsTabBtn->{
                    showFragmentForTab(4)
                }
                R.id.taskListTabBtn->{
                    showFragmentForTab(5)
                }
            }
        }

        override fun onBadgedButtonLongClicked(v: View) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private fun showFragmentForTab(tab: Int) {
            val transaction = manager.beginTransaction()
            //transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            var fragmentToShow: Fragment
            when (tab) {
                1 -> {
                    selectedTab.setButtonSelected(false)
                    accountsTabBtn.setSelectedState(true)
                    selectedTab = accountsTabBtn
                    fragmentToShow = accountsFrag
                }
                2-> {
                    selectedTab.setButtonSelected(false)
                    selfServiceTabBtn.setButtonSelected(true)
                    selectedTab = selfServiceTabBtn
                    fragmentToShow = selfServiceFrag
                }
                3 -> {
                    selectedTab.setSelectedState(false)
                    bankMailTabBtn.setButtonSelected(true)
                    selectedTab = bankMailTabBtn
                    fragmentToShow = bankingMailFrag
                }
                4 -> {
                    selectedTab.setSelectedState(false)
                    settingsTabBtn.setButtonSelected(true)
                    selectedTab = settingsTabBtn
                    fragmentToShow = settingsFrag
                }
                5 -> {
                    selectedTab.setSelectedState(false)
                    taskListTabBtn.setButtonSelected(true)
                    selectedTab = taskListTabBtn
                    fragmentToShow = taskListFrag
                }
                else->{
                    fragmentToShow = accountsFrag
                }
            }
            transaction.replace(homeTabBodyContainerFrmLyt.id, fragmentToShow)
            transaction.commitAllowingStateLoss()
        }
    }

    private lateinit var ui:HomeActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        ui = HomeActivityUI(findViewById(R.id.activity_home),AccountsFragment(), SelfServiceFragment(), BankMailFragment(), SettingsFragment(), TaskListFragment())
    }
}
