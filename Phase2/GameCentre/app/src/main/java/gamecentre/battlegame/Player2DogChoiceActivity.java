package gamecentre.battlegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import gamecentre.slidingtiles.R;

/**
 * Activity for choosing the dog to play.
 */
public class Player2DogChoiceActivity extends AppCompatActivity {

    static boolean isPlayer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battlegame_p2_dog_character);

        addDetectiveButtonListener();
        addDruidButtonListener();
        addSirShibeButtonListener();

    }

    /**
     * Add the Sir Shibe button.
     */
    private void addSirShibeButtonListener() {
        Button sirShibeButton = findViewById(R.id.sir);
        sirShibeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameActivity("SirShibe");
            }
        });
    }

    /**
     * Add the Detective button.
     */
    private void addDetectiveButtonListener() {
        Button detectiveButton = findViewById(R.id.detective);
        detectiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameActivity("DetectiveShibe");
            }
        });
    }

    /**
     * Add the Druid button.
     */
    private void addDruidButtonListener() {
        Button druidButton = findViewById(R.id.druid);
        druidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameActivity("DruidShibe");
            }
        });
    }

    /**
     * Switch to the GameActivity view.
     */
    private void switchToGameActivity(String character) {
        Intent tmp = new Intent(this, BattleGameActivity.class);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            extras.putString("player2", character);
        } else
            throw new NullPointerException("Value of Bundle is null");
        tmp.putExtras(extras);
        startActivity(tmp);
    }
}
