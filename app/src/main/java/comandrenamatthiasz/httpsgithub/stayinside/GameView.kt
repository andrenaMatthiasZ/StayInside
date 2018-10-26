package comandrenamatthiasz.httpsgithub.stayinside


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
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

        setOnTouchListener { _, event ->
            handleTouch(event)

        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return
        drawBarrier(canvas)
    }

    private fun drawBarrier(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.BLACK
        paint.strokeWidth = 10f
        paint.style = Paint.Style.STROKE
        canvas.drawLine(_xStart, _yStart, _xStop, _yStop, paint)
    }


    private fun handleTouch(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val actionType = event.actionMasked
        when (actionType) {
            MotionEvent.ACTION_DOWN -> saveAsStart(x, y)
            MotionEvent.ACTION_UP -> {
                saveAsStop(x, y)
                createNewBarrierIfLineIsLongEnough()
            }
        }
        invalidate()
        return true
    }

    private fun createNewBarrierIfLineIsLongEnough() {

        val differenceSquare =
            (_newXStart - _newXStop) * (_newXStart - _newXStop) + (_newYStart - _newYStop) * (_newYStart - _newYStop)

        if (differenceSquare > 10f * 10f) {
            useNewPointsForBarrier()
        }
    }

    private fun useNewPointsForBarrier() {
        _xStart = _newXStart
        _yStart = _newYStart
        _xStop = _newXStop
        _yStop = _newYStop
    }

    private var _xStart: Float = 0f
    private var _yStart: Float = 0f
    private var _xStop: Float = 3f
    private var _yStop: Float = 3f

    private var _newXStop: Float = 0f

    private var _newYStop: Float = 0f

    private fun saveAsStop(x: Float, y: Float) {
        _newXStop = x
        _newYStop = y
    }

    private var _newXStart: Float = 0f

    private var _newYStart: Float = 0f

    private fun saveAsStart(x: Float, y: Float) {
        _newXStart = x
        _newYStart = y
    }


    private lateinit var _dot: ImageView

    fun setDot(dot: ImageView) {
        _dot = dot

    }


    private var _listener: OnCertainAreaReachedListener? = null

    fun setOnCertainAreaReachedListener(listener: OnCertainAreaReachedListener) {
        _listener = listener
    }

    private lateinit var _goodArea: ImageView

    fun setGoodArea(goodArea: ImageView) {
        _goodArea = goodArea
        _goodArea.setColorFilter(Color.GREEN)
    }

    fun startMovement() {
        Timer("schedule", false).schedule(0, 20) {
            doStepForDot()
        }
    }

    private fun doStepForDot() {

        moveToNextPosition()
        checkForGoodArea()
        colorGoodAreaAccordingToDotState()
    }

    private fun colorGoodAreaAccordingToDotState() {
        when (_state) {
            DotState.CannotCollectPoint -> _goodArea.setColorFilter(Color.GRAY)
            DotState.CanCollectPoint -> _goodArea.setColorFilter(Color.YELLOW)
        }
    }

    private var _state = DotState.CanCollectPoint

    private fun checkForGoodArea() {
        val dotIsInsideGoodArea = dotIsInGoodArea()
        val canCollectPoint = canCollectPoint()

        if (dotIsInsideGoodArea && canCollectPoint) {
            _listener?.reached(AreaType.Point)
            setDotState(DotState.CannotCollectPoint)
        }


    }

    private fun dotIsInGoodArea(): Boolean {
        val xIsInsideGoodArea = _dot.x <= (_goodArea.x + _goodArea.width) && (_dot.x + _dot.width) >= _goodArea.x
        val yIsInsideGoodArea = _dot.y <= (_goodArea.y + _goodArea.height) && (_dot.y + _dot.height) >= _goodArea.y

        return xIsInsideGoodArea && yIsInsideGoodArea
    }

    private fun canCollectPoint() = _state == DotState.CanCollectPoint

    private fun moveToNextPosition() {
        var newX = _dot.x + xVelocity
        var newY = _dot.y + yVelocity

        val effectiveWidth = width - _dot.width
        val effectiveHeight = height - _dot.height

        if (newX < x) {
            newX = reflect(x, newX)
            xVelocity = -xVelocity
            onOuterAreaReached()
        }
        if (newY < y) {
            newY = reflect(y, newY)
            yVelocity = -yVelocity
            onOuterAreaReached()
        }
        if (newX > x + effectiveWidth) {
            newX = reflect(x + effectiveWidth, newX)
            xVelocity = -xVelocity
            onOuterAreaReached()
        }
        if (newY > y + effectiveHeight) {
            newY = reflect(y + effectiveHeight, newY)
            yVelocity = -yVelocity
            onOuterAreaReached()
        }

        _dot.x = newX
        _dot.y = newY
    }

    private fun onOuterAreaReached() {
        setDotState(DotState.CanCollectPoint)
        _listener?.reached(AreaType.Outer)
    }

    private fun setDotState(state: DotState) {
        _state = state
    }

    private fun reflect(barrier: Float, position: Float): Float {
        return 2 * barrier - position
    }

}