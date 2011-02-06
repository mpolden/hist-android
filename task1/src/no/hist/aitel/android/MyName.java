package no.hist.aitel.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MyName extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, R.string.show_first_name);
        menu.add(Menu.NONE, 2, Menu.NONE, R.string.show_last_name);
        menu.add(Menu.NONE, 3, Menu.NONE, R.string.show_full_name);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1: {
                Toast.makeText(getApplicationContext(), R.string.first_name,
                        Toast.LENGTH_SHORT).show();
                return true;
            }
            case 2: {
                Toast.makeText(getApplicationContext(), R.string.last_name,
                        Toast.LENGTH_SHORT).show();
                return true;
            }
            case 3: {
                Toast.makeText(getApplicationContext(),
                        String.format("%s %s",
                                getResources().getString(R.string.first_name),
                                getResources().getString(R.string.last_name)),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }
}
