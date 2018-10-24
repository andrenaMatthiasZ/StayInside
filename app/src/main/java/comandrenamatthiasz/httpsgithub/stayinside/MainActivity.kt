package comandrenamatthiasz.httpsgithub.stayinside

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    val horizontalMode = "horizontal"
    val verticalMode = "vertical"
    private var mode = horizontalMode


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        horizontalButton.setOnClickListener {
            mode = horizontalMode
            setTextShown()
        }
        verticalButton.setOnClickListener {
            mode = verticalMode
            setTextShown()
        }

        setTextShown()
        gameView.setDot(dot)
        gameView.setGoodArea(goodArea)
    }

    private fun setTextShown() {
        textView.text = mode
    }
}
