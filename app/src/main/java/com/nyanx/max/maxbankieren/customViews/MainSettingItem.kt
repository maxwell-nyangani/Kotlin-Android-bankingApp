package com.nyanx.max.maxbankieren.customViews

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.nyanx.max.maxbankieren.R
import kotlinx.android.synthetic.main.ui_main_setting_item.view.*

class MainSettingItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private  var mContext:Context = context
    private  var iconImgVw: ImageView
    private  var titleTxtVwt:TextView
    private  var descriptionTxtVwt:TextView
    private  var containerCnsLyt: ConstraintLayout

    init {

        LayoutInflater.from(context).inflate(R.layout.ui_main_setting_item,this,true)

        iconImgVw = mainSettingItemIconImgVw
        titleTxtVwt = mainSettingItemTitleTxtVw
        descriptionTxtVwt = mainSettingItemDescTxtVw
        containerCnsLyt = mainSettingItemContainerCnstLyt

        if (attrs != null) {
            //use the xml attributes to tune appearances
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.MainSettingItem,
                0,
                0
            )

            try {

                /*title*/
                if (a.getString(R.styleable.MainSettingItem_settingTitle) != null) {
                    titleTxtVwt.text = a.getString(R.styleable.MainSettingItem_settingTitle)
                }
                  /*description*/
                if (a.getString(R.styleable.MainSettingItem_settingDescription) != null) {
                    descriptionTxtVwt.text = a.getString(R.styleable.MainSettingItem_settingDescription)
                }

                /*icon*/
                if (a.getDrawable(R.styleable.MainSettingItem_settingIcon) != null) {
                    iconImgVw.setImageDrawable(a.getDrawable(R.styleable.MainSettingItem_settingIcon))
                    iconImgVw.drawable.setTint(mContext.getColor(R.color.colorPrimary))
                }

            } finally {
                a.recycle()
            }
        }
    }


}