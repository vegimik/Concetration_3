package ml.x1carbon.concetration_3;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class fetchDataClass extends AsyncTask<Void, Void, Void> {

    String data="";
    String dataParsed="";
    String singleParsed="";
    List<imageDataSource> dataSourcesEachImage=new ArrayList<imageDataSource>();

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url=new URL("https://api.myjson.com/bins/1gmis2");
            //https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=cc99e0b1479c21ed88391f752e09eca4&tags=dog&format=json&nojsoncallback=1
            //  http://wego.infinityfreeapp.com/api/dogsDataSource.php
            HttpsURLConnection httpURLConnection=(HttpsURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while (line!=null)
            {
                line=bufferedReader.readLine();
                data=data+line;
            }

            JSONObject jsonArray=new JSONObject(data);
            JSONObject jsonObject=(JSONObject)jsonArray.get("photos");
            JSONArray jsonArray1=(JSONArray)jsonObject.get("photo");

            for (int i=0; i<jsonArray1.length(); i++)
            {
                JSONObject jsonObject1=(JSONObject)jsonArray1.get(i);
                imageDataSource objImageDataSource=new imageDataSource();
                objImageDataSource.setFarmImg(jsonObject1.get("farm").toString());
                objImageDataSource.setServerImg(jsonObject1.get("server").toString());
                objImageDataSource.setIdImg(jsonObject1.get("id").toString());
                objImageDataSource.setSecretImg(jsonObject1.get("secret").toString());
                singleParsed=jsonArray1.length()+"\n"+"Farm:"+jsonObject1.get("farm")+"\n"+
                        "server:"+jsonObject1.get("server")+"\n"+
                        "ID:"+jsonObject1.get("id")+"\n"+
                        "Secret:"+jsonObject1.get("secret")+"\n";

                dataParsed=dataParsed+singleParsed;

                dataSourcesEachImage.add(objImageDataSource);



            }

        }
        catch (java.io.IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.txtFetchdata.setText(this.data);
        MainActivity.kettu=dataParsed;
        MainActivity.dataSourcesEachImage=this.dataSourcesEachImage;
        BoardGameActivity.dataSourcesEachImage=this.dataSourcesEachImage;
        StarterActivity.dataSourcesEachImage=this.dataSourcesEachImage;
    }
}
