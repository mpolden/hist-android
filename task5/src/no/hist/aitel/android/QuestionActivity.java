package no.hist.aitel.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class QuestionActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        readQuestions();
    }

    private void readQuestions() {
        InputStream iFile = getResources().openRawResource(R.raw.questions);
        BufferedReader isr = new BufferedReader(new InputStreamReader(iFile));
        String line;
        try {
            while ((line = isr.readLine()) != null) {
                Log.d("question", line);
            }
        } catch (IOException e) {

        }
        /*YamlReader reader = new YamlReader(new InputStreamReader(iFile));
        try {
            List<Question> questions = reader.read(List.class);
            for (Question q : questions) {
                Log.d("question", q.getQuestion());
            }
        } catch (YamlException e) {

        }*/


    }

}
