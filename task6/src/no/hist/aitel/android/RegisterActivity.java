package no.hist.aitel.android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import no.hist.aitel.android.utils.FmtUtil;
import no.hist.aitel.android.utils.HttpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RegisterActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Properties properties;
    private String name;
    private String number;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        readProperties();
        final Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editName = (EditText) findViewById(R.id.name);
                EditText editNumber = (EditText) findViewById(R.id.number);

                name = null;
                number = null;

                Toast toast = Toast.makeText(getApplicationContext(), R.string.processing, Toast.LENGTH_SHORT);
                toast.show();

                if (FmtUtil.isAlphaNumeric(editName.getText().toString()) &&
                        FmtUtil.isNumeric(editNumber.getText().toString())) {
                    name = editName.getText().toString();
                    number = editNumber.getText().toString();
                    new RegisterTask().execute(properties.getProperty("registerUrl"));
                } else {
                    toast.setText(R.string.error);
                    toast.show();
                }
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

    private class RegisterTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {
            String url = String.format(urls[0], FmtUtil.encodeUrl(name), FmtUtil.encodeUrl(number));
            return HttpUtil.getBody(url);
        }


        @Override
        protected void onPostExecute(String body) {
            if (body != null && properties.get("okText").equals(body.trim())) {
                Intent intent = new Intent(getApplicationContext(), GuessNumberActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("number", number);
                intent.putExtra("info", body.trim());
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.gibberish, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

}
