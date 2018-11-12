package online.miguelzurita.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import online.miguelzurita.eventbus.events.CustomMessageEvent;
import online.miguelzurita.eventbus.events.GoEvent;

public class MainActivity extends AppCompatActivity {

    private EditText resultsEditText;
    private Button launchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        resultsEditText = (EditText) findViewById(R.id.resultsEditText);
        launchButton = (Button) findViewById(R.id.launchButton);

        //Ir a la segunda actividad
        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChildActivity.class);
                startActivity(intent);
            }
        });
    }

    @Subscribe
    public void onEvent(CustomMessageEvent event) {
        Log.d("App", "Event fired " + event.getCustomMessage());
        resultsEditText.setText(event.getCustomMessage());
    }

    @Subscribe
    public void onGoEvent(GoEvent goEvent) {
        if (!goEvent.message1.isEmpty()) {
            resultsEditText.setText(goEvent.message1);
        }

        if (!goEvent.message2.isEmpty()) {
            resultsEditText.setText(goEvent.message2);
        }

    }
}
