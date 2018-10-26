package comandrenamatthiasz.httpsgithub.stayinside

import android.view.View

class ViewExtension{
    companion object {
        fun areOverlapping(first: View, second:View):Boolean{
            val xOverlapping = first.x <= (second.x + second.width) && (first.x + first.width) >= second.x
            val yOverlapping = first.y <= (second.y + second.height) && (first.y + first.height) >= second.y

            return xOverlapping && yOverlapping
        }
    }
}