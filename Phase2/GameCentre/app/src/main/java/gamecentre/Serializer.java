package gamecentre;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import gamecentre.cardmatching.MatchingBoard;
import gamecentre.cardmatching.MatchingBoardManager;
import gamecentre.slidingtiles.BoardManager;


/**
 * Saves and loads (serializes) files.
 */
@SuppressLint("Registered")
public class Serializer extends AppCompatActivity {

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    public BoardManager loadBoardManagerFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                BoardManager tmpBoardManager = (BoardManager) input.readObject();
                inputStream.close();
                return tmpBoardManager;
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveBoardManagerToFile(String fileName, BoardManager boardManager) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load a preexisting ScoreBoard from a previous game.
     *
     * @param fileName name of file to load
     */
    public Scoreboard loadScoreboardFromFile(String fileName) {
        try {
            InputStream inputStream = new FileInputStream(fileName);
            ObjectInputStream input = new ObjectInputStream(inputStream);
            Scoreboard tmpScoreboard = (Scoreboard) input.readObject();
            inputStream.close();
            return tmpScoreboard;
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }

    /**
     * Save the current scoreBoard to file for later access.
     *
     * @param fileName name of the file to save
     */
    public void saveScoreboardToFile(String fileName, Scoreboard scoreboard) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(scoreboard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    public MatchingBoardManager loadMatchingBoardManagerFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                MatchingBoardManager tmpBoardManager = (MatchingBoardManager) input.readObject();
                inputStream.close();
                return tmpBoardManager;
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveMatchingBoardManagerToFile(String fileName, MatchingBoardManager matchingBoardManager) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(matchingBoardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the user manager from USER_FILENAME.
     */
    public UserManager loadUserManagerFromFile(String filename) {
        try {
            InputStream inputStream = this.openFileInput(filename);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                UserManager tmpUserManager = (UserManager) input.readObject();
                if (tmpUserManager == null) {
                    tmpUserManager = new UserManager();
                }
                inputStream.close();
                return tmpUserManager;
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }

    /**
     * Save the user manager to USER_FILENAME.
     */
    public void saveUserManagerToFile(String filename, UserManager userManager) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(filename, MODE_PRIVATE));
            outputStream.writeObject(userManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
