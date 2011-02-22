package no.hist.aitel.android;

import android.content.res.XmlResourceParser;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class WeatherParser {

    public static void parse(XmlResourceParser parser, TableLayout tab) {
        int eventType = -1;
        try {
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String element = parser.getName();
                    if ("location".equals(element)) {
                        parseLocation(parser, tab);
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

    private static void parseLocation(XmlResourceParser parser, TableLayout tab)
            throws XmlPullParserException, IOException {
        int eventType = -1;
        boolean done = false;
        while (!done) {
            String element = parser.getName();
            if (eventType == XmlResourceParser.START_TAG) {
                if ("name".equals(element)) {
                    addRow(tab, element, parser.nextText().trim());
                }
            } else if (eventType == XmlResourceParser.END_TAG) {
                if ("location".equals(element)) {
                    done = true;
                }
            }
            eventType = parser.next();
        }
    }

    private static void addRow(TableLayout tab, String column, String value) {
        TableRow row = new TableRow(tab.getContext());
        TextView columnView = new TextView(tab.getContext());
        columnView.setText(column);
        TextView valueView = new TextView(tab.getContext());
        valueView.setText(value);
        row.addView(columnView);
        row.addView(valueView);
    }
}
