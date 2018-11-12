package online.miguelzurita.eventbus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import online.miguelzurita.eventbus.events.ClickEvent;
import online.miguelzurita.eventbus.events.CustomMessageEvent;
import online.miguelzurita.eventbus.events.GoEvent;

@SuppressLint("DefaultLocale")
public class ChildActivity extends AppCompatActivity {

    private Button triggerEventButton;
    private EditText messageEditText;
    private Button button2;
    private Button button3;
    private Button button4;
    private EventBus insideEventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        insideEventBus = new EventBus();
        insideEventBus.register(this);
//        EventBus.getDefault().register(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messageEditText = (EditText) findViewById(R.id.editText);
        triggerEventButton = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        triggerEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userText = messageEditText.getText().toString();

                //enviar un evento de prueba con propiedades
                CustomMessageEvent event = new CustomMessageEvent();
                event.setCustomMessage(userText);
                EventBus.getDefault().post(event);

                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //enviar un evento de prueba con propiedades
                GoEvent goEvent = new GoEvent();
                goEvent.message1 = "GO 11";

                EventBus.getDefault().post(goEvent);
                finish();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                double random = Math.random();

                //enviar un evento de prueba con propiedades
                ClickEvent clickEvent = new ClickEvent();
                clickEvent.label = String.format("LABEL (%s)", String.valueOf(random));

                insideEventBus.post(clickEvent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if (insideEventBus.isRegistered(ChildActivity.this)) {
//                Elimina el suscriptor de la actividad actual
                    insideEventBus.unregister(ChildActivity.this);
                } else {
//                Agrega el suscriptor de la actividad actual
                    insideEventBus.register(ChildActivity.this);
                }
            }
        });

    }

    @Subscribe
    public void onClickButton(ClickEvent clickEvent) {
        button2.setText(clickEvent.label);
    }

}
