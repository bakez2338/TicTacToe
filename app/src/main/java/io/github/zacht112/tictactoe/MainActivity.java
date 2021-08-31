package io.github.zacht112.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;

    // Players:
    // X == player 0
    // O == player 1
    int activePlayer = 0;

    // States:
    // 0 == X
    // 1 == O
    // 2 == Null
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    // All possible win positions
    int[][] winPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    public static int counter = 0;

    //This function will be called every time a player taps in an empty box of the grid
    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        // Game reset if someone wins or boxes are full
        if (!gameActive) {
            gameReset(view);
        }

        // if tapped image is empty
        if (gameState[tappedImage] == 2) {
            // increase counter
            counter++;
            // check if it's the last box
            if (counter == 9) {
                // Reset the game
                gameActive = false;
            }

            // mark this position
            gameState[tappedImage] = activePlayer;

            // This will give a motion effect to the image
            img.setTranslationY(-1000f);

            // change the active player
            if (activePlayer == 0) {
                // set the image to x
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);

                // Change the status
                status.setText(getString(R.string.oTurn));
            } else {
                // Set the image to o
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);

                // Change the status
                status.setText(getString(R.string.xTurn));
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        int flag = 0;
        // Check if a player has won
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                flag = 1;

                String winnerString;
                // Reset game
                gameActive = false;
                if (gameState[winPosition[0]] == 0) {
                    winnerString = getString(R.string.xWin);
                } else {
                    winnerString = getString(R.string.oWin);
                }
                // Update status bar
                TextView status = findViewById(R.id.status);
                status.setText(winnerString);
            }
        }
        // set the status if the match is a draw
        if (counter == 9 && flag == 0) {
            TextView status = findViewById(R.id.status);
            status.setText(R.string.drawText);
        }
    }

    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;
        counter = 0;
        for (int i = 0; i < gameState.length; i++)
            gameState[i] = 2;
        // Remove all images
        ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText(getString(R.string.xTurn));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}