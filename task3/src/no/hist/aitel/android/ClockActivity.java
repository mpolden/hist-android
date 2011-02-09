package no.hist.aitel.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ClockActivity extends Activity {

    private View minute;
    private View hour;
    private Animation minuteAnimation;
    private Animation hourAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        minute = findViewById(R.id.minute);
        hour = findViewById(R.id.hour);
        minuteAnimation = AnimationUtils.loadAnimation(this, R.anim.minute);
        hourAnimation = AnimationUtils.loadAnimation(this, R.anim.hour);
        startClock();
    }

    private void startClock() {
        minute.startAnimation(minuteAnimation);
        hour.startAnimation(hourAnimation);
    }

    private void stopClock() {
        minute.clearAnimation();
        hour.clearAnimation();
    }

    private void restartClock() {
        stopClock();
        startClock();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, R.string.restart);
        menu.add(Menu.NONE, 2, Menu.NONE, R.string.stop);
        menu.add(Menu.NONE, 3, Menu.NONE, R.string.exit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1: {
                restartClock();
                return true;
            }
            case 2: {
                stopClock();
                return true;
            }
            case 3: {
                finish();
                return true;
            }
        }
        return false;
    }
}
