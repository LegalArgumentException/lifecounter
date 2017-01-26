package edu.washington.tg71223.lifecounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String TAG = "LifeCounter";
    GameState state = new GameState();

    private class GameState {

        List<Player> playerList = new ArrayList<Player>();

        public void init() {
            for(int i = 1; i <= 4; i++) {
                Player player = new Player();
                player.name = "player" + i;
                playerList.add(player);
                Log.i(TAG, player.name + " added to playerList, playerList now has size of " + playerList.size());
            }
        }
    }

    public void changeLife(View v) {
        String parentTag = ((ViewGroup) v.getParent()).getTag().toString();
        int lifeChange = Integer.parseInt(v.getTag().toString());
        String playerLife;
        for(Player player : state.playerList) {

            // Iterate through players until the correct one is found
            if(player.name.equals(parentTag)) {

                // Change player's life value
                player.changeLife(lifeChange);

                // Update view with player's current life value
                playerLife = "" + player.life;
                TextView textChild = (TextView)((ViewGroup)v.getParent()).getChildAt(0);
                textChild.setText(playerLife);

                // Change message if player is currently dead
                if(player.isDead()) {
                setMessage(parentTag + " LOST!");
                }

                // Log current player status
                Log.i( TAG, "parent is " + parentTag + ", Player Life is " + playerLife + ", player dead is " + player.isDead() );
            }
        }
    }

    public void setMessage(String message) {
        TextView currentMessage = (TextView)findViewById(R.id.message);
        currentMessage.setText(message);
    }

    private class Player {
        int life = 20;
        String name;

        public void changeLife(int change) {
            life += change;
        }

        public boolean isDead() {
            return life < 1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        state.init();
    }
}
