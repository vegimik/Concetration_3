package ml.x1carbon.concetration_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class Database extends SQLiteOpenHelper {
    public  Database(Context context){
        super(context,"memorygamedb",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE highscores"+
        "(playername varchar(30), score integer, level varchar (30))");
    }

    public boolean addData(String playername, int score, String level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("playername", playername);
        contentValues.put("score", score);
        contentValues.put("level", level);

        long result = db.insert("highscores", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(String level){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM highscores WHERE level ='" + level + "' ORDER BY score DESC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getHighscore(String level){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT MAX(score)\n" +
                "FROM highscores; ";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS highscores");
        onCreate(db);
    }
}
