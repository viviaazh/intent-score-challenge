package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static id.putraprima.skorbola.MatchActivity.AWAYSCORE_KEY;
import static id.putraprima.skorbola.MatchActivity.HOMESCORE_KEY;

public class ScorerActivity extends AppCompatActivity {
    private TextView homeScore;
    private TextView awayScore;
    private TextView scoreHome;
    private TextView scoreAway;

    int scorehome =0;
    int scoreaway=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);
        homeScore = findViewById(R.id.txtScorer);
        awayScore = findViewById(R.id.txtScorer);
    }

    public void handleOk(View view) {
        String stringToPassBack = homeScore.getText().toString();
        String stringToPassBack2 = awayScore.getText().toString();
        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtra(HOMESCORE_KEY, stringToPassBack);
        intent.putExtra(AWAYSCORE_KEY, stringToPassBack2);
        setResult(RESULT_OK, intent);
        finish();
    }
}
