package com.nyanx.max.maxbankieren.customViews

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.nyanx.max.maxbankieren.R
import kotlinx.android.synthetic.main.badged_button.view.*

class BadgedButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var mContext: Context = context
    private val badgedButtonContext = this
    private var bgSelected = R.drawable.ripple_selected
    private var bgUnselected = R.drawable.ripple
    private var textColorSelected: Int = 0
    private var textColorUnselected: Int = 0
    private var badgeBackground = R.drawable.badge_background
    private var badgeValueColor: Int = 0
    private var isBadgeVisible = true
    private var isTextVisible = true
    private var isIconVisible = true
    private var iconSelected: Drawable? = null
    private var iconUnselected: Drawable? = null
    private var isBtnSelected = false

    interface BadgedButtonEventHandlers {
        fun onBadgedButtonClicked(v: View)
        fun onBadgedButtonLongClicked(v: View)
    }

    private lateinit var callbacks: BadgedButtonEventHandlers

    init {
        /*initialize the child views*/
        LayoutInflater.from(context).inflate(R.layout.badged_button, this, true)
        if (attrs != null) {
            //use the xml attributes to tune appearances
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.BadgedButton,
                0,
                0
            )

            try {
                if (a.getDrawable(R.styleable.BadgedButton_bgUnselected) != null) {
                    badgedButtonContainer.background = a.getDrawable(R.styleable.BadgedButton_bgUnselected)
                } else {
                    badgedButtonContainer.setBackgroundResource(R.drawable.ripple)
                }
                /*get button bgUnselected*/
                this.bgUnselected = a.getColor(R.styleable.BadgedButton_bgUnselected, R.drawable.ripple)
                /*get button bgSelected*/
                this.bgSelected = a.getColor(R.styleable.BadgedButton_bgSelected, R.drawable.ripple_selected)

                /*textColorUnselected*/
                badgedButtonTextTxtVw.setTextColor(a.getColor(R.styleable.BadgedButton_textColorUnselected, Color.BLACK))
                textColorUnselected = a.getColor(R.styleable.BadgedButton_textColorUnselected, Color.BLACK)
                textColorSelected = textColorUnselected

                /*textColorSelected*/
                textColorSelected = a.getColor(R.styleable.BadgedButton_textColorSelected, Color.BLACK)

                /*text*/
                if (a.getString(R.styleable.BadgedButton_text) != null) {
                    badgedButtonTextTxtVw.text = a.getString(R.styleable.BadgedButton_text)
                }

                /*textVisibility*/
                if (a.getBoolean(R.styleable.BadgedButton_textVisibility, true)) {
                    badgedButtonTextTxtVw.visibility = View.VISIBLE
                    isTextVisible = true
                } else {
                    badgedButtonTextTxtVw.visibility = View.GONE
                    isTextVisible = false
                }

                /*badgeValueColor*/
                badgedButtonBadgeTxtVw.setTextColor(a.getColor(R.styleable.BadgedButton_badgeValueColor, Color.WHITE))
                badgeValueColor = a.getColor(R.styleable.BadgedButton_badgeValueColor, Color.WHITE)

                /*badgeValue*/
                if (a.getString(R.styleable.BadgedButton_badgeValue) != null) {
                    badgedButtonBadgeTxtVw.text = a.getString(R.styleable.BadgedButton_badgeValue)
                }

                /*badgeVisibility*/
                if (a.getBoolean(R.styleable.BadgedButton_badgeVisibility, true)) {
                    badgedButtonBadgeTxtVw.visibility = View.VISIBLE
                    isBadgeVisible = true
                } else {
                    badgedButtonBadgeTxtVw.visibility = View.GONE
                    isBadgeVisible = false
                }

                /*the drawable for the image view unselected state*/
                if (a.getDrawable(R.styleable.BadgedButton_buttonIconUnselected) != null) {
                    badgedButtonImageImgVw.setImageDrawable(a.getDrawable(R.styleable.BadgedButton_buttonIconUnselected))
                    badgedButtonImageImgVw.drawable.setTint(mContext.getColor(R.color.colorGrey))
                    iconUnselected = a.getDrawable(R.styleable.BadgedButton_buttonIconUnselected)
                    iconSelected = iconUnselected
                } else {
                    iconUnselected = context.getDrawable(R.mipmap.ic_launcher_round)
                    iconSelected = iconUnselected
                }
                /*the drawable for the image view selected state*/
                if (a.getDrawable(R.styleable.BadgedButton_buttonIconSelected) != null) {
                    iconSelected = a.getDrawable(R.styleable.BadgedButton_buttonIconSelected)
                }

            } finally {
                a.recycle()
            }
        }

        badgedButtonContainer.setOnClickListener(OnClickListener {
            if (callbacks != null) {
                callbacks.onBadgedButtonClicked(badgedButtonContext)
            }
        })

        badgedButtonContainer.setOnLongClickListener(OnLongClickListener {
            if (callbacks != null) {
                callbacks.onBadgedButtonLongClicked(badgedButtonContext)
            }
            true
        })

    }

    /**
     * Changes the button into selected state and may change the bg and icon image if the attributes
     * are set.
     * @param newState a boolean indicating the new selection state of the button
     */
    fun setButtonSelected(newState: Boolean) {
        isSelected = newState
        if (newState) {
            badgedButtonContainer.setBackgroundColor(context.getColor(R.color.colorPrimaryDark))
            badgedButtonImageImgVw.setImageDrawable(iconSelected)
            badgedButtonImageImgVw.drawable.setTint(mContext.getColor(R.color.colorWhite))
            badgedButtonTextTxtVw.setTextColor(textColorSelected)
            invalidate()
        } else {
            badgedButtonContainer.setBackgroundColor(context.getColor(R.color.colorLightestGrey))
            badgedButtonImageImgVw.setImageDrawable(iconUnselected)
            badgedButtonImageImgVw.drawable.setTint(mContext.getColor(R.color.colorGrey))
            badgedButtonTextTxtVw.setTextColor(textColorUnselected)
            invalidate()
        }
    }

    /*getters and setters*/

    fun getBadgeBackground(): Int {
        return badgeBackground
    }

    fun setBadgeBackground(badgeBackground: Int) {
        this.badgeBackground = badgeBackground
        badgedButtonTextTxtVw.setBackgroundResource(badgeBackground)
        invalidate()
    }

    fun isIconVisible(): Boolean {
        return isIconVisible
    }

    fun setIconVisible(iconVisible: Boolean) {
        isIconVisible = iconVisible
        if (iconVisible)
            badgedButtonImageImgVw.visibility = View.VISIBLE
        else
            badgedButtonImageImgVw.visibility = View.GONE
        invalidate()
    }

    fun isBadgeVisible(): Boolean {
        return isBadgeVisible
    }

    fun setBadgeVisible(badgeVisible: Boolean) {
        isBadgeVisible = badgeVisible
        if (badgeVisible)
            badgedButtonTextTxtVw.visibility = View.VISIBLE
        else
            badgedButtonTextTxtVw.visibility = View.GONE
        invalidate()

    }

    fun isSelectedState(): Boolean {
        return this.isSelected
    }

    fun setSelectedState(selected: Boolean) {
        this.isSelected = selected
        setButtonSelected(selected)
    }

    fun isTextVisible(): Boolean {
        return isTextVisible
    }

    fun setTextVisible(textVisible: Boolean) {
        isTextVisible = textVisible
        if (textVisible)
            badgedButtonTextTxtVw.visibility = View.VISIBLE
        else
            badgedButtonTextTxtVw.visibility = View.GONE
        invalidate()
    }

    fun setBgUnselected(bgUnselected: Int) {
        this.bgUnselected = bgUnselected
        badgedButtonContainer.setBackgroundResource(bgUnselected)
        invalidate()
    }

    fun setBgSelected(bgSelected: Int) {
        this.bgSelected = bgSelected
        badgedButtonContainer.setBackgroundResource(bgSelected)
        invalidate()
    }

    fun getTextColorUnselected(): Int {
        return textColorUnselected
    }

    fun setTextColorUnselected(textColorUnselected: Int) {
        this.textColorUnselected = textColorUnselected
        badgedButtonTextTxtVw.setTextColor(textColorUnselected)
        invalidate()
    }

    fun getTextColorSelected(): Int {
        return textColorSelected
    }

    fun setTextColorSelected(textColorSelected: Int) {
        this.textColorSelected = textColorSelected
        badgedButtonTextTxtVw.setTextColor(textColorSelected)
        invalidate()
    }

    fun setBadgeValueColor(badgeValueColor: Int) {
        this.badgeValueColor = badgeValueColor
        badgedButtonTextTxtVw.setTextColor(badgeValueColor)
        invalidate()
    }

    fun getBadgeValueColor(): Int {
        return badgeValueColor
    }

    fun setEventHandlers(callbacks: BadgedButtonEventHandlers) {
        this.callbacks = callbacks
    }
    /*getters and setters*/


}