package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import static id.putraprima.skorbola.MainActivity.AWAYIMAGE_KEY;
import static id.putraprima.skorbola.MainActivity.AWAYTEAM_KEY;
import static id.putraprima.skorbola.MainActivity.HOMEIMAGE_KEY;
import static id.putraprima.skorbola.MainActivity.HOMETEAM_KEY;

public class MatchActivity extends AppCompatActivity {
    public static final String HASIL_KEY = "hasil";
    public static final String HOMESCORE_KEY = "homeScore";
    public static final String AWAYSCORE_KEY = "awayScore";
    public static final String HOMESCORER_KEY = "homeScorer";
    public static final String AWAYSCORER_KEY = "awayScorer";

    private ImageView imageViewHome;
    private ImageView imageViewAway;
    private TextView homeTeam;
    private TextView awayTeam;
    private TextView scoreHome;
    private TextView scoreAway;
    private TextView scorerHome;
    private TextView scorerAway;
    private Uri uriHome;
    private Uri uriAway;

    int scorehome =0;
    int scoreaway =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        homeTeam = findViewById(R.id.txt_home);
        awayTeam = findViewById(R.id.txt_away);
        imageViewHome = findViewById(R.id.home_logo);
        imageViewAway = findViewById(R.id.away_logo);
        scoreHome = findViewById(R.id.score_home);
        scoreAway = findViewById(R.id.score_away);
        scorerHome = findViewById(R.id.txtScorer);
        scorerAway = findViewById(R.id.txtScorer);
        //1.Menampilkan detail match sesuai data dari main activity
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            homeTeam.setText(extras.getString(HOMETEAM_KEY));
            awayTeam.setText(extras.getString(AWAYTEAM_KEY));
            uriHome = Uri.parse(extras.getString(HOMEIMAGE_KEY));
            uriAway = Uri.parse(extras.getString(AWAYIMAGE_KEY));
            Bitmap bitmapHome = null;
            Bitmap bitmapAway = null;

            try {
                bitmapHome = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriHome);
                bitmapAway = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriAway);
            }catch (IOException e){
                e.printStackTrace();
            }
            imageViewHome.setImageBitmap(bitmapHome);
            imageViewAway.setImageBitmap(bitmapAway);
        }

        //TODO

        //2.Tombol add score menambahkan memindah activity ke scorerActivity dimana pada scorer activity di isikan nama pencetak gol
        //3.Dari activity scorer akan mengirim kembali ke activity matchactivity otomatis nama pencetak gol dan skor bertambah +1
        //4.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang beserta nama pencetak gol ke ResultActivity, jika seri di kirim text "Draw",
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                scorehome++;
                Intent intent = new Intent(this, ScorerActivity.class);
//                String returnString = data.getStringExtra("HOMESCORE_KEY");
                scoreHome.setText(String.valueOf(scorehome));
//                scorerHome.setText(returnString);
            }
        }
        else if (requestCode == 2){
            if(resultCode == RESULT_OK){
                scoreaway++;
                Intent intent = new Intent(this, ScorerActivity.class);
//                String returnString = data.getStringExtra("AWAYSCORE_KEY");
                scoreAway.setText(String.valueOf(scoreaway));
//                scorerAway.setText(returnString);
            }
        }
    }

    public void handleHomeScore(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, 1);
    }

    public void handleAwayScore(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent, 2);
    }

    public void handleCek(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        String hasil = null;
        if(scorehome > scoreaway){
            hasil = homeTeam.getText().toString() + " Menang";
        }
        else if(scoreaway > scorehome){
            hasil = awayTeam.getText().toString() + " Menang";
        }
        else{
            hasil = homeTeam.getText().toString() + " Draw dengan " + awayTeam.getText();
        }
        intent.putExtra(HASIL_KEY, hasil);
        startActivity(intent);
    }

}
