package codeman.ifco.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import codeman.ifco.R;

public class SplashActivity extends Activity {

    private static String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        launchMainPage();
    }

    private void launchMainPage() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException exception) {
            Log.e(TAG, exception.toString());
        } finally {
            Intent mainPage = new Intent(this, MainActivity.class);
            startActivity(mainPage);
            finish();
        }
    }

    @Override
    public void onBackPressed() {}
}
