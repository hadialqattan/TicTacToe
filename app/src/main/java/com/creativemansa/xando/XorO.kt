package com.creativemansa.xando

import android.content.Context
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class XorO (private var context:Context){

    private var tapCounter:Int = 1
    fun getTapCounter() = tapCounter

    private val btnArray = arrayOf("btn1","btn2","btn3","btn4","btn5","btn6","btn7","btn8","btn9")
    fun getBtnArray() = btnArray

    var imgWinnerb:Boolean = false

    private var draw = false
    private var winnerXO:String = ""

    fun setButtonImgResource(button:Button,buttonName:String,XorO:String){
        button.isEnabled = false
        if (XorO == "x"){
            button.setBackgroundResource(R.drawable.x_box)
            partOfFindWinner(buttonName,XorO)
        }else{
            button.setBackgroundResource(R.drawable.o_box)
            partOfFindWinner(buttonName,XorO)
        }
        if (tapCounter < 9) {
            tapCounter += 1
        }else{
            draw = true
        }
    }

    private fun partOfFindWinner(buttonName:String,XorO:String){
        for (x in 0..8){
            if (btnArray[x]==buttonName){
                btnArray[x] = XorO
            }
        }
    }

    private fun setAllToDefault(vararg btnS:Button,imgWinner:ImageView,playerX:TextView,playerO:TextView,imgX:ImageView,imgO:ImageView){
        tapCounter = 1 ; draw = false
        for(x in 1..btnArray.size){
            btnArray[x - 1] = "btn$x"
        }
        for(b in btnS){
            b.setBackgroundResource(R.drawable.btn_style)
        }
        imgWinner.setBackgroundResource(R.drawable.empty_image)
        playerX.text = "Choose";playerO.text = "Choose"
        imgX.isEnabled = true;imgO.isEnabled = true
    }

    private var a = 0 ; private var y = 0 ; private var d = 0
    fun findWinner(vararg btn:Button,imgWinner:ImageView,playerX:TextView,playerO:TextView,txtAuto:TextView,txtYou:TextView,txtDraw:TextView,imgX:ImageView,imgO:ImageView){
        if (btnArray[0] == btnArray[1] && btnArray[1] == btnArray[2]) {
            //winner = "123"
            imgWinner.setBackgroundResource(R.drawable.winner123)
            winnerXO = btnArray[0]
            imgWinnerb = false
        } else if (btnArray[3] == btnArray[4] && btnArray[4] == btnArray[5]) {
            //winner = "456"
            imgWinner.setBackgroundResource(R.drawable.winner456)
            winnerXO = btnArray[3]
            imgWinnerb = false
        } else if (btnArray[6] == btnArray[7] && btnArray[7] == btnArray[8]) {
            //winner = "789"
            imgWinner.setBackgroundResource(R.drawable.winner789)
            winnerXO = btnArray[6]
            imgWinnerb = false
        } else if (btnArray[6] == btnArray[3] && btnArray[3] == btnArray[0]) {
            //winner = "741"
            imgWinner.setBackgroundResource(R.drawable.winner741)
            winnerXO = btnArray[6]
            imgWinnerb = false
        } else if (btnArray[7] == btnArray[4] && btnArray[4] == btnArray[1]) {
            //winner = "852"
            imgWinner.setBackgroundResource(R.drawable.winner852)
            winnerXO = btnArray[7]
            imgWinnerb = false
        } else if (btnArray[8] == btnArray[5] && btnArray[5] == btnArray[2]) {
            //winner = "963"
            imgWinner.setBackgroundResource(R.drawable.winner963)
            winnerXO = btnArray[8]
            imgWinnerb = false
        } else if (btnArray[0] == btnArray[4] && btnArray[4] == btnArray[8]) {
            //winner = "159"
            imgWinner.setBackgroundResource(R.drawable.winner159)
            winnerXO = btnArray[0]
            imgWinnerb = false
        } else if (btnArray[6] == btnArray[4] && btnArray[4] == btnArray[2]) {
            //winner = "753"
            imgWinner.setBackgroundResource(R.drawable.winner753)
            winnerXO = btnArray[6]
            imgWinnerb = false
        } else {
            //winner = "NoOne"
            winnerXO = "NoOne"
        }
        var message = "Error"
        if (((winnerXO=="o")&&(playerO.text=="Auto"))||((winnerXO=="x")&&(playerX.text=="Auto"))){
            a++;txtAuto.text = "Auto: $a";message="You lost!"
        }else if ((winnerXO=="x")&&(playerX.text=="You")||((winnerXO=="o")&&(playerO.text=="You"))){
            y++;txtYou.text = "You: $y";message="You Won!"
        }else if (draw){
            d++;txtDraw.text = "Draw: $d";message = "Draw!"; imgWinnerb = false
        }
        if (message!="Error") {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                setAllToDefault(btnS = *btn,imgWinner = imgWinner,playerO = playerO,playerX = playerX, imgO = imgO, imgX = imgX)
                imgWinnerb = true
            },800)
        }
    }

}