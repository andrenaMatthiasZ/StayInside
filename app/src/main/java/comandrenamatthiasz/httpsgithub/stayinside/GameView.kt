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
        paint.color = drawBarrierColor
        paint.strokeWidth = barrierThickness
        paint.style = barrierStyle
        _start.drawLineTo(_stop, canvas, paint)
    }


    private val drawBarrierColor = Color.CYAN
    private val barrierThickness = 10f
    private val barrierStyle = Paint.Style.STROKE

    private fun handleTouch(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val actionType = event.actionMasked
        when (actionType) {
            MotionEvent.ACTION_DOWN -> saveAsStart(PositionVector(x, y))
            MotionEvent.ACTION_UP -> {
                saveAsStop(PositionVector(x, y))
                createNewBarrierIfLineIsLongEnough()
            }
        }
        invalidate()
        return true
    }

    private fun createNewBarrierIfLineIsLongEnough() {

        val distance = _newStart.distance(_newStop)

        if (distance > 10f) {
            useNewPointsForBarrier()
        }
    }

    private var _start = PositionVector()
    private var _stop = PositionVector()
    private var _newStop: PositionVector = PositionVector()
    private var _newStart: PositionVector = PositionVector()

    private fun useNewPointsForBarrier() {

        _start = _newStart
        _stop = _newStop
    }

    private fun saveAsStop(positionVector: PositionVector) {

        _newStop = positionVector
    }

    private fun saveAsStart(positionVector: PositionVector) {

        _newStart = positionVector
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
        return ViewExtension.areOverlapping(_dot,_goodArea)
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