package comandrenamatthiasz.httpsgithub.stayinside

import android.graphics.Canvas
import android.graphics.Paint

class Line(first: PositionVector, second: PositionVector) {

    private var _first: PositionVector = first

    private var _second: PositionVector = second


    fun draw(canvas: Canvas, paint: Paint){
        _first.drawLineTo(_second,canvas,paint)
    }

    fun length():Float{
        return _first.distance(_second)
    }
}