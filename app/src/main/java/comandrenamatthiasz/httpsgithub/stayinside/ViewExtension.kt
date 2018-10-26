package comandrenamatthiasz.httpsgithub.stayinside

import android.view.View

class ViewExtension{
    companion object {
        fun areOverlapping(first: View, second:View):Boolean{
            val leftSideOfFirstIsLeftFromRightSightOfSecond = first.x <= (second.x + second.width)
            val rightSideOfFirstIsRightFromLeftSideOfSecond = (first.x + first.width) >= second.x
            val upperSideOfFirstIsAboveLowerSideOfSecond = first.y <= (second.y + second.height)
            val lowerSideOfFirstIsBelowUpperSideOfSecond = (first.y + first.height) >= second.y

            val xOverlapping = leftSideOfFirstIsLeftFromRightSightOfSecond && rightSideOfFirstIsRightFromLeftSideOfSecond
            val yOverlapping = upperSideOfFirstIsAboveLowerSideOfSecond && lowerSideOfFirstIsBelowUpperSideOfSecond

            return xOverlapping && yOverlapping
        }
    }
}