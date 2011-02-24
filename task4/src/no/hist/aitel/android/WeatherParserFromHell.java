package no.hist.aitel.android;

import android.content.res.XmlResourceParser;
import android.text.Html;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class WeatherParserFromHell {

    public static void parse(XmlResourceParser parser, TableLayout tableLayout) {
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String element = parser.getName();
                    if ("location".equals(element) &&
                            tableLayout.getId() == R.id.location) {

                        parseLocation(parser, tableLayout);
                    } else if ("credit".equals(element) &&
                            tableLayout.getId() == R.id.location) {
                        parseCredit(parser, tableLayout);
                    } else if ("text".equals(element) &&
                            tableLayout.getId() == R.id.text) {
                        parseWeatherText(parser, tableLayout);
                    } else if ("tabular".equals(element) &&
                            tableLayout.getId() == R.id.detailed) {
                        parseWeatherDetailed(parser, tableLayout);
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            Log.e("Exception", "XmlPullParserException", e);
        } catch (IOException e) {
            Log.e("Exception", "IOException", e);
        }
    }

    private static void parseWeatherDetailed(XmlResourceParser parser,
                                             TableLayout tableLayout)
            throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            String element = parser.getName();
            if (eventType == XmlResourceParser.START_TAG) {

                String value;
                StringBuilder out = new StringBuilder();
                if ("time".equals(element)) {
                    String from = parser.getAttributeValue(null, "from");
                    String to = parser.getAttributeValue(null, "to");
                    if (from != null && to != null) {
                        value = String.format("<b>%s til %s</b>", from, to);
                        addLine(tableLayout, value);

                        while (eventType != XmlResourceParser.END_DOCUMENT) {
                            element = parser.getName();
                            if (eventType == XmlResourceParser.START_TAG) {

                                if ("symbol".equals(element)) {
                                    out.append(String.format("Type: %s", parser.
                                            getAttributeValue(null,
                                                    "name")));
                                } else if ("windDirection".equals(element)) {
                                    out.append(
                                            String.format(", vindretning: %s",
                                                    parser.getAttributeValue(null,
                                                            "name")));
                                } else if ("windSpeed".equals(element)) {
                                    out.append(
                                            String.format(
                                                    ", vindhastighet: %s m/s",
                                                    parser.getAttributeValue(null,
                                                            "mps")));
                                } else if ("temperature".equals(element)) {
                                    out.append(
                                            String.format(
                                                    ", temperatur: %s %s",
                                                    parser.getAttributeValue(null,
                                                            "value"),
                                                    parser.getAttributeValue(null,
                                                            "unit")
                                            ));
                                }
                            } else if (eventType == XmlResourceParser.END_TAG) {
                                if ("pressure".equals(element)) {
                                    addLine(tableLayout, out.toString());
                                    out.delete(0, out.length());
                                    break;
                                }
                            }
                            eventType = parser.next();
                        }
                    }
                }
            } else if (eventType == XmlResourceParser.END_TAG &&
                    "tabular".equals(element)) {
                break;
            }
            eventType = parser.next();
        }
    }

    private static void parseWeatherText(XmlResourceParser parser,
                                         TableLayout tableLayout)
            throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            String element = parser.getName();
            if (eventType == XmlResourceParser.START_TAG) {

                String column, value;
                if ("time".equals(element)) {
                    String from = parser.getAttributeValue(null, "from");
                    String to = parser.getAttributeValue(null, "to");
                    if (from != null && to != null) {
                        column = String.format("<b>%s til %s</b>", from, to);
                        addLine(tableLayout, column);
                        while (eventType != XmlResourceParser.END_DOCUMENT) {
                            element = parser.getName();
                            if (eventType == XmlResourceParser.START_TAG
                                    && "body".equals(element)) {
                                value = parser.nextText();
                                addLine(tableLayout, value);
                                break;
                            }
                            eventType = parser.next();
                        }
                    }
                }
            } else if (eventType == XmlResourceParser.END_TAG &&
                    "text".equals(element)) {
                break;
            }
            eventType = parser.next();
        }
    }


    private static void parseCredit(XmlResourceParser parser,
                                    TableLayout tableLayout)
            throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            String element = parser.getName();
            if (eventType == XmlResourceParser.START_TAG &&
                    "link".equals(element)) {
                String text = parser.getAttributeValue(null, "text");
                if (text != null && text.length() > 0) {
                    addLine(tableLayout, text);
                }
            } else if (eventType == XmlResourceParser.END_TAG &&
                    "link".equals(element)) {
                break;
            }
            eventType = parser.next();
        }
    }

    private static void parseLocation(XmlResourceParser parser,
                                      TableLayout tableLayout)
            throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            String element = parser.getName();
            if (eventType == XmlResourceParser.START_TAG) {

                if ("name".equals(element) || "type".equals(element)
                        || "country".equals(element)) {

                    addLine(tableLayout, parser.nextText());
                }
            } else if (eventType == XmlResourceParser.END_TAG &&
                    "location".equals(element)) {
                break;
            }
            eventType = parser.next();
        }
    }

    private static void addLine(TableLayout tableLayout, String value) {
        TableRow row = new TableRow(tableLayout.getContext());
        TextView textView = new TextView(tableLayout.getContext());
        textView.setText(Html.fromHtml(value));
        textView.setSingleLine(false);
        row.addView(textView);
        tableLayout.addView(row);
    }
}
