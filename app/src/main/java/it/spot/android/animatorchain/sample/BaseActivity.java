package it.spot.android.animatorchain.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * @author a.rinaldi
 */
public class BaseActivity extends AppCompatActivity {

    // region Activity life cycle

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.base_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_simple_chain:
                this.startActivity(new Intent(this, SimpleChainActivity.class));
                this.finish();
                return true;
            case R.id.menu_circular_chain:
                this.startActivity(new Intent(this, CircularChainActivity.class));
                this.finish();
                return true;
            case R.id.menu_dynamic_chain:
                this.startActivity(new Intent(this, DynamicChainActivity.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // endregion

}
