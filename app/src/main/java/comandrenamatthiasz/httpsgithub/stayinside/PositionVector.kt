package comandrenamatthiasz.httpsgithub.stayinside

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

class PositionVector {

    private var _vector: Vector

    constructor(x: Float=0f, y: Float=0f):this(Vector(x,y))

    private constructor(vector:Vector){
        _vector=vector
    }

    fun drawLineTo(otherVector: PositionVector, canvas: Canvas, paint: Paint){
        _vector.drawLineTo(otherVector._vector,canvas,paint)
    }

    fun distance(other: PositionVector):Float{
        return _vector.distance(other._vector)
    }

    fun moveBy(vector: Vector): PositionVector {
        return PositionVector(_vector.add(vector))
    }

    fun movesTo(other: PositionVector): MovementVector {
        return MovementVector(other._vector.minus(_vector))
    }

    fun setAsPosition(view: View) {
        _vector.setAsPosition(view)
    }


}