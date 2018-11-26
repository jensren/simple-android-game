package gamecentre.battlegame;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import gamecentre.slidingtiles.R;

/**
 * Starting activity for the battle game.
 */
public class BattleStartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static String saveFileName;
    /**
     * A temporary save file.
     */
    public static String tempSaveFileName;
    /**
     * The auto saved file.
     */
    public static String autoSaveFileName;
    /**
     * The battle queue.
     */
    private BattleQueue battleQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        battleQueue = new BattleQueue();
        saveToFile(tempSaveFileName);

        setContentView(R.layout.activity_battlegame_starting);
        startAnimations();

        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
    }

    /**
     * Start the animations in the activity.
     */
    private void startAnimations() {
        AnimationDrawable shibeAnimation;
        ImageView imageView = findViewById(R.id.animatedshibe);
        imageView.setBackgroundResource(R.drawable.animated_shibe);
        shibeAnimation = (AnimationDrawable) imageView.getBackground();
        shibeAnimation.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.battlestart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToCatChoiceActivity();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.battleload);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(saveFileName);
                saveToFile(tempSaveFileName);
                saveToFile(autoSaveFileName);
                makeToastLoadedText();
                switchToGameActivity();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.battlesave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(saveFileName);
                saveToFile(tempSaveFileName);
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to the CatChoiceActivity view.
     */
    private void switchToCatChoiceActivity() {
        Intent tmp = new Intent(this, CatOrDogActivity.class);
        //saveToFile(SlidingtilesStartingActivity.tempSaveFileName);
        startActivity(tmp);
    }

    /**
     * Switch to the BattleGame view.
     */
    private void switchToGameActivity() {
        Intent tmp = new Intent(this, CatOrDogActivity.class);
        saveToFile(BattleStartingActivity.tempSaveFileName);
        startActivity(tmp);
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(tempSaveFileName);
    }

    /**
     * Load the battle queue from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                battleQueue = (BattleQueue) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the battle queue to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(battleQueue);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}