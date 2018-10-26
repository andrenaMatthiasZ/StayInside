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
        setBackgroundColor(Color.LTGRAY)
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
        _barrier?.draw(canvas, paint)
    }


    private val drawBarrierColor = Color.CYAN
    private val barrierThickness = 10f
    private val barrierStyle = Paint.Style.STROKE

    private fun handleTouch(event: MotionEvent): Boolean {

        val position = PositionVector(event.x, event.y)
        val actionType = event.actionMasked
        when (actionType) {
            MotionEvent.ACTION_DOWN -> saveAsStart(position)
            MotionEvent.ACTION_UP -> {
                saveAsStop(position)
                createNewBarrierIfLineIsLongEnough()
            }
        }
        invalidate()
        return true
    }

    private fun createNewBarrierIfLineIsLongEnough() {

        val barrier = Line(_newStart, _newStop)

        if (barrier.length() > 10f) {
            useNewPointsForBarrier(barrier)
        }
    }


    private var _newStop = PositionVector()
    private var _newStart = PositionVector()

    private var _barrier: Line? = null

    private fun useNewPointsForBarrier(barrier: Line) {

        _barrier = barrier
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
        return ViewExtension.areOverlapping(_dot, _goodArea)
    }

    private fun canCollectPoint() = _state == DotState.CanCollectPoint

    private fun moveToNextPosition() {
        val newX = _dot.x + xVelocity
        val newY = _dot.y + yVelocity

        val pair = ReflectFromBorderIfNecessary(newX, newY)

        _dot.x = pair.first
        _dot.y = pair.second
    }

    private fun ReflectFromBorderIfNecessary(
        newX: Float,
        newY: Float
    ): Pair<Float, Float> {
        var newX1 = newX
        var newY1 = newY
        val effectiveWidth = width - _dot.width
        val effectiveHeight = height - _dot.height

        if (newX1 < x) {
            newX1 = reflect(x, newX1)
            xVelocity = -xVelocity
            onOuterAreaReached()
        }
        if (newY1 < y) {
            newY1 = reflect(y, newY1)
            yVelocity = -yVelocity
            onOuterAreaReached()
        }
        if (newX1 > x + effectiveWidth) {
            newX1 = reflect(x + effectiveWidth, newX1)
            xVelocity = -xVelocity
            onOuterAreaReached()
        }
        if (newY1 > y + effectiveHeight) {
            newY1 = reflect(y + effectiveHeight, newY1)
            yVelocity = -yVelocity
            onOuterAreaReached()
        }
        return Pair(newX1, newY1)
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