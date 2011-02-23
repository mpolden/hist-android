package no.hist.aitel.android;

import android.content.res.XmlResourceParser;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class WeatherParser {

    public static void parse(XmlResourceParser parser, TableLayout tableLayout) {
        int eventType = XmlResourceParser.START_DOCUMENT;
        try {
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String element = parser.getName();
                    if ("location".equals(element) &&
                            tableLayout.getId() == R.id.location) {

                        parseLocation(parser, tableLayout);
                    } else if ("credit".equals(element)) {
                        parseCredit(parser, tableLayout);
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

    private static void parseCredit(XmlResourceParser parser,
                                    TableLayout tableLayout)
            throws XmlPullParserException, IOException {
        int eventType = -1;
        boolean done = false;
        while (!done) {
            String element = parser.getName();
            if (eventType == XmlResourceParser.START_TAG &&
                    "link".equals(element)) {
                String link = parser.getAttributeValue("link", "text");
                addRow(tableLayout, element, link);
            } else if (eventType == XmlResourceParser.END_TAG) {
                if ("country".equals(element)) {
                    done = true;
                }
            }
            eventType = parser.next();
        }
    }

    private static void parseLocation(XmlResourceParser parser,
                                      TableLayout tableLayout)
            throws XmlPullParserException, IOException {
        int eventType = -1;
        String element;
        boolean done = false;
        while (!done) {
            eventType = parser.nextTag();
            element = parser.getName();
            if (eventType == XmlResourceParser.START_TAG) {
                String value = parser.nextText();
                addRow(tableLayout, element, value);
            }
            if (eventType == XmlResourceParser.END_TAG) {
                if ("country".equals(element)) {
                    done = true;
                }
            }
        }
    }

    private static void addRow(TableLayout tableLayout, String column,
                               String value) {
        TableRow row = new TableRow(tableLayout.getContext());
        TextView columnView = new TextView(tableLayout.getContext());
        columnView.setText(column);
        TextView valueView = new TextView(tableLayout.getContext());
        valueView.setText(value);
        row.addView(columnView);
        row.addView(valueView);
        tableLayout.addView(row);
    }
}
