package comandrenamatthiasz.httpsgithub.stayinside

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView



class Points : TextView {


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        init()
    }

    private fun init() {
    }

    private var _value: Int = 0

    fun resetPoints() {
        _value = 0
        updateText()
    }

    val text = "Points: "

    private fun updateText() {
        setText(text + _value)
    }

    fun increasePoints() {
        _value++
        updateText()
    }
}