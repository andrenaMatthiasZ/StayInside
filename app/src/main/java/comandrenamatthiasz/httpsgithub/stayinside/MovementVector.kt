package comandrenamatthiasz.httpsgithub.stayinside

class MovementVector {

    constructor(vX: Float = 0f, vY: Float = 0f) : this(Vector(vX, vY))
    constructor(vector: Vector) {
        _vector = vector
    }

    private var _vector: Vector

    fun move(position: PositionVector): PositionVector {
        return position.moveBy(_vector)
    }

    fun rotateByNinetyDegree(): MovementVector {
        return MovementVector(_vector.rotateByNinetyDegree())
    }

    fun scalarProduct(other: MovementVector): Float {
        return _vector.scalarProduct(other._vector)
    }

    fun length():Float{
        return _vector.length()
    }
}