package no.hist.aitel.android;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class WeatherTabActivity extends TabActivity {

    private View locationTab;
    private View weatherTab;
    private View deatailedTab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        intent = new Intent().setClass(this, LocationActivity.class);
        spec = tabHost.newTabSpec("location").setIndicator("Location",
                res.getDrawable(R.drawable.ic_tab_location))
                .setContent(intent);
        tabHost.addTab(spec);
        intent = new Intent().setClass(this, WeatherActivity.class);
        spec = tabHost.newTabSpec("weather").setIndicator("Weather",
                res.getDrawable(R.drawable.ic_tab_weather))
                .setContent(intent);
        tabHost.addTab(spec);
        intent = new Intent().setClass(this, DetailedWeatherActivity.class);
        spec = tabHost.newTabSpec("detailed").setIndicator("Detailed",
                res.getDrawable(R.drawable.ic_tab_detailed))
                .setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);

    }

    /*public void parse() {
        XmlResourceParser parser = getResources().getXml(R.xml.varsel);
        int eventType = -1;
        try {
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String element = parser.getName();
                    if ("location".equals(element)) {
                        
                        parseLocation(parser, (TableLayout) locationTab);
                    } else if ("credit".equals(element)) {
                    } else if ("forecast".equals(element)) {
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            Log.e("XmlPullParserException", "XmlPullParserException", e);
        } catch (IOException e) {
            Log.e("IOException", "IOException", e);
        }
    }

    private void parseLocation(XmlResourceParser parser, TableLayout tab)
            throws XmlPullParserException, IOException {
        int eventType = -1;
        boolean done = false;
        while (!done) {
            String element = parser.getName();
            if (eventType == XmlResourceParser.START_TAG) {
                addRow(tab, element, parser.nextText().trim());
            } else if (eventType == XmlResourceParser.END_TAG) {
                if ("country".equals(element)) {
                    done = true;
                }
            }
            eventType = parser.next();
        }
    }

    private void addRow(TableLayout tab, String column, String value) {
        TableRow row = new TableRow(tab.getContext());
        TextView columnView = new TextView(tab.getContext());
        columnView.setText(column);
        TextView valueView = new TextView(tab.getContext());
        valueView.setText(value);
        row.addView(columnView);
        row.addView(valueView);
        tab.addView(row);
    }*/
}