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

    fun minus(other : MovementVector):MovementVector{
        return MovementVector(_vector.minus(other._vector))
    }

    fun getParallelComponentOf(other: MovementVector): MovementVector {
        val scalarProduct =_vector.scalarProduct(other._vector)
        val scalarProductWithItself = other._vector.length()*other._vector.length()

        val parallelComponent = other._vector.scaleWith(scalarProduct/scalarProductWithItself)

        return MovementVector(parallelComponent)
    }

    fun getOrthogonalComponentOf(other: MovementVector): MovementVector {
        val orthogonal = other._vector.rotateByNinetyDegree()
        return getParallelComponentOf(MovementVector(orthogonal))
    }

    fun reflectOnXAxis(): MovementVector {
        return MovementVector(_vector.reflectOnXAxis())
    }

    fun reflectOnYAxis(): MovementVector {
        return MovementVector(_vector.reflectOnYAxis())
    }
}