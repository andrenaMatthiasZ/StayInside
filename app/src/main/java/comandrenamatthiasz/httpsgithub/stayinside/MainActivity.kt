package comandrenamatthiasz.httpsgithub.stayinside

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import layout.AreaType
import layout.OnCertainAreaReachedListener

class MainActivity : AppCompatActivity(), OnCertainAreaReachedListener {
    override fun reached(areaType: AreaType) {
        runOnUiThread {
            when (areaType) {
                AreaType.Outer -> points.resetPoints()
                AreaType.Point -> points.increasePoints()
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        gameView.setDot(dot)
        gameView.setGoodArea(goodArea)
        gameView.startMovement()

        points.resetPoints()

        gameView.setOnGoodAreaReachedListener(this)
    }


}
