package no.hist.aitel.android;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.TableLayout;

public class WeatherDetailedActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed);
        XmlResourceParser parser = getResources().getXml(R.xml.varsel);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.detailed);
        WeatherParserFromHell.parse(parser, tableLayout);
    }
}