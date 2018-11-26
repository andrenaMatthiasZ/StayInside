package comandrenamatthiasz.httpsgithub.stayinside

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import kotlin.math.sqrt


class Vector(private val x: Float =0f, private val y: Float =0f)  {

    fun add(other:Vector):Vector{
        return Vector(x+other.x,y+other.y)
    }

    fun distance(other: Vector):Float{
        return sqrt((x- other.x) *(x-other.x)+(y-other.y)*(y-other.y))
    }

    fun length():Float{
       return distance(Vector())
    }

    fun drawLineTo(otherVector: Vector, canvas: Canvas, paint: Paint){
        canvas.drawLine(x,y,otherVector.x,otherVector.y,paint)
    }

    fun minus(other: Vector) :Vector{
        return Vector(x-other.x,y-other.y)
    }

    fun rotateByNinetyDegree(): Vector {
        return Vector(-y,x)
    }

    fun scalarProduct(other: Vector): Float {
        return x*other.x+y*other.y
    }

    fun setAsPosition(view: View) {
        view.x = x
        view.y = y
    }

    fun scaleWith(scalingFactor: Float): Vector {
          return Vector(x*scalingFactor,y*scalingFactor)
    }

    fun reflectOnXAxis(): Vector {
        return Vector(-x,y)
    }

    fun reflectOnYAxis(): Vector {
        return Vector(x,-y)
    }

}