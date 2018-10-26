package comandrenamatthiasz.httpsgithub.stayinside

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.sqrt


class Vector(private val x: Float =0f, private val y: Float =0f)  {

    fun add(other:Vector):Vector{
        return Vector(x+other.x,y+other.y)
    }
    fun distance(other: Vector):Float{
        return sqrt((x- other.x) *(x-other.x)+(y-other.y)*(y-other.y))
    }

    fun drawLineTo(otherVector: Vector, canvas: Canvas, paint: Paint){
        canvas.drawLine(x,y,otherVector.x,otherVector.y,paint)
    }
}