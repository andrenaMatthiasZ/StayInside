package layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import java.util.*
import kotlin.concurrent.schedule

class GameView : View {


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        init()
    }

    private var xVelocity = -10f
    private var yVelocity = -20f

    private fun init() {
        setBackgroundColor(Color.RED)
    }


    private lateinit var _dot: ImageView

    fun setDot(dot: ImageView) {
        _dot = dot
        startTimer()
    }

    private fun startTimer() {
        Timer("schedule", false).schedule(0, 20) {
            moveDot()
        }
    }

    private fun moveDot() {

        _dot!!

        var newX = _dot.x + xVelocity
        var newY = _dot.y + yVelocity

        val effectiveWidth = width - _dot.width
        val effectiveHeight = height - _dot.height

        if (newX < x) {
            newX = reflect(x,newX)
            xVelocity = -xVelocity
        }
        if (newY < y) {
            newY = reflect(y,newY)
            yVelocity = -yVelocity
        }
        if (newX > x+effectiveWidth) {
            newX = reflect(x+effectiveWidth,newX)
            xVelocity = -xVelocity
        }
        if (newY > y + effectiveHeight) {
            newY = reflect(y+effectiveHeight,newY)
            yVelocity = -yVelocity
        }

        _dot.x = newX
        _dot.y = newY

    }

    private fun reflect(barrier: Float,position: Float): Float {
       return 2*barrier - position
    }

}