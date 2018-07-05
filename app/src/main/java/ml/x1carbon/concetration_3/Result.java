package ml.x1carbon.concetration_3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;


public class Result extends AppCompatActivity {

    String playerName;
    EditText etName;
    TextView scoreLabel;
    TextView highScoreLabel;
    int playerOnePoints;
    int playerTwoPoints;
    String playerTwo = "player";
    String gameMode;
    String gameDifficulty;
    String getPlayerOne;
    String getPlayerTwo;
    String pointsPlayerOne;
    String pointsPlayerTwo;
    String movesPlayerOne;
    String movesPlayerTwo;
    Database myDatabase;
    List<AddPlayer> playersList;
    DatabaseReference databaseReferencePlayers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        databaseReferencePlayers = FirebaseDatabase.getInstance().getReference("player");
        playersList = new ArrayList<>();
        databaseReferencePlayers.keepSynced(true);


        scoreLabel = findViewById(R.id.scoreLabel);
        highScoreLabel = findViewById(R.id.highScoreLabel);

        Intent i = getIntent();

        getPlayerOne=i.getStringExtra("playerOnePoints");//,0);
        getPlayerTwo=i.getStringExtra("playerTwoPoints");//,0);
        String[] gPO=getPlayerOne.split("&&");
        String[] gPT=getPlayerOne.split("&&");
        pointsPlayerOne=gPO[0];
        movesPlayerOne=gPO[1];
        pointsPlayerTwo=gPT[0];
        movesPlayerTwo=gPT[1];
        Double fRPO=(Double.parseDouble(pointsPlayerOne)/Double.parseDouble(movesPlayerOne))*10;
        Double fRPT=(Double.parseDouble(pointsPlayerTwo)/Double.parseDouble(movesPlayerTwo))*10;
        final int finalResultPlayerOne=fRPO.intValue();//Integer.parseInt(pointsPlayerOne)/Integer.parseInt(movesPlayerOne)*10;
        final int finalResultPlayerTwo=fRPT.intValue();//teger.parseInt(pointsPlayerTwo)/Integer.parseInt(movesPlayerTwo)*10;

        Toast.makeText(getApplicationContext(),pointsPlayerOne+", "+movesPlayerOne,Toast.LENGTH_LONG).show();

        playerTwo= i.getStringExtra("PlayerTwo");
        gameMode = i.getStringExtra("GameMode");
        gameDifficulty = i.getStringExtra("GameDifficulty");

        Toast.makeText(getApplicationContext(), getPlayerOne+", "+getPlayerTwo+", "+playerTwo+", "+gameMode+", "+gameDifficulty, Toast.LENGTH_LONG).show();


        if (gameMode.equals("cpu"))
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Result.this);
            alertDialogBuilder.setTitle("Enter your name:");
            alertDialogBuilder.setView(R.layout.twoplayers_result);

            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            Button btnName = alertDialog.findViewById(R.id.btnSubmit2);
            final EditText etName1 = alertDialog.findViewById(R.id.etName1);
            EditText etName2 = alertDialog.findViewById(R.id.etName2);
            TextView tvTextViewPlayer2 = alertDialog.findViewById(R.id.tvTextViewPlayer2);

            etName2.setVisibility(View.INVISIBLE);
            tvTextViewPlayer2.setVisibility(View.INVISIBLE);

            // if the user doesn't enter his name
            scoreLabel.setText("CPU: " + playerTwoPoints + "      Player: " + playerOnePoints);

            btnName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    scoreLabel.setText("CPU: " + playerTwoPoints + "      " + playerName + ": " + playerOnePoints);

                    // Saving data to database of Firebase
                    callDatabaseFirebase(etName1.getText().toString(), String.valueOf(finalResultPlayerOne), gameMode, gameDifficulty);
                    callDatabaseFirebase("cpu", String.valueOf(finalResultPlayerTwo), gameMode, gameDifficulty);



                    // Saving data to DB
                    myDatabase = new Database(Result.this);
                    boolean insertData = myDatabase.addData(playerName,Integer.valueOf(playerOnePoints),gameDifficulty);

                    if(insertData) {
                        Toast.makeText(Result.this,"Result saved in the database",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Result.this,"Result could not be saved in the database",Toast.LENGTH_LONG).show();
                    }

                    // Getting Highscore
                    Cursor data = myDatabase.getHighscore(gameDifficulty);

                    while (data.moveToNext()) {
                        highScoreLabel.setText("Highscore: " + data.getString(0));
                    }


                    alertDialog.hide();
                }
            });

        }
        else if (gameMode.equals("1player"))
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Result.this);
            alertDialogBuilder.setTitle("Enter your name:");
            alertDialogBuilder.setView(R.layout.twoplayers_result);

            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            Button btnName = alertDialog.findViewById(R.id.btnSubmit2);
            final EditText etName1 = alertDialog.findViewById(R.id.etName1);
            EditText etName2 = alertDialog.findViewById(R.id.etName2);
            TextView tvTextViewPlayer2 = alertDialog.findViewById(R.id.tvTextViewPlayer2);
            etName2.setVisibility(View.INVISIBLE);
            tvTextViewPlayer2.setVisibility(View.INVISIBLE);

            final String vleraPlayerOne="1";
            // if the user doesn't enter his name
            scoreLabel.setText("      Player "+vleraPlayerOne+": "+ finalResultPlayerOne );


            btnName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    scoreLabel.setText("      Player "+etName1.getText().toString()+": "+ finalResultPlayerOne );


                    // Saving data to database of Firebase
                    callDatabaseFirebase(etName1.getText().toString(), String.valueOf(finalResultPlayerOne), gameMode, "medium");



                    // Saving data to DB
                    myDatabase = new Database(Result.this);
                    boolean insertData = myDatabase.addData(playerName,Integer.valueOf(playerOnePoints),gameDifficulty);

                    if(insertData) {
                        Toast.makeText(Result.this,"Result saved in the database",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Result.this,"Result could not be saved in the database",Toast.LENGTH_LONG).show();
                    }

                    // Getting Highscore
                    Cursor data = myDatabase.getHighscore(gameDifficulty);

                    while (data.moveToNext()) {
                        highScoreLabel.setText("Highscore: " + data.getString(0));
                    }


                    alertDialog.hide();
                }
            });
        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Result.this);
            alertDialogBuilder.setTitle("Enter your names:");
            alertDialogBuilder.setView(R.layout.twoplayers_result);

            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            Button btnName = alertDialog.findViewById(R.id.btnSubmit2);
            final EditText etName1 = alertDialog.findViewById(R.id.etName1);
            final EditText etName2 = alertDialog.findViewById(R.id.etName2);

            String vleraPlayerOne="1";
            String vleraPlayerTwo="2";

            // if the user doesn't enter his name
            scoreLabel.setText("      Player "+vleraPlayerOne+": "+ finalResultPlayerOne+"\n"+"      Player "+vleraPlayerTwo+": "+ finalResultPlayerTwo);

            btnName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), String.valueOf(finalResultPlayerTwo)+", "+String.valueOf(finalResultPlayerTwo), Toast.LENGTH_LONG).show();

                    scoreLabel.setText("      Player "+etName1.getText().toString()+": "+ finalResultPlayerOne+"\n"+"      Player "+etName2.getText().toString()+": "+ finalResultPlayerTwo);

                    //Saving in databe of firebase

                    callDatabaseFirebase(etName1.getText().toString(), String.valueOf(finalResultPlayerOne), gameMode, "medium");
                    callDatabaseFirebase(etName2.getText().toString(), String.valueOf(finalResultPlayerTwo), gameMode, "medium");
//                    String id = databaseReferencePlayers.push().getKey();
//                    AddPlayer playeri = new AddPlayer(etName1.getText().toString(), String.valueOf(finalResultPlayerOne), gameMode, gameDifficulty, id);
//                    databaseReferencePlayers.child(id).setValue(playeri);
//
//                    finish();


                    // Saving data to DB
                    myDatabase = new Database(Result.this);
                    boolean insertData = myDatabase.addData(playerName,Integer.valueOf(playerOnePoints),gameDifficulty);

                    if(insertData) {
                        Toast.makeText(Result.this,"Result saved in the database",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Result.this,"Result could not be saved in the database",Toast.LENGTH_LONG).show();
                    }

                    // Getting Highscore
                    Cursor data = myDatabase.getHighscore(gameDifficulty);

                    while (data.moveToNext()) {
                        highScoreLabel.setText("Highscore: " + data.getString(0));
                    }


                    alertDialog.hide();
                }
            });

        }


//

//        if(gameMode.equals("cpu")) {



//        } else if(gameMode.equals("2players")) {
//            playerName = "Player 1";
//            scoreLabel.setText(playerName + ": " + playerOnePoints +"\n"+ playerTwo + ": " + playerTwoPoints);
//        } else {
//            scoreLabel.setText("You Won!");
//        }


    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    public void callDatabaseFirebase(String playerName, String playerScore, String playerGameMode, String playerGameDifficulty)
    {
        String id = databaseReferencePlayers.push().getKey();
        AddPlayer playeri = new AddPlayer(playerName, playerScore, playerGameMode, playerGameDifficulty, id);
        databaseReferencePlayers.child(id).setValue(playeri);
    }


    public void tryAgain(View view)
    {
        Intent intent = new Intent(getApplicationContext(),BoardGameActivity.class);
        intent.putExtra("GameMode",gameMode);
        intent.putExtra("GameDifficulty",gameDifficulty);
        startActivity(intent);
        finish();
    }
    public void backToMeny (View view)
    {
        finish();
    }
    public void share (View view)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "I scored " + playerOnePoints
                + " points, level "+ gameDifficulty +" in Memory Game, can you beat my score?" );
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }




}
