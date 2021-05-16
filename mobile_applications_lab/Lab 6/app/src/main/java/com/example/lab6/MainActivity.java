package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewManager;
import android.view.Window;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int currentScreenBrightness;
    int currentVolume;

    private ContentResolver cResolver;
    private Window window;

    AudioManager mAudioManager;

    EditText mName, mEmail, mContact;
    Button mSubmit, mView;

    Boolean isStored = Boolean.FALSE;

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Settings.System.canWrite(getApplicationContext())) {

            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
            Toast.makeText(getApplicationContext(), "Please, allow system settings for automatic logout ", Toast.LENGTH_LONG).show();
            startActivityForResult(intent, 200);
        }

        cResolver = getContentResolver();

        window = getWindow();

        try
        {
            // To handle the auto
            Settings.System.putInt(cResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            //Get the current system brightness
            currentScreenBrightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
        }
        catch (Settings.SettingNotFoundException e)
        {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }

        mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        mName = (EditText) findViewById(R.id.name);
        mEmail = (EditText) findViewById(R.id.email);
        mContact = (EditText) findViewById(R.id.phone);
        mSubmit = (Button) findViewById(R.id.submit);
        mView = (Button) findViewById(R.id.view);
        mSharedPreferences = getSharedPreferences(((Lab6Application)this.getApplication()).getSPKey(), Context.MODE_PRIVATE);
        String[] keys = ((Lab6Application)this.getApplication()).getSPValueKeys();

        getSPreferences();
        checkEmptyVal();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                String contact = mContact.getText().toString();

                if (name.equals("") || email.equals("") || contact.equals("")) {

                } else {
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(keys[0], name);
                    editor.putString(keys[1], email);
                    editor.putString(keys[2], contact);
                    editor.commit();

                    isStored = Boolean.TRUE;
                    Toast.makeText(getApplicationContext(), "Saved in Shared Preferences", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStored != Boolean.TRUE) {

                } else {
                    Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                String url = "https://developer.android.com/";
                Intent helpIntent = new Intent(Intent.ACTION_VIEW);
                helpIntent.setData(Uri.parse(url));
                startActivity(helpIntent);
                return true;

            case R.id.settings:
                Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(settingsIntent);
                return true;

            case R.id.exit:
                finishAndRemoveTask();
                return true;

            case R.id.clear_sp:
                clearSharedPreferences();
                getSPreferences();
                Toast.makeText(getApplicationContext(), "Shared Preferences Cleared", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.bluetooth:
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                if (mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.disable();
                    Toast.makeText(getApplicationContext(), "Bluetooth Disabled", Toast.LENGTH_SHORT).show();
                }
                else {
                    mBluetoothAdapter.enable();
                    Toast.makeText(getApplicationContext(), "Bluetooth Enabled", Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.b_inc:
                setBrightness(currentScreenBrightness + 64);
                Toast.makeText(getApplicationContext(), "Brightness Increased", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.b_dec:
                setBrightness(currentScreenBrightness - 64);
                Toast.makeText(getApplicationContext(), "Brightness Decreased", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.v_inc:
                setVolume(currentVolume + 25);
                Toast.makeText(getApplicationContext(), "Volume Increased", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.v_dec:
                setVolume(currentVolume - 25);
                Toast.makeText(getApplicationContext(), "Volume Decreased", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.orientation:
                if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                else
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setBrightness(int brightness) {
        if(brightness < 0)
            brightness = 0;
        else if(brightness > 255)
            brightness = 255;

        currentScreenBrightness = brightness;

        Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / (float)255;
        window.setAttributes(lp);
    }

    public void setVolume(int volume) {
        if(volume < 0)
            volume = 0;
        else if(volume > 100)
            volume = 100;

        currentVolume = volume;

        mAudioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                (int) mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * volume / 100,
                0);
    }

    private void getSPreferences() {
        String[] keys = ((Lab6Application)this.getApplication()).getSPValueKeys();
        mName.setText(mSharedPreferences.getString(keys[0], ""));
        mEmail.setText(mSharedPreferences.getString(keys[1], ""));
        mContact.setText(mSharedPreferences.getString(keys[2], ""));
    }

    private void clearSharedPreferences() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void checkEmptyVal() {
        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String contact = mContact.getText().toString();
        if (!(name.equals("") || email.equals("") || contact.equals(""))) {
            isStored = Boolean.TRUE;
            Toast.makeText(getApplicationContext(), "Loading values from Stored Preferences", Toast.LENGTH_SHORT).show();
        }
    }


}