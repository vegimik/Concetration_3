package ml.x1carbon.concetration_3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.List;
import java.util.Random;

public class StarterActivity extends AppCompatActivity {

    Button btnOnePlayer, btnTwoPlayers, btnPlayerCpu, btnHighscores;
    Button btnEasy, btnMedium, btnHard;
    AlertDialog dialog;
    Intent intent;
    Bitmap[] bitmapImage;
    public  static List<imageDataSource> dataSourcesEachImage;//=new ArrayList<imageDataSource>();
    String server_url="";//"http://farm2.static.flickr.com/1785/41271119680_dbe475db8a.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnOnePlayer = (Button) findViewById(R.id.btnOnePlayer);
        btnTwoPlayers = (Button) findViewById(R.id.btnTwoPlayers);
        btnPlayerCpu = (Button) findViewById(R.id.btnPlayerCpu);
        btnHighscores = (Button) findViewById(R.id.btnHighscores);

        intent = new Intent(getApplicationContext(), BoardGameActivity.class);

        btnOnePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("GameMode","1player");
                startActivity(intent);
            }
        });

        btnTwoPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("GameMode","2players");
                startActivity(intent);
            }
        });

        btnPlayerCpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(StarterActivity.this);
                alert.setTitle("Select game difficulty:");
                alert.setView(R.layout.select_difficulty);

                dialog = alert.create();
                dialog.show();

                btnEasy = (Button) dialog.findViewById(R.id.btnEasy);
                btnMedium = (Button) dialog.findViewById(R.id.btnMedium);
                btnHard = (Button) dialog.findViewById(R.id.btnHard);

                btnEasy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("GameMode","cpu");
                        intent.putExtra("GameDifficulty","easy");
                        startActivity(intent);
                        dialog.hide();
                    }
                });

                btnMedium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("GameMode","cpu");
                        intent.putExtra("GameDifficulty","medium");
                        startActivity(intent);
                        dialog.hide();
                    }
                });

                btnHard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra("GameMode","cpu");
                        intent.putExtra("GameDifficulty","hard");
                        startActivity(intent);
                        dialog.hide();
                    }
                });
            }
        });

        btnHighscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HighscoreActivity.class);
                startActivity(intent);
            }
        });

        fetchDataClass objFetchDataClass=new fetchDataClass();
        objFetchDataClass.execute();

        for (int i=0; i<10;i++)
        {
            final int j=i;
            Random random=new Random();
            int numriFotos=random.nextInt(100)+1;
//            Toast.makeText(getApplicationContext(), String.valueOf(numriFotos), Toast.LENGTH_LONG).show();
            server_url="http://farm"+dataSourcesEachImage.get(numriFotos).getFarmImg()+".static.flickr.com/"+dataSourcesEachImage.get(numriFotos).getServerImg()+"/"+dataSourcesEachImage.get(numriFotos).getIdImg()+"_"+dataSourcesEachImage.get(numriFotos).getSecretImg()+".jpg";
            final ImageRequest imageRequest=new ImageRequest(server_url,
                    new com.android.volley.Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
//                                imgImg.setImageBitmap(response);
                            BoardGameActivity.bitmapImage[j]=response;
//                        imgBtnTestPamja.setImageBitmap(bitmapImage1);


                        }
                    }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Someting went wrong", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                }
            });

            MySingleton.getmInstance(StarterActivity.this).addToRequesQue(imageRequest);
        }

    }
}
