package comandrenamatthiasz.httpsgithub.stayinside

class MovementVector(vX: Float = 0f, vY: Float = 0f){

    private var _vector: Vector = Vector(vX, vY)

    fun move(position: PositionVector): PositionVector {
        return position.moveBy(_vector)
    }
}