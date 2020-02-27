package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    public static final String HOMETEAM_KEY = "homeTeam";
    public static final String AWAYTEAM_KEY = "awayTeam";
    public static final String HOMEIMAGE_KEY = "homeImage";
    public static final String AWAYIMAGE_KEY = "awayImage";

    private EditText homeTeamInput;
    private EditText awayTeamInput;
    private ImageView homeImage;
    private ImageView awayImage;
    private Uri imageUriHome = null;
    private Uri imageUriAway = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO
        //Fitur Main Activity
        homeTeamInput = findViewById(R.id.home_team);
        awayTeamInput = findViewById(R.id.away_team);
        homeImage = findViewById(R.id.home_logo);
        awayImage = findViewById(R.id.away_logo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_CANCELED){
                return;
            }
        //3. Ganti Logo Home Team
            if(requestCode == 1){
                if(data != null){
                    try{
                        imageUriHome = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUriHome);
                        homeImage.setImageBitmap(bitmap);
                    }catch(IOException e){
                        Toast.makeText(this, "Can't Load Image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        //4. Ganti Logo Away Team
            if(requestCode == 2){
                if(data != null){
                    try{
                        imageUriAway = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUriAway);
                        awayImage.setImageBitmap(bitmap);
                    }catch (IOException e){
                        Toast.makeText(this, "Can't Load Image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
    }

    public void handleHomeImage(View view) {
        Intent intent  = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    public void handleAwayImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    public void handleNext(View view) {
        String homeTeam = homeTeamInput.getText().toString();
        String awayTeam = awayTeamInput.getText().toString();
        //1. Validasi Input Home Team
        if(homeTeamInput.length()==0){
            homeTeamInput.setError("Home Team Dilarang Kosong");
        }
        //2. Validasi Input Away Team
        else if(awayTeamInput.length()==0){
            awayTeamInput.setError("Away Team Dilarang Kosong");
        }
        else if(imageUriHome == null){
            Toast.makeText(this, "Home image tidak boleh kosong", Toast.LENGTH_SHORT).show();
            handleHomeImage(view);
        }
        else if(imageUriAway == null){
            Toast.makeText(this, "Away image tidak boleh kosong", Toast.LENGTH_SHORT).show();
            handleAwayImage(view);
        }
        else{
            //5. Next Button Pindah Ke MatchActivity
            Intent intent = new Intent(this, MatchActivity.class);
            intent.putExtra(HOMETEAM_KEY, homeTeam);
            intent.putExtra(AWAYTEAM_KEY, awayTeam);
            intent.putExtra(HOMEIMAGE_KEY, imageUriHome.toString());
            intent.putExtra(AWAYIMAGE_KEY, imageUriAway.toString());
            startActivity(intent);
        }
    }
}
