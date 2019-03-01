package com.nyanx.max.maxbankieren

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.nyanx.max.maxbankieren.customViews.LoginKeypadButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private  var isColdLaunch:Boolean = true
    private val splashScreenRequestCode: Int = 1
    private val homeRequestCode: Int = 2
    private val generalInfoRequestCode: Int = 3
    private val scanQRCodeRequestCode: Int = 4
    private lateinit var ui: MainUI
    private var entredDigitCount: Int = 0
    private  var pincode:IntArray = IntArray(5){
        i ->  -1
    }

    private lateinit var localStorage: SharedPreferences

    inner class MainUI : LoginKeypadButton.DigitKeyEventHandlers {

        override fun onButtonClicked(v: View, value: Int, alpha: String) {
            //Toast.makeText(applicationContext,"Key Pressed => "+value, Toast.LENGTH_LONG).show()
            enterPinEntry(value)
        }

        init {
            //set triggers for the keypad buttons

            generalInfoImgVw.setOnClickListener{v ->
                this.goToGeneralInfoActivity()
            }

            scanQRCodeImgVw.setOnClickListener{v ->
                this.goToScanQRCodeActivity()
            }
            pinButton0.setEventHandlers(this)
            pinButton1.setEventHandlers(this)
            pinButton2.setEventHandlers(this)
            pinButton3.setEventHandlers(this)
            pinButton4.setEventHandlers(this)
            pinButton5.setEventHandlers(this)
            pinButton6.setEventHandlers(this)
            pinButton7.setEventHandlers(this)
            pinButton8.setEventHandlers(this)
            pinButton9.setEventHandlers(this)

            pinDigit1Musk.focused()

            fingerPrintLoginImgVw.setOnClickListener{v ->
                showPlaceFingerOnScannerMessage()
            }

            loginKeypadBackBtnImgVw.setOnClickListener{v ->
                undoPinEntry()
            }
        }

        fun goToHomeActivity(){
            val homeIntent = Intent(applicationContext, HomeActivity::class.java)
            homeIntent.putExtra("KEY","value")
            startActivityForResult(homeIntent,homeRequestCode)
        }

        fun goToGeneralInfoActivity(){
            val generalInfoIntent = Intent(applicationContext, GeneralInfoActivity::class.java)
            generalInfoIntent.putExtra("KEY","value")
            startActivityForResult(generalInfoIntent,generalInfoRequestCode)
        }

        fun goToScanQRCodeActivity(){
            val scanQRCodeIntent = Intent(applicationContext, QRCodeScannerActivity::class.java)
            scanQRCodeIntent.putExtra("KEY","value")
            startActivityForResult(scanQRCodeIntent,scanQRCodeRequestCode)
        }

        fun showPlaceFingerOnScannerMessage(){
            val builder = AlertDialog.Builder(applicationContext)
            //builder.setTitle(getString(R.string.enter_your_complaint));

            // Set up the reportAbuseTextEdtTxt
            val areYouSureUI = View.inflate(applicationContext, R.layout.ui_fingerprint_prompt, null)
            builder.setView(areYouSureUI)

            // Set up the buttons
            builder.setPositiveButton(
                getString(R.string.yes)
            ) { dialog, which ->
                //intentionally left blank
            }
            builder.setNegativeButton(
                getString(R.string.cancel)
            ) { dialog, which -> dialog.cancel() }
            /*var warningDialog = builder.create()
            warningDialog.setCanceledOnTouchOutside(false)
            warningDialog.show()
            //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
            warningDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                View.OnClickListener {
                    warningDialog.dismiss()
                })*/

            builder.show()
        }

        fun undoPinEntry(){
            when(entredDigitCount){
                5->{
                    entredDigitCount--
                    pinDigit5Musk.focused()
                }
                4->{
                    entredDigitCount--
                    pinDigit5Musk.idle()
                    pinDigit4Musk.focused()
                }
                3->{
                    entredDigitCount--
                    pinDigit4Musk.idle()
                    pinDigit3Musk.focused()
                }
                2->{
                    entredDigitCount--
                    pinDigit3Musk.idle()
                    pinDigit2Musk.focused()
                }
                1->{
                    entredDigitCount--
                    pinDigit2Musk.idle()
                    pinDigit1Musk.focused()
                }
            }
        }

        fun enterPinEntry(digit:Int){

            when(entredDigitCount){
                0->{
                    pincode[0]=digit
                    pinDigit1Musk.filled()
                    pinDigit2Musk.focused()
                    entredDigitCount++
                }
                1->{
                    pincode[1]=digit
                    pinDigit2Musk.filled()
                    pinDigit3Musk.focused()
                    entredDigitCount++
                }
                2->{
                    pincode[2]=digit
                    pinDigit3Musk.filled()
                    pinDigit4Musk.focused()
                    entredDigitCount++
                }
                3->{
                    pincode[3]=digit
                    pinDigit4Musk.filled()
                    pinDigit5Musk.focused()
                    entredDigitCount++
                }
                4->{
                    pincode[4]=digit
                    pinDigit5Musk.filled()
                    entredDigitCount++
                    logUserIn()
                }
            }
        }

        fun logUserIn(){

            if((pincode.map { i -> i.toString() }.reduce { acc, s ->acc+s}).equals("80808")){
                resetUI()
                goToHomeActivity()
            }else{
                Toast.makeText(applicationContext,"---wrong code---",Toast.LENGTH_LONG).show()
            }
        }

        fun resetUI(){
            entredDigitCount = 0
            pincode = IntArray(5){
                    i ->  -1
            }
            pinDigit1Musk.focused()
            pinDigit2Musk.idle()
            pinDigit3Musk.idle()
            pinDigit4Musk.idle()
            pinDigit5Musk.idle()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        localStorage = applicationContext.getSharedPreferences(
            getString(R.string.local_storage_key),
            Context.MODE_PRIVATE
        )

        ui = MainUI()

        if(isColdLaunch){//TODO: change this variable based on whether is is restart or fist launch
            val splashIntent = Intent(this, SplashScreenActivity::class.java)
            splashIntent.putExtra("KEY","value")
            startActivityForResult(splashIntent,splashScreenRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1->{ //SplashScreenActivity  finished
                if (/*localStorage.getString("isFirstUser",null)==null*/ true) {
                    //mark the user as not first use user anymore
                    localStorage.edit().putString("isFirstUser", "no").apply()

                    //launch the tutorial if the user is first time
                    val tutorialIntent = Intent(applicationContext, OnBoardingActivity::class.java)
                    startActivity(tutorialIntent)
                }
            }
            2->{//HomeActivity finished probably a logout happened

            }
            3->{//General info activity finished

            }
            4->{//QRCodeScannerActivity finished

            }
        }
    }
}
