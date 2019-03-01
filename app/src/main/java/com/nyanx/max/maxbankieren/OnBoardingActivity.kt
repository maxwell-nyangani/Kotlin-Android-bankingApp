package com.nyanx.max.maxbankieren

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.nyanx.max.maxbankieren.adapters.TutorialFragmentPagerAdapter
import com.nyanx.max.maxbankieren.fragments.TutorialStepFragment
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {

    inner class OnBoardingActivityUI(layout: View, orderedTutorialPageList:ArrayList<TutorialStepFragment> ) :
        ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(p0: Int) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onPageSelected(p0: Int) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private var context: Context = layout.context
        private var orderedTutorialPageList: ArrayList<TutorialStepFragment> = orderedTutorialPageList

        private var pagerAdapter: TutorialFragmentPagerAdapter

        init {
            tutorialPagesVwPgr.addOnPageChangeListener(this)
            pagerAdapter = TutorialFragmentPagerAdapter(supportFragmentManager)
            pagerAdapter.setFragmentsList(orderedTutorialPageList)
            tutorialPagesVwPgr.adapter = pagerAdapter

        }

    }
    lateinit var ui:OnBoardingActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val pages = java.util.ArrayList<TutorialStepFragment>()

        val page1 = TutorialStepFragment()
        pages.add(page1)

        val page2 = TutorialStepFragment()
        pages.add(page2)

        val page3 = TutorialStepFragment()
        pages.add(page3)

        val page4 = TutorialStepFragment()
        pages.add(page4)

        val page5 = TutorialStepFragment()
        pages.add(page5)

        ui = OnBoardingActivityUI(findViewById(R.id.onboardingActivityUI),pages)
    }
}
