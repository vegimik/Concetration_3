package ml.x1carbon.concetration_3;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BoardGameActivity extends AppCompatActivity {

    public  static List<imageDataSource> dataSourcesEachImage;//=new ArrayList<imageDataSource>();
    String server_url="";//"http://farm2.static.flickr.com/1785/41271119680_dbe475db8a.jpg";
    public static TextView txtFetchdata;
    String data;
    public static Bitmap[] bitmapImage=new Bitmap[10];//bitmapImage1, bitmapImage2, bitmapImage3, bitmapImage4, bitmapImage5, bitmapImage6, bitmapImage7, bitmapImage8, bitmapImage9, bitmapImage10 ;
    String shikoiID="";
    int movesCardsOne=0, movesCardsTwo=0;

    TextView  tvPlayerOne, tvPlayerTwo, tvPlayerOneScore, tvPlayerTwoScore, tvPlayerOneMoves, tvPlayerTwoMoves, tvPlayerTwoDesc;

    ImageButton card0, card1, card2, card3, card4, card5, card6, card7, card8, card9,
            card10, card11, card12, card13, card14, card15, card16, card17, card18,card19;

    Integer[] cardsArray = {101,102,103,104,105,106,107,108,109,110,
            201,202,203,204,205,206,207,208,209,210};

    Bitmap image101,image102,image103,image104,image105,image106,image107,image108,image109,image110
            ,image201,image202,image203,image204,image205,image206,image207,image208,image209,image210;


    int index;

    String gameMode;
    String gameDifficulty;

    Map<Integer,Integer> cpuMemory = new LinkedHashMap<>();
    private Random randomGenerator = new Random();
    private ArrayList<ImageButton> cards = new ArrayList<ImageButton>();
    private ArrayList<Integer> removedCards = new ArrayList<Integer>();

    int firstCard, secondCard;
    int clickedFirst, clickedSecond;

    int cardCount = 1;
    int turn = 1;

    int playerOnePoints = 0, playerTwoPoints = 0;

    Handler handler = new Handler();

    static boolean active = false;
//    ImageButton imgBtnTestPamja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

//        imgBtnTestPamja=(ImageButton) findViewById(R.id.testpamja);

//ketu kemi me evendos





        Intent i = getIntent();

        gameMode = i.getStringExtra("GameMode");
        gameDifficulty = i.getStringExtra("GameDifficulty");

        tvPlayerOneScore = (TextView) findViewById(R.id.tvPlayerOneScore);
        tvPlayerTwoScore = (TextView) findViewById(R.id.tvPlayerTwoScore);
        tvPlayerOneMoves = (TextView) findViewById(R.id.tvPlayerOneMoves);
        tvPlayerTwoMoves = (TextView) findViewById(R.id.tvPlayerTwoMoves);
        tvPlayerTwoDesc = (TextView) findViewById(R.id.tvPlayerTwoDesc);
        tvPlayerOne = (TextView) findViewById(R.id.tvPlayerOne);
        tvPlayerTwo = (TextView) findViewById(R.id.tvPlayerTwo);

        card0 = (ImageButton) findViewById(R.id.card1);
        card1 = (ImageButton) findViewById(R.id.card2);
        card2 = (ImageButton) findViewById(R.id.card3);
        card3 = (ImageButton) findViewById(R.id.card4);
        card4 = (ImageButton) findViewById(R.id.card5);
        card5 = (ImageButton) findViewById(R.id.card6);
        card6 = (ImageButton) findViewById(R.id.card7);
        card7 = (ImageButton) findViewById(R.id.card8);
        card8 = (ImageButton) findViewById(R.id.card9);
        card9 = (ImageButton) findViewById(R.id.card10);
        card10 = (ImageButton) findViewById(R.id.card11);
        card11 = (ImageButton) findViewById(R.id.card12);
        card12 = (ImageButton) findViewById(R.id.card13);
        card13 = (ImageButton) findViewById(R.id.card14);
        card14 = (ImageButton) findViewById(R.id.card15);
        card15 = (ImageButton) findViewById(R.id.card16);
        card16 = (ImageButton) findViewById(R.id.card17);
        card17 = (ImageButton) findViewById(R.id.card18);
        card18 = (ImageButton) findViewById(R.id.card19);
        card19 = (ImageButton) findViewById(R.id.card20);

        cards.add(0,card0);
        cards.add(1,card1);
        cards.add(2,card2);
        cards.add(3,card3);
        cards.add(4,card4);
        cards.add(5,card5);
        cards.add(6,card6);
        cards.add(7,card7);
        cards.add(8,card8);
        cards.add(9,card9);
        cards.add(10,card10);
        cards.add(11,card11);
        cards.add(12,card12);
        cards.add(13,card13);
        cards.add(14,card14);
        cards.add(15,card15);
        cards.add(16,card16);
        cards.add(17,card17);
        cards.add(18,card18);
        cards.add(19,card19);

        card0.setTag("0");
        card1.setTag("1");
        card2.setTag("2");
        card3.setTag("3");
        card4.setTag("4");
        card5.setTag("5");
        card6.setTag("6");
        card7.setTag("7");
        card8.setTag("8");
        card9.setTag("9");
        card10.setTag("10");
        card11.setTag("11");
        card12.setTag("12");
        card13.setTag("13");
        card14.setTag("14");
        card15.setTag("15");
        card16.setTag("16");
        card17.setTag("17");
        card18.setTag("18");
        card19.setTag("19");

        tvPlayerOne.setTextColor(Color.BLACK);
        tvPlayerOneScore.setTextColor(Color.BLACK);
        tvPlayerOneMoves.setTextColor(Color.BLACK);
        tvPlayerTwo.setTextColor(Color.GRAY);
        tvPlayerTwoScore.setTextColor(Color.GRAY);
        tvPlayerTwoMoves.setTextColor(Color.GRAY);

        if(gameMode.equals("cpu")) {
            tvPlayerTwo.setText("CPU");
        } else if(gameMode.equals("1player")) {
            tvPlayerTwo.setText("");
            tvPlayerTwoScore.setText("");
            tvPlayerTwoMoves.setText("");
            tvPlayerTwoDesc.setVisibility(View.INVISIBLE);
        }

        frontOfCardsResources();

        Collections.shuffle(Arrays.asList(cardsArray));

        card0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card0, theCard);
            }
        });
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card1, theCard);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card2, theCard);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card3, theCard);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card4, theCard);
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card5, theCard);
            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card6, theCard);
            }
        });
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card7, theCard);
            }
        });
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card8, theCard);
            }
        });
        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card9, theCard);
            }
        });
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card10, theCard);
            }
        });
        card11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card11, theCard);
            }
        });
        card12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card12, theCard);
            }
        });
        card13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card13, theCard);
            }
        });
        card14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card14, theCard);
            }
        });
        card15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card15, theCard);
            }
        });
        card16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card16, theCard);
            }
        });
        card17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card17, theCard);
            }
        });
        card18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card18, theCard);
            }
        });
        card19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doAction(card19, theCard);
            }
        });
    }

    private void doAction(ImageButton ib, int card) {
        if(cardsArray[card] == 101) {
//            ib.setBackgroundResource(bitmapImage1);//image101
            ib.setImageBitmap(image101);
//            ib.setBackgroundResource(image101);
        } else if (cardsArray[card] == 102) {
//            ib.setBackgroundResource(image102);
            ib.setImageBitmap(image102);
        } else if (cardsArray[card] == 103) {
            ib.setImageBitmap(image103);
        } else if (cardsArray[card] == 104) {
            ib.setImageBitmap(image104);
        } else if (cardsArray[card] == 105) {
            ib.setImageBitmap(image105);
        } else if (cardsArray[card] == 106) {
            ib.setImageBitmap(image106);
        } else if (cardsArray[card] == 107) {
            ib.setImageBitmap(image107);
        } else if (cardsArray[card] == 108) {
            ib.setImageBitmap(image108);
        } else if (cardsArray[card] == 109) {
            ib.setImageBitmap(image109);
        } else if (cardsArray[card] == 110) {
            ib.setImageBitmap(image110);
        } else if (cardsArray[card] == 201) {
            ib.setImageBitmap(image201);
        } else if (cardsArray[card] == 202) {
            ib.setImageBitmap(image202);
        } else if (cardsArray[card] == 203) {
            ib.setImageBitmap(image203);
        } else if (cardsArray[card] == 204) {
            ib.setImageBitmap(image204);
        } else if (cardsArray[card] == 205) {
            ib.setImageBitmap(image205);
        } else if (cardsArray[card] == 206) {
            ib.setImageBitmap(image206);
        } else if (cardsArray[card] == 207) {
            ib.setImageBitmap(image207);
        } else if (cardsArray[card] == 208) {
            ib.setImageBitmap(image208);
        } else if (cardsArray[card] == 209) {
            ib.setImageBitmap(image209);
        } else if (cardsArray[card] == 210) {
            ib.setImageBitmap(image210);
        }


        if(gameMode.equals("cpu")) {
            cpuMemory.put(card,cardsArray[card]);
        }

        if(cardCount == 1) {
            firstCard = cardsArray[card];
            if(firstCard > 200) {
                firstCard = firstCard - 100;
            }
            cardCount = 2;
            clickedFirst = card;

            ib.setEnabled(false);
        } else if(cardCount == 2) {
            secondCard = cardsArray[card];
            if(secondCard > 200) {
                secondCard = secondCard - 100;
            }
            cardCount = 1;
            clickedSecond = card;

            for (ImageButton a: cards) {
                a.setEnabled(false);
            }


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    calculate();
                }
            },1000);
        }
    }

    private void calculate() {
        if(firstCard == secondCard) {

            cards.get(clickedFirst).setVisibility(View.INVISIBLE);
            cards.get(clickedSecond).setVisibility(View.INVISIBLE);

            if(turn == 1) {
                playerOnePoints++;
                tvPlayerOneScore.setText(playerOnePoints+"");
                movesCardsOne+=1;
                tvPlayerOneMoves.setText(String.valueOf(movesCardsOne));

            } else if (turn == 2) {
                playerTwoPoints++;
                tvPlayerTwoScore.setText(playerTwoPoints+"");
                movesCardsTwo+=1;
                tvPlayerTwoMoves.setText(String.valueOf(movesCardsTwo));
                if(gameMode.equals("cpu")) {
                    cpuPlay();
                }
            }

            removedCards.add(clickedFirst);
            removedCards.add(clickedSecond);

            if (gameMode.equals("cpu")) {
                cpuMemory.remove(clickedFirst);
                cpuMemory.remove(clickedSecond);
            }

        } else {
            for (ImageButton a: cards) {
                Drawable drawable=this.getResources().getDrawable(R.drawable.ic_unknown);
                Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();
                a.setImageBitmap(bitmap);
//                a.setBackgroundResource(R.drawable.ic_unknown);
            }

            if(turn == 1) {
                turn = 2;
                movesCardsOne+=1;
                tvPlayerOne.setTextColor(Color.GRAY);
                tvPlayerOneScore.setTextColor(Color.GRAY);
                tvPlayerOneMoves.setTextColor(Color.GRAY);
                tvPlayerTwo.setTextColor(Color.BLACK);
                tvPlayerTwoScore.setTextColor(Color.BLACK);
                tvPlayerTwoDesc.setTextColor(Color.BLACK);
                tvPlayerOneMoves.setText(String.valueOf(movesCardsOne));

                if(gameMode.equals("cpu")) {
                    cpuPlay();
                }
            } else if(turn == 2) {
                turn = 1;
                movesCardsTwo+=1;
                tvPlayerOne.setTextColor(Color.BLACK);
                tvPlayerOneScore.setTextColor(Color.BLACK);
                tvPlayerOneMoves.setTextColor(Color.BLACK);
                tvPlayerTwo.setTextColor(Color.GRAY);
                tvPlayerTwoScore.setTextColor(Color.GRAY);
                tvPlayerOneMoves.setTextColor(Color.GRAY);
                tvPlayerTwoMoves.setText(String.valueOf(movesCardsTwo));
            }

            if( gameMode.equals("1player") ) {
                turn = 1;
                movesCardsOne+=1;
                tvPlayerOne.setTextColor(Color.BLACK);
                tvPlayerOneScore.setTextColor(Color.BLACK);
                tvPlayerOneMoves.setTextColor(Color.BLACK);
                tvPlayerOneMoves.setText(String.valueOf(movesCardsOne/2));
            }
        }

        for (ImageButton a: cards) {
            a.setEnabled(true);
        }

        checkEnd();
    }

    private void cpuPlay() {

        if(gameDifficulty.equals("easy")) {
            Iterator<Map.Entry<Integer,Integer>> iterator = cpuMemory.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer,Integer> entry = iterator.next();
                if(cpuMemory.size() > 2){
                    iterator.remove();
                }
            }
        } else if(gameDifficulty.equals("medium")) {
            Iterator<Map.Entry<Integer,Integer>> iterator = cpuMemory.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer,Integer> entry = iterator.next();
                if(cpuMemory.size() > 4){
                    iterator.remove();
                }
            }
        }

        for (ImageButton a: cards) {
            a.setClickable(false);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean match = false;

                if (removedCards.size() != cards.size()) {

                    for (int key1 : cpuMemory.keySet()) {
                        for (int key2 : cpuMemory.keySet()) {
                            if (Math.abs(cpuMemory.get(key1) - (cpuMemory.get(key2))) == 100) {
                                index = key1;
                                match = true;
                                break;
                            }
                        }
                    }

                    if(removedCards.size() == 16 && gameDifficulty.equals("hard")) {
                        while (removedCards.contains(index) || cpuMemory.containsKey(index)) {
                            randomGenerator = new Random();
                            index = randomGenerator.nextInt(cards.size());
                        }
                        match = true;
                    }

                    if (!match) {
                        index = randomGenerator.nextInt(cards.size());

                        while (removedCards.contains(index)) {
                            randomGenerator = new Random();
                            index = randomGenerator.nextInt(cards.size());
                        }
                    }

                    ImageButton ib = cards.get(index);
                    doAction(ib, index);
                }
            }
        },1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean match = false;
                int tempIndex = index;

                if (removedCards.size() != cards.size()) {

                    for (int key1 : cpuMemory.keySet()) {
                        for (int key2 : cpuMemory.keySet()) {
                            if (Math.abs(cpuMemory.get(key1) - (cpuMemory.get(key2))) == 100) {
                                if (tempIndex != key2) {
                                    index = key2;
                                    match = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!match) {
                        while (index == tempIndex || removedCards.contains(index) || cpuMemory.containsKey(index)) {
                            randomGenerator = new Random();
                            index = randomGenerator.nextInt(cards.size());
                        }
                    }
                    if(!match)
                    {
                        while ((index ==tempIndex || removedCards.contains(index) || cpuMemory.containsKey(index)))
                        {
                            randomGenerator = new Random();
                            index = randomGenerator.nextInt(cards.size());
                        }
                    }

                    ImageButton ib = cards.get(index);
                    doAction(ib, index);

                    for (ImageButton a : cards) {
                        a.setClickable(true);
                    }
                }
            }
        },2000);
    }

    private void checkEnd() {

        if(cards.size() == removedCards.size()){
            if(active) {
                Intent intent = new Intent(getApplicationContext(),Result.class);
                intent.putExtra("playerOnePoints",tvPlayerOneScore.getText().toString()+"&&"+tvPlayerOneMoves.getText().toString());
                intent.putExtra("playerTwoPoints",tvPlayerTwoScore.getText().toString()+"&&"+tvPlayerTwoMoves.getText().toString());
                intent.putExtra("PlayerTwo",tvPlayerTwo.getText());
                intent.putExtra("GameMode",gameMode);
                intent.putExtra("GameDifficulty",gameDifficulty);
                startActivity(intent);
                finish();
            }
        }
    }

    private void frontOfCardsResources() {
        image101 = bitmapImage[0];
        image102 = bitmapImage[1];
        image103 = bitmapImage[2];
        image104 = bitmapImage[3];
        image105 = bitmapImage[4];
        image106 = bitmapImage[5];
        image107 = bitmapImage[6];
        image108 = bitmapImage[7];
        image109 = bitmapImage[8];
        image110 = bitmapImage[9];
        image201 = bitmapImage[0];
        image202 = bitmapImage[1];
        image203 = bitmapImage[2];
        image204 = bitmapImage[3];
        image205 = bitmapImage[4];
        image206 = bitmapImage[5];
        image207 = bitmapImage[6];
        image208 = bitmapImage[7];
        image209 = bitmapImage[8];
        image210 = bitmapImage[9];

    }
    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(BoardGameActivity.this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }
}
