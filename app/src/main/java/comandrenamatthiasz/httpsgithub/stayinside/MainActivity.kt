package comandrenamatthiasz.httpsgithub.stayinside

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    private var screenWidth = -1
    private var screenHeight = -1

    val horizontalMode = "horizontal"
    val verticalMode = "vertical"
    private var mode = horizontalMode

    private var xVelocity = -10f
    private var yVelocity = -20f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        setTextShown()
        val timer = Timer("schedule", false).schedule(0,20){
            moveDot()
        }



        horizontalButton.setOnClickListener {
            mode = horizontalMode
            setTextShown()
        }
        verticalButton.setOnClickListener {
            mode = verticalMode
            setTextShown()
        }
    }

    private fun moveDot() {
        var newX = dot.x + xVelocity
        var newY = dot.y + yVelocity
        val effectiveWidth = screenWidth - dot.width
        val effectiveHeight = screenHeight - dot.height
        if(newX<0){
            newX = -newX
            xVelocity = -xVelocity
        }
        if(newY<0){
            newY = -newY
            yVelocity = -yVelocity
        }
        if(newX >effectiveWidth)
        {
            newX = 2*effectiveWidth-newX
            xVelocity = -xVelocity
        }
        if(newY > effectiveHeight)
        {
            newY = 2*effectiveHeight-newY
            yVelocity = -yVelocity
        }

        dot.x = newX
        dot.y = newY
    }

    private fun setTextShown(){
        textView.text=mode
    }
}
