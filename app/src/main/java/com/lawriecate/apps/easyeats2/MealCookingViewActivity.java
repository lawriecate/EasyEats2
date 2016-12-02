package com.lawriecate.apps.easyeats2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MealCookingViewActivity extends AppCompatActivity implements SensorEventListener {
    TextView txtTimer;

    long startTime = 0;
    boolean counting = false;
    int onStep = 0;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean init;
    private static final float ERROR = (float) 7.0;
    private float x1, x2, x3;
    float diffX, diffY, diffZ;
    long lastUpdate = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            txtTimer.setText(String.format("%d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_cooking_view);
        final Meal meal = this.getIntent().getExtras().getParcelable("meal");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button fab = (Button) findViewById(R.id.button_control_timer);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle_timer();
            }
        });

        Button back = (Button) findViewById(R.id.button_back_step);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prev_step();
            }
        });

        Button next = (Button) findViewById(R.id.button_forward_step);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next_step();
            }
        });

        txtTimer = (TextView) findViewById(R.id.text_timer);
        TextView stepTxt = (TextView) findViewById(R.id.text_instruction);
        stepTxt.setText(meal.getInstructions().get(0));

        toggle_timer();


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);   //Crear instancia para Manejador de sensores
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  //Crear instancia para acelerÛmetro

        sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_GAME);

    }

    public void next_step() {
        final Meal meal = this.getIntent().getExtras().getParcelable("meal");
        if(onStep < meal.getInstructions().size()) {
            onStep++;
            TextView countTxt = (TextView) findViewById(R.id.text_step);
            countTxt.setText("Step " + (onStep+1));
            TextView stepTxt = (TextView) findViewById(R.id.text_instruction);
            stepTxt.setText(meal.getInstructions().get(onStep));
        }
    }

    public void prev_step() {
        final Meal meal = this.getIntent().getExtras().getParcelable("meal");
        if(onStep > -1) {
            onStep--;
            TextView countTxt = (TextView) findViewById(R.id.text_step);
            countTxt.setText("Step " + (onStep+1));
            TextView stepTxt = (TextView) findViewById(R.id.text_instruction);
            stepTxt.setText(meal.getInstructions().get(onStep));
        }
    }

    public void toggle_timer(){
        if (counting) {
            counting = false;
            timerHandler.removeCallbacks(timerRunnable);
        } else {
            counting = true;
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
        }
    }

    public void onShake()
    {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {    //Cada que valor de sensor sea modificado
        long curTime = System.currentTimeMillis();

        if ((curTime - lastUpdate) > 500) { //No permite shakes menores a 100 ms
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            float[] data = event.values;
            float x = data[0];  //Obtener valores en eje X
            float y = data[1];  //Obtener valores en eje Y
            float z = data[2];  //Obtener vaores en eje Z

            if (!init) { //Si variable init es falsa
                x1 = x; //x1 toma el valor de x
                x2 = y; //x2 toma el valor de y
                x3 = z; //x3 toma el valor de z
                init = true;    //Variable init es verdadera
            } else {
                diffX = Math.abs(x1 - x);   //Diferencia entre mediciÛn inmediatamente anterior de x y x actual
                diffY = Math.abs(x2 - y);   //Diferencia entre mediciÛn inmediatamente anterior de y y y actual
                diffZ = Math.abs(x3 - z);   //Diferencia entre mediciÛn inmediatamente anterior de z y z actual

                //Handling ACCELEROMETER Noise
                if (diffX < ERROR) {    //Si diferencia en valores de eje X es menor a 7
                    diffX = (float) 0.0;    //diffX es cero
                }
                if (diffY < ERROR) {        //Si diferencia en valores de eje Y es menor a 7
                    diffY = (float) 0.0;    //diffY es cero
                }
                if (diffZ < ERROR) {        //Si diferencia en valores de eje Z es menor a 7
                    diffZ = (float) 0.0;    //diffZ es cero
                }

                x1 = x;     //Almacenar valor actual de x en x1
                x2 = y;     //Almacenar valor actual de y en x2
                x3 = z;     //Almacenar valor actual de z en x3

                //Horizontal Shake Detected!
                if (diffX > diffY && diffX > diffZ) {
                    onShake();
                } else if (diffY > diffX && diffY > diffZ) {
                    onShake();
                } else if (diffZ > diffX && diffZ > diffY) {
                    onShake();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }


}
