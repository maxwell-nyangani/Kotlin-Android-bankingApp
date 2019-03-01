package com.nyanx.max.maxbankieren.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.nyanx.max.maxbankieren.fragments.TutorialStepFragment
import java.util.ArrayList

class TutorialFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var fragmentsList: ArrayList<TutorialStepFragment>? = null

    override fun getItem(position: Int): Fragment? {
        return if (fragmentsList!!.size > 0) {
            this.fragmentsList!![position]
        } else {
            null
        }
    }

    override fun getCount(): Int {
        return fragmentsList!!.size
    }

    fun setFragmentsList(fragmentsList: ArrayList<TutorialStepFragment>) {
        this.fragmentsList = fragmentsList
    }

}
