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
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        startTimer()
    }

    private fun startTimer() {
        Timer("schedule", false).schedule(0, 20) {
            moveDot()
        }
    }

    private fun moveDot() {


        var newX = _dot.x + xVelocity
        var newY = _dot.y + yVelocity

        val effectiveWidth = this.width - _dot.width
        val effectiveHeight = this.height - _dot.height

        if (newX < 0) {
            newX = -newX
            xVelocity = -xVelocity
        }
        if (newY < 0) {
            newY = -newY
            yVelocity = -yVelocity
        }
        if (newX > effectiveWidth) {
            newX = 2 * effectiveWidth - newX
            xVelocity = -xVelocity
        }
        if (newY > effectiveHeight) {
            newY = 2 * effectiveHeight - newY
            yVelocity = -yVelocity
        }

        _dot.x = newX
        _dot.y = newY

    }

}