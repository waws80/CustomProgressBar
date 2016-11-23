package thanatos.customprogressbar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "thanatos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CustomProgressBar customProgressBar= (CustomProgressBar) findViewById(R.id.customProgressBar);
        SeekBar seekBar= (SeekBar) findViewById(R.id.seekbar);
        SeekBar seekBar_i= (SeekBar) findViewById(R.id.seekbar_interval);
        SeekBar seekBar_w= (SeekBar) findViewById(R.id.seekbar_width);
        RadioGroup rg= (RadioGroup) findViewById(R.id.rg);
        RadioButton bt= (RadioButton) findViewById(R.id.nomal);
        RadioButton bt1= (RadioButton) findViewById(R.id.interval);
        if (customProgressBar.getProgressType()== CustomProgressBar.BarType.DEFAULT){
            bt.setChecked(true);
        }else if (customProgressBar.getProgressType()== CustomProgressBar.BarType.INTERVAL){
            bt1.setChecked(true);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.nomal:
                        customProgressBar.setProgressType(CustomProgressBar.BarType.DEFAULT);
                    break;
                    case R.id.interval:
                        customProgressBar.setProgressType(CustomProgressBar.BarType.INTERVAL);
                        break;

                }
            }
        });
        customProgressBar.setCirColor(Color.RED);
        customProgressBar.setCirStokeWidth(20);
        seekBar.setProgress(0);
        customProgressBar.setProgress(0);
        Log.w(TAG, "onCreate: 初始化-----" );
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                customProgressBar.setProgress(i);
                Log.w(TAG, "onProgressChanged: "+i );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar_i.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                customProgressBar.setArcType_interval_interval(i/10);
                Log.w(TAG, "onProgressChanged: "+i );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar_w.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                customProgressBar.setArcType_interval_width(i/10);
                Log.w(TAG, "onProgressChanged: "+i );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
