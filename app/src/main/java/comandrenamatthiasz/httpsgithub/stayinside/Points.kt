package layout

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView



class Points : TextView {


    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context, attributeSet, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        init(context, attributeSet, defStyle)
    }

    private fun init(context: Context, attributeSet: AttributeSet?, defStyle: Int) {
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