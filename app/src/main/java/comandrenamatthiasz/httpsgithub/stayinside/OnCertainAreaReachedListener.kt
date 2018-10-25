package layout

interface OnCertainAreaReachedListener {
    fun reached(areaType: AreaType)
}

enum class AreaType {
Outer,Point
}
