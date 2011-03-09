package no.hist.aitel.android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import no.hist.aitel.android.utils.FmtUtil;
import no.hist.aitel.android.utils.HttpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GuessNumberActivity extends Activity {

    private static final String TAG = GuessNumberActivity.class.getSimpleName();

    private Properties properties;
    private String number;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess);
        setTitle(R.string.app_name);
        TextView greeting = (TextView) findViewById(R.id.greet);
        greeting.setText(String.format(getResources().getString(R.string.greet), this.getIntent().getExtras().
                get("name")));
        TextView info = (TextView) findViewById(R.id.info);
        info.setText(this.getIntent().getExtras().get("info").toString());
        readProperties();
        addButtonHandlers();
    }

    private void addButtonHandlers() {
        final Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editNumber = (EditText) findViewById(R.id.guess);
                number = null;
                if (FmtUtil.isNumeric(editNumber.getText().toString())) {
                    TextView result = (TextView) findViewById(R.id.result);
                    result.setText(R.string.processing);
                    number = editNumber.getText().toString();
                    new GuessNumberTask().execute(properties.getProperty("playUrl"));
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.error_number, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        final Button restart = (Button) findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void readProperties() {
        try {
            InputStream inputStream = getAssets().open("url.properties");
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            Log.e(TAG, "Could not read properties file", e);
        }
    }

    private class GuessNumberTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {
            String url = String.format(urls[0], FmtUtil.encodeUrl(number));
            return HttpUtil.getBody(url);
        }


        @Override
        protected void onPostExecute(String body) {
            if (body != null && body.length() > 0) {
                TextView textView = (TextView) findViewById(R.id.result);
                textView.setText(body.trim());
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.gibberish, Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }

}