package com.nyanx.max.maxbankieren.customViews

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.nyanx.max.maxbankieren.R
import kotlinx.android.synthetic.main.ui_musked_digit.view.*

class MuskedDigit @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    private  var mContext:Context
    private  var dotImgVw:ImageView
    private  var sliderCnstLyt:ConstraintLayout
    private  var containerCnsLyt:ConstraintLayout

    init {

        mContext = context
        LayoutInflater.from(context).inflate(R.layout.ui_musked_digit,this,true)

        dotImgVw = muskedDigitDotImgVw
        sliderCnstLyt = muskedDigitSliderConstLyt
        containerCnsLyt = muskedDigitContainerConstLyt

        containerCnsLyt.setOnLongClickListener{v ->
            filled()
        }

        containerCnsLyt.setOnClickListener{v ->
            focused()
        }



    }

    fun focused(){
        dotImgVw.animate().scaleY(1f).scaleX(1f)
        sliderCnstLyt.animate().scaleY(1f)
        sliderCnstLyt.visibility = View.VISIBLE
    }
    fun filled(): Boolean {
        dotImgVw.animate().scaleY(2f).scaleX(2f)
        sliderCnstLyt.visibility = View.VISIBLE
        sliderCnstLyt.animate().setDuration(300).scaleY(100f)
        return true
    }
    fun idle(){
        dotImgVw.animate().scaleY(1f).scaleX(1f)
        sliderCnstLyt.visibility = View.GONE
        sliderCnstLyt.animate().setDuration(300).scaleY(1f)
    }
}