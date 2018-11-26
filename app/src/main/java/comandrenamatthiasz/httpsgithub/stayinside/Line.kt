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

    fun crosses(other:Line):Boolean{
        val thisSeparatesOther = separates(other._first, other._second)
        val otherSeparatesThis = other.separates(_first,_second)


        return thisSeparatesOther&&otherSeparatesThis
    }

    private fun separates(
        first: PositionVector,
        second: PositionVector
    ): Boolean {
        val normal = getNormal()

        val fromFirstToOnePointOfLine = first.movesTo(_first)
        val fromSecondToOnePointOfLine = second.movesTo(_first)

        val scalarProduct1 = normal.scalarProduct(fromFirstToOnePointOfLine)
        val scalarProduct2 = normal.scalarProduct(fromSecondToOnePointOfLine)

        val bothPointsAreOnDifferentSideOfHalfSpaceGeneratedByLine = scalarProduct1 * scalarProduct2 < 0

        return bothPointsAreOnDifferentSideOfHalfSpaceGeneratedByLine
    }

    private fun getNormal(): MovementVector {
        val directionOfLine = _second.movesTo(_first)
        val normal = directionOfLine.rotateByNinetyDegree()
        return normal
    }

    fun reflect(direction: MovementVector):MovementVector{
        val parallel = direction.getParallelComponentOf(_second.movesTo(_first))
        val orthogonal = direction.getOrthogonalComponentOf(_second.movesTo(_first))
        return parallel.minus(orthogonal)
    }
}