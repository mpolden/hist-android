package no.hist.aitel.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GreetActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.greet);
        String name = getIntent().getStringExtra("name");
        TextView textView = (TextView) findViewById(R.id.greeting);
        String f = String.format(getResources().getString(R.string.greeting),
                name);
        textView.setText(Html.fromHtml(f));
        Button button = (Button) findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),
                        MyActivity.class));
            }
        });
    }
}