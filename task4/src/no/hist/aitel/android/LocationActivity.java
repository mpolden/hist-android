package no.hist.aitel.android;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.TableLayout;

public class LocationActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        XmlResourceParser parser = getResources().getXml(R.xml.varsel);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.location);
        WeatherParser.parse(parser, tableLayout);
    }
}
