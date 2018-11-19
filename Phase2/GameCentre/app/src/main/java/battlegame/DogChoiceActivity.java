package battlegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import slidingtiles.R;

/**
 * Activity for choosing the dog to play.
 */
public class DogChoiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battlegame_main);

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
                switchToGameActivity();
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
                switchToGameActivity();
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
                switchToGameActivity();
            }
        });
    }

    /**
     * Switch to the battle game activity.
     */
    private void switchToGameActivity() {
        Intent tmp = new Intent(this, BattleGameActivity.class);
        startActivity(tmp);
    }
}
