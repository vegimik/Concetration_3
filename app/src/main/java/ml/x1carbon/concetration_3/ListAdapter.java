package ml.x1carbon.concetration_3;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Player> {

    TextView tvName;
    TextView tvScore;
    ArrayList<Player> players;
    Activity c;

    public ListAdapter(@NonNull Activity context, ArrayList<Player> players) {
        super(context, R.layout.row_layout, players);
        c = context;
        this.players = players;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = c.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row_layout, null, true);

        tvName = rowView.findViewById(R.id.tvName);
        tvScore = rowView.findViewById(R.id.tvScore);

        tvName.setText(players.get(position).getPlayer());
        tvScore.setText(players.get(position).getScore());

        return rowView;
    }
}