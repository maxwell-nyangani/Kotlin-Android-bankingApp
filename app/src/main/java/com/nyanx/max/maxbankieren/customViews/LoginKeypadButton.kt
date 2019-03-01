package com.nyanx.max.maxbankieren.customViews

import android.content.Context
import android.content.res.TypedArray
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.nyanx.max.maxbankieren.R
import kotlinx.android.synthetic.main.ui_login_keypad_button.view.*

class LoginKeypadButton: LinearLayout {
    interface DigitKeyEventHandlers{
        fun onButtonClicked(v: View,value:Int,alpha:String)
    }

    private lateinit var mContext: Context
    private var isTouched: Boolean = false
    private var digit:Int = -1
    private lateinit var characters:String

    private lateinit var digitTxtVw:TextView
    private lateinit var alphaTxtVw:TextView
    private lateinit var containerCnstLyt:ConstraintLayout
    private lateinit var callbacks:DigitKeyEventHandlers

    constructor(context: Context, digit: Int) : super(context){
        initialize(context,null)
        this.digit = digit
    }

    constructor(context: Context, attrs: AttributeSet):    super(context, attrs){
        initialize(context,attrs)
    }

    constructor(context: Context, attrs: AttributeSet?,    defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context,attrs)
    }

    private  fun initialize( context:Context,  attrs:AttributeSet?){

        this.mContext = context
        LayoutInflater.from(context).inflate(R.layout.ui_login_keypad_button,this,true)
        this.containerCnstLyt = loginKeyPadBtnContainerCnstLyt
        this.digitTxtVw = loginKeyPadBtnDigitTxtVw
        this.alphaTxtVw = loginKeyPadBtnAlphaTxtVw

        loginKeyPadBtnContainerCnstLyt.setOnClickListener{v ->

            if(callbacks!=null){
                callbacks.onButtonClicked(this,digit,characters)
            }
        }
        orientation= VERTICAL

        if(attrs!=null){
            var a: TypedArray = mContext.theme.obtainStyledAttributes(attrs,R.styleable.LoginKeyPadButton,0,0)
            try {
                /*digit value*/
                if (a.getString(R.styleable.LoginKeyPadButton_digitValue) != null) {
                    digitTxtVw.text =a.getString(R.styleable.LoginKeyPadButton_digitValue)
                    digit = a.getString(R.styleable.LoginKeyPadButton_digitValue).toInt()
                }

                /*alpha value*/
                if (a.getString(R.styleable.LoginKeyPadButton_alphaValue) != null) {
                    alphaTxtVw.text =a.getString(R.styleable.LoginKeyPadButton_alphaValue)
                    characters = a.getString(R.styleable.LoginKeyPadButton_alphaValue)
                }

            }finally {
                a.recycle()
            }
        }

    }

    public fun setEventHandlers(eventHandlers:DigitKeyEventHandlers){
        callbacks =eventHandlers
    }
}