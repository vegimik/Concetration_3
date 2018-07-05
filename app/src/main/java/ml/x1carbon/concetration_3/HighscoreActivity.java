package ml.x1carbon.concetration_3;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HighscoreActivity extends AppCompatActivity {

    Button btnEasyHighscores;
    Button btnMediumHighscores;



    Button btnHardHighscores;
    ListView listViewResult;
    int counter=0;

    Database myDatabase;
    ListView lvHighscore;
    ListAdapter adapter;
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<AddPlayer> playersList;
    DatabaseReference databaseReferencePlayers;
    DatabaseReference _databaseReferencePlayers;
    String playerNameEasyString="";
    String playerScoreEasyString="";
    String playerNameMediumString="";
    String playerScoreMediumString="";
    String playerNameHardString="";
    String playerScoreHardString="";

    String total="";
    int u1=0, u2=0, u3=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        listViewResult=(ListView)findViewById(R.id.lvHighscore);

        databaseReferencePlayers = FirebaseDatabase.getInstance().getReference("player");
        _databaseReferencePlayers = FirebaseDatabase.getInstance().getReference("player");
        playersList = new ArrayList<>();
        databaseReferencePlayers.keepSynced(true);
        _databaseReferencePlayers.keepSynced(true);


        databaseReferencePlayers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                playersList.clear();

                //iterating through all the nodes

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AddPlayer playeri = postSnapshot.getValue(AddPlayer.class);
                    //adding artist to the list
                    playersList.add(playeri);

                    if (playeri.getPlayerGameDifficulty().toString().equals("easy"))
                    {
                        u1++;
                        if (playerNameEasyString.equals(""))
                        {
                            playerNameEasyString=playeri.getPlayerName().toString();
                            playerScoreEasyString=playeri.getPlayerScore().toString();
                        }
                        else
                        {
                            playerNameEasyString=playerNameEasyString+"&&"+playeri.getPlayerName().toString();
                            playerScoreEasyString=playerScoreEasyString+"&&"+playeri.getPlayerScore().toString();
                        }
//                        Toast.makeText(getApplicationContext(),name+", "+score+", "+gameDifficulty, Toast.LENGTH_LONG).show();
                    }
                    else if (playeri.getPlayerGameDifficulty().toString().equals("medium"))
                    {
                        if (playerNameMediumString.equals(""))
                        {
                            playerNameMediumString=playeri.getPlayerName().toString();
                            playerScoreMediumString=playeri.getPlayerScore().toString();
                        }
                        else
                        {
                            playerNameMediumString=playerNameMediumString+"&&"+playeri.getPlayerName().toString();
                            playerScoreMediumString=playerScoreMediumString+"&&"+playeri.getPlayerScore().toString();
                        }

                        u2++;
                    }
                    else
                    {
                        if (playerNameHardString.equals(""))
                        {
                            playerNameHardString=playeri.getPlayerName().toString();
                            playerScoreHardString=playeri.getPlayerScore().toString();
                        }
                        else
                        {
                            playerNameHardString=playerNameHardString+"&&"+playeri.getPlayerName().toString();
                            playerScoreHardString=playerScoreHardString+"&&"+playeri.getPlayerScore().toString();
                        }

                        u3++;
                    }

                    counter++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        btnEasyHighscores = findViewById(R.id.btnEasyHighscores);
        btnMediumHighscores = findViewById(R.id.btnMediumHighscores);
        btnHardHighscores = findViewById(R.id.btnHardHighscores);

        lvHighscore = findViewById(R.id.lvHighscore);
        myDatabase = new Database(this);



        String[] playerNameEasyArray=playerNameEasyString.split("&&");
        String[] playerScoreEasyArray=playerScoreEasyString.split("&&");
        PlayerRowLayoutAdapter playerRowLayoutAdapter=new PlayerRowLayoutAdapter(HighscoreActivity.this, playerNameEasyArray, playerScoreEasyArray);



        btnEasyHighscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] playerNameEasyArray=playerNameEasyString.split("&&");
                String[] playerScoreEasyArray=playerScoreEasyString.split("&&");
                PlayerRowLayoutAdapter playerRowLayoutAdapter=new PlayerRowLayoutAdapter(HighscoreActivity.this, playerNameEasyArray, playerScoreEasyArray);
                listViewResult.setAdapter(playerRowLayoutAdapter);

//              populateListView("easy");
                btnEasyHighscores.setBackgroundResource(R.drawable.tab_button_active);
                btnMediumHighscores.setBackgroundResource(R.drawable.tab_button_inactive);
                btnHardHighscores.setBackgroundResource(R.drawable.tab_button_inactive);
            }
        });

        btnMediumHighscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] playerNameMediumArray=playerNameMediumString.split("&&");
                String[] playerScoreMediumArray=playerScoreMediumString.split("&&");
                PlayerRowLayoutAdapter playerRowLayoutAdapter=new PlayerRowLayoutAdapter(HighscoreActivity.this, playerNameMediumArray, playerScoreMediumArray);
                listViewResult.setAdapter(playerRowLayoutAdapter);

//              populateListView("medium");
                btnEasyHighscores.setBackgroundResource(R.drawable.tab_button_inactive);
                btnMediumHighscores.setBackgroundResource(R.drawable.tab_button_active);
                btnHardHighscores.setBackgroundResource(R.drawable.tab_button_inactive);
            }
        });

        btnHardHighscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] playerNameHardArray=playerNameHardString.split("&&");
                String[] playerScoreHardArray=playerScoreHardString.split("&&");
                PlayerRowLayoutAdapter playerRowLayoutAdapter=new PlayerRowLayoutAdapter(HighscoreActivity.this, playerNameHardArray, playerScoreHardArray);
                listViewResult.setAdapter(playerRowLayoutAdapter);

            //  populateListView("hard");
                btnEasyHighscores.setBackgroundResource(R.drawable.tab_button_inactive);
                btnMediumHighscores.setBackgroundResource(R.drawable.tab_button_inactive);
                btnHardHighscores.setBackgroundResource(R.drawable.tab_button_active);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReferencePlayers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                playersList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AddPlayer playeri = postSnapshot.getValue(AddPlayer.class);
                    //adding artist to the list
                    playersList.add(playeri);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    class PlayerRowLayoutAdapter extends ArrayAdapter<String>
    {

        Context context;
        String[] nameArray;
        String[] scoreArray;
        public PlayerRowLayoutAdapter(Context c, String[] playerNameRow, String[] playerScoreRow) {
            super(c,R.layout.row_layout, R.id.tvName, playerNameRow);
            this.context=c;
            this.nameArray=playerNameRow;
            this.scoreArray=playerScoreRow;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View row= inflater.inflate(R.layout.row_layout, parent, false);
            TextView tvNameRow=(TextView) row.findViewById(R.id.tvName);
            TextView tvScoreRow=(TextView) row.findViewById(R.id.tvScore);

            tvNameRow.setText(nameArray[position]);//+",       "+scoreArray[position]);
            tvScoreRow.setText(scoreArray[position]);
            return row;
        }
    }
}
