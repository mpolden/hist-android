package no.hist.aitel.android;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class WeatherTabActivity extends TabActivity {

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
}