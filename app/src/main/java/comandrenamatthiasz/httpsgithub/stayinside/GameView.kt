package layout

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

class GameView: View {


    constructor(context: Context):super(context){
        init()
    }

    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        init()
    }

    constructor(context: Context,attributeSet: AttributeSet, defStyle: Int):super(context,attributeSet,defStyle){
        init()
    }


    private fun init() {
        setBackgroundColor(Color.RED)
    }
}