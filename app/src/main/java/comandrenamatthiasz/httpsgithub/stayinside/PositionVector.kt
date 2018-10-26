package comandrenamatthiasz.httpsgithub.stayinside

import android.graphics.Canvas
import android.graphics.Paint

class PositionVector(private val x: Float =0f, private val y: Float =0f) {

    fun drawLineTo(otherVector: PositionVector, canvas: Canvas,paint: Paint){
        canvas.drawLine(x,y,otherVector.x,otherVector.y,paint)
    }

    fun distanceSquaredTo(other: PositionVector):Float{
        return (x- other.x)*(x-other.x)+(y-other.y)*(y-other.y)
    }

}