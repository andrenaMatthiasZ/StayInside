package comandrenamatthiasz.httpsgithub.stayinside

class MovementVector {


    private var _vector: Vector

    constructor(vX: Float = 0f, vY: Float = 0f) {
        this._vector = Vector(vX,vY)

    }
    fun move(position: PositionVector):PositionVector{
        
    }
}




class Vector(private val x: Float =0f, private val y: Float =0f)  {

    fun add(other:Vector):Vector{
        return Vector(x+other.x,y+other.y)
    }
}