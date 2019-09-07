package com.creativemansa.xando

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        active()
    }

    private var playerXorO = "none" ; private var autoXorO = "none"
    private val setXO = XorO(this)
    private fun setXorO(){
        val autoX = (playerX.text=="Auto") ; val autoO = (playerO.text=="Auto")
        if (autoX){
            this.autoXorO = "x" ; this.playerXorO = "o"
        }else if (autoO){
            this.autoXorO = "o" ; this.playerXorO = "x"
        }
    }

    private fun active(){
        fun onClick(btnName:String){ btnsEnable(false); btn(btnName,playerXorO); autoWithSleep(); Handler().postDelayed({if (setXO.imgWinnerb){btnsEnable(true)}},ranSleep) }
        imgX.setOnClickListener {
            imgX.isEnabled = false; imgO.isEnabled = false; setXO.imgWinnerb = true ; btnsEnable(true)
            playerX.text = "You" ;playerO.text = "Auto";setXorO()
        }
        imgO.setOnClickListener {
            imgX.isEnabled = false; imgO.isEnabled = false; setXO.imgWinnerb = true ; btnsEnable(true)
            playerX.text = "Auto" ;playerO.text = "You";setXorO()
            autoPlaying()
        }
        try {
            btn9.setOnClickListener { onClick("btn9") }
            btn8.setOnClickListener { onClick("btn8") }
            btn7.setOnClickListener { onClick("btn7") }

            btn6.setOnClickListener { onClick("btn6") }
            btn5.setOnClickListener { onClick("btn5") }
            btn4.setOnClickListener { onClick("btn4") }

            btn3.setOnClickListener { onClick("btn3") }
            btn2.setOnClickListener { onClick("btn2") }
            btn1.setOnClickListener { onClick("btn1") }
        }catch (ex:Exception){Toast.makeText(this,ex.message,Toast.LENGTH_LONG).show()}
    }

    private val btn = {buttonName:String,XorO:String ->
        if(setXO.imgWinnerb) {
            setXO.setButtonImgResource(btnNameToButton(buttonName), buttonName, XorO)
            findWinner()
        }
    }

    private val sleepRange = 250..400
    private val ranSleep = sleepRange.random().toLong()
    private fun autoWithSleep(){
        if (setXO.imgWinnerb) {
            Handler().postDelayed({ autoPlaying() }, ranSleep)
        }
    }

    private fun btnsEnable(isEnable:Boolean){
        val btnArray = ArrayList<Button>()
        val xo = arrayOf("x","o")
        for (actbtns in setXO.getBtnArray()){
                if (actbtns !in xo ){
                    btnArray.add(btnNameToButton(actbtns))
                }
        }
        for (btn in btnArray){
            btn.isEnabled = isEnable
        }
    }
    private fun findWinner(){
        setXO.findWinner(
            btn = *arrayOf(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9),
            imgWinner=imgWinner,playerX = playerX,playerO = playerO,
            txtAuto = txtAuto,txtDraw = txtDraw,txtYou = txtYou, imgO = imgO, imgX = imgX)
    }
    private fun btnNameToButton(buttonName:String):Button{
        val button:Button = when(buttonName){
            "btn9" -> btn9
            "btn8" -> btn8
            "btn7" -> btn7
            "btn6" -> btn6
            "btn5" -> btn5
            "btn4" -> btn4
            "btn3" -> btn3
            "btn2" -> btn2
            else -> btn1
        }; return button
    }


    private fun autoPlaying(){

        var done = true

        val xo = arrayOf("x","o")
        val corner = arrayOf(0,8,2,6)

        val win123 = arrayOf(0,1,2)
        val win456 = arrayOf(3,4,5)
        val win789 = arrayOf(6,7,8)

        val win147 = arrayOf(0,3,6)
        val win258 = arrayOf(1,4,7)
        val win369 = arrayOf(2,5,8)

        val win159 = arrayOf(0,4,8)
        val win357 = arrayOf(2,4,6)

        val win = arrayOf(win123,win456,win789,win147,win258,win369,win159,win357)

        try {
            if (setXO.getTapCounter() == 1) {
                val not5 = arrayOf(1, 2, 3, 4, 6, 7, 8, 9)
                btn("btn${not5.random()}", autoXorO)
            } else if (setXO.getTapCounter() == 2) {
                var isDone = true
                if (isDone&&setXO.getBtnArray()[4]=="btn5"){
                    btn("btn5",autoXorO);isDone=false
                }
                if (isDone) {
                    btn("btn${corner.random() + 1}", autoXorO)
                }
            } else if (setXO.getTapCounter() < 11) {
                val autoO = (playerO.text == "Auto")
                val autoX = (playerX.text == "Auto")
                if (done) {
                    for (x in win) {
                        if ((setXO.getBtnArray()[x[0]] == setXO.getBtnArray()[x[1]]) && (setXO.getBtnArray()[x[2]] !in xo) && ((setXO.getBtnArray()[x[0]] == "o" && autoO) || (setXO.getBtnArray()[x[0]] == "x" && autoX))) {
                            btn("btn${(x[2]) + 1}", autoXorO); done = false; break
                        } else if ((setXO.getBtnArray()[x[0]] == setXO.getBtnArray()[x[2]]) && (setXO.getBtnArray()[x[1]] !in xo) && ((setXO.getBtnArray()[x[0]] == "o" && autoO) || (setXO.getBtnArray()[x[0]] == "x" && autoX))) {
                            btn("btn${(x[1]) + 1}", autoXorO); done = false; break
                        } else if ((setXO.getBtnArray()[x[2]] == setXO.getBtnArray()[x[1]]) && (setXO.getBtnArray()[x[0]] !in xo) && ((setXO.getBtnArray()[x[2]] == "o" && autoO) || (setXO.getBtnArray()[x[2]] == "x" && autoX))) {
                            btn("btn${(x[0]) + 1}", autoXorO); done = false; break
                        }
                    }
                }
                if (done) {
                    for (x in win) {
                        if ((setXO.getBtnArray()[x[0]] == setXO.getBtnArray()[x[1]]) && (setXO.getBtnArray()[x[2]] !in xo)) {
                            btn("btn${(x[2]) + 1}", autoXorO); done = false; break
                        } else if ((setXO.getBtnArray()[x[0]] == setXO.getBtnArray()[x[2]]) && (setXO.getBtnArray()[x[1]] !in xo)) {
                            btn("btn${(x[1]) + 1}", autoXorO); done = false; break
                        } else if ((setXO.getBtnArray()[x[2]] == setXO.getBtnArray()[x[1]]) && (setXO.getBtnArray()[x[0]] !in xo)) {
                            btn("btn${(x[0]) + 1}", autoXorO); done = false; break
                        }
                    }
                }
                if (done && (setXO.getTapCounter() == 4) && autoO) {
                    val usedBTN = arrayOf(3,1,5,7  ,0,8,6,2)
                    val availBTN = ArrayList<Int>()
                    for (inUse in 0..3){
                        if (inUse != 3){
                            if (setXO.getBtnArray()[usedBTN[inUse]]==setXO.getBtnArray()[usedBTN[inUse+1]]) {
                                for (toUse in 4..7) {
                                    if (toUse != (inUse + 5)&&(inUse+5)!=7) {
                                        availBTN.add(usedBTN[toUse])
                                    }else if (inUse+5==7){
                                        availBTN.add(6);availBTN.add(2);availBTN.add(8)
                                    }
                                }
                                btn("btn${availBTN.random()+1}",autoXorO);done = false;break
                            }
                        }else if (inUse == 3){
                            if (setXO.getBtnArray()[usedBTN[0]]==setXO.getBtnArray()[usedBTN[3]]) {
                                val avalBTNs = arrayOf(7, 1, 9)
                                btn("btn${avalBTNs.random()}", autoXorO);done=false;break
                            }
                        }
                    }
                    availBTN.clear()
                }
                if (done && (setXO.getTapCounter() == 5) && autoX && (setXO.getBtnArray()[4] == "btn5")) {
                    btn("btn5", autoXorO); done = false
                }
                if (done && setXO.getTapCounter() == 4 && ((setXO.getBtnArray()[0] == setXO.getBtnArray()[8]) || (setXO.getBtnArray()[2] == setXO.getBtnArray()[6]))) {
                    val plusBTNs = arrayOf(2, 4, 6, 8)
                    btn("btn${plusBTNs.random()}", autoXorO); done = false
                }
                if (done) {
                    val availableCorner = ArrayList<Int>()
                    for (x in corner) {
                        if (setXO.getBtnArray()[x] !in xo) {
                            availableCorner.add(x + 1)
                        }else{}
                    }
                    if (availableCorner.size!=0) {
                        btn("btn${availableCorner.random()}", autoXorO); done = false
                        availableCorner.clear()
                    }
                }
                if (done) {
                    val ranArray = ArrayList<Int>()
                    for (r in 0..8) {
                        if (setXO.getBtnArray()[r] !in xo) {
                            ranArray.add(r + 1)
                        }
                    }
                    if (ranArray.size!=0) {
                        btn("btn${ranArray.random()}", autoXorO); done = false
                        ranArray.clear()
                    }
                }
                done = true
            }
        }catch (noEX:Exception){Toast.makeText(this,noEX.message,Toast.LENGTH_LONG).show()}
    }

}
