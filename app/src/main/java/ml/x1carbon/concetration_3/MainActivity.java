package ml.x1carbon.concetration_3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.media.ImageReader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="Mainactivity";
    public  static List<imageDataSource> dataSourcesEachImage;//=new ArrayList<imageDataSource>();
    Button btnClickeMe;
    Button btnGetImg;
    Button btnNext;
    ImageView imgImg;
    String server_url="";//"http://farm2.static.flickr.com/1785/41271119680_dbe475db8a.jpg";
    public static TextView txtFetchdata;
    String data;
    public static String kettu="";
    Icon icon;
    Image image;
    Bitmap bitmapImage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchDataClass objFetchDataClass=new fetchDataClass();
        objFetchDataClass.execute();

        btnClickeMe=(Button)findViewById(R.id.clickMe);
        btnGetImg=(Button)findViewById(R.id.getImg);
        btnNext=(Button)findViewById(R.id.btnNext);
        txtFetchdata=(TextView)findViewById(R.id.fetchData);
        imgImg=(ImageView)findViewById(R.id.img);

        btnGetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random=new Random();
                int numriFotos=random.nextInt(100)+1;
//                Toast.makeText(getApplicationContext(), String.valueOf(numriFotos), Toast.LENGTH_LONG).show();
                server_url="http://farm"+dataSourcesEachImage.get(numriFotos).getFarmImg()+".static.flickr.com/"+dataSourcesEachImage.get(numriFotos).getServerImg()+"/"+dataSourcesEachImage.get(numriFotos).getIdImg()+"_"+dataSourcesEachImage.get(numriFotos).getSecretImg()+".jpg";
                final ImageRequest imageRequest=new ImageRequest(server_url,
                        new com.android.volley.Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
//                                imgImg.setImageBitmap(response);
                                bitmapImage1=response;
                                imgImg.setImageBitmap(response);

                            }
                        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Someting went wrong", Toast.LENGTH_LONG).show();
                        error.printStackTrace();

                    }
                });

                MySingleton.getmInstance(MainActivity.this).addToRequesQue(imageRequest);

            }
        });

        btnClickeMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                fetchDataClass objFetchDataClass=new fetchDataClass();
                objFetchDataClass.execute();

//                Toast.makeText(getApplicationContext(), objFetchDataClass.data,Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), txtFetchdata.getText().toString(),Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), kettu,Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), dataSourcesEachImage.get(2).getFarmImg()+dataSourcesEachImage.get(2).getServerImg()+dataSourcesEachImage.get(2).getIdImg()+dataSourcesEachImage.get(2).getSecretImg(),Toast.LENGTH_LONG).show();

            }
        });



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), StarterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.itHome:
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            case R.id.itGame:
                Intent intent1=new Intent(getApplicationContext(), StarterActivity.class);
                startActivity(intent1);
        }

        return true;
    }
}










/*

new AsyncTask<Void, Void, String>()
                {

                    @Override
                    protected String doInBackground(Void... voids) {
                        OkHttpClient client=new OkHttpClient();
                        Request request=new Request.Builder()
                                .url("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=168088b18a504b0df9ee2563ff926fdb&tags=dog&format=json&nojsoncallback=1&api_sig=e038cc52dcc37632d54504bc6e63d9f8")
                                .build();


                        try {
                            Response response=client.newCall(request).execute();
                            Log.d(TAG, "doInBackground: "+response.body().toString());
                            return response.body().toString();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                    }
                }.execute();


*/