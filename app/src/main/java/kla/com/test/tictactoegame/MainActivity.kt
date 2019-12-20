package kla.com.test.tictactoegame

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.gridlayout.widget.GridLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var gameState = intArrayOf (2, 2, 2, 2, 2, 2, 2, 2, 2)
    var winningPositions  = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    var playerCount = 0
    var gameActive = true

    fun dropIn(view: View) {

        val dropInAction: ImageView = view as ImageView
        val tapped =  dropInAction.tag.toString().toInt()

        if (gameState[tapped] == 2 && gameActive) {

            gameState[tapped] = playerCount

            playerCount = if (playerCount == 0) {

                dropInAction.setImageResource(R.drawable.b)
                dropInAction.alpha = 0f
                dropInAction.animate().alpha(1f).duration = 800
                1
            } else {

                dropInAction.setImageResource(R.drawable.a)
                dropInAction.translationY = -1300f
                dropInAction.animate().translationYBy(1300f).rotation(1080f)
                    .duration = 2000
                0
            }
        }

        for (winningPosition in winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                gameState[winningPosition[0]] != 2) {

                gameActive = false

                val winner = if (playerCount == 1) "Saiparn's Cat" else "Saiparn"

                Toast.makeText(this, "Game ends, $winner got lucky", Toast.LENGTH_LONG).show()

                val replayButton = findViewById<Button>(R.id.replayButton)
                val winningMessage = findViewById<TextView>(R.id.winningMessage)

                winningMessage.text = "$winner has won the game!"
                winningMessage.isVisible = true
                replayButton.isVisible = true

            } else if (2 !in gameState) {
                gameActive = false
                winningMessage.text = "Nobody wins :("
                winningMessage.isVisible = true
                replayButton.isVisible = true
            }
        }

    }

    fun playAgain(view: View) {
        val replayButton = findViewById<Button>(R.id.replayButton)
        val winningMessage = findViewById<TextView>(R.id.winningMessage)

        winningMessage.isVisible = false
        replayButton.isVisible = false

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)

        for (i in 0 until gridLayout.childCount){
            val playerCount = gridLayout.getChildAt(i) as ImageView
            playerCount.setImageDrawable(null)
        }

        for (i in gameState.indices) {
            gameState[i] = 2
        }

        playerCount = 0
        gameActive = true

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
