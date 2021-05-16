package com.example.colours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<CardView> colours;
    List<TextView> words;
    List<String> matchedWords;

    Button checkButton;

    String colourSet[] = {"#DC143C", "#FFA500", "#FFFF00", "#228B22", "#00BFFF", "#9400D3", "#FF69B4", "#00FFFF", "#00FF00", "#A0522D", "#808080", "#000000"};
    String wordSet[] = {"RED", "ORANGE", "YELLOW", "GREEN", "BLUE", "VIOLET", "PINK", "CYAN", "LIME", "BROWN", "GREY", "BLACK"};

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = this.getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        createNotificationChannel();

        setColours();

        for(int i=0; i<6; i++) {
            TextView word = words.get(i);
            word.setTag(String.valueOf(word.getId()));

            word.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                        v.startDrag(data, shadowBuilder, v, 0);
                        v.setVisibility(View.INVISIBLE);
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            word.setOnDragListener(dragListener);
            colours.get(i).setOnDragListener(dragListener);
        }

        checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = true;
                for(int i=0; i<6; i++) {
                    TextView colorText = (TextView) colours.get(i).getChildAt(0);
                    if(colorText == null) {
                        Toast.makeText(v.getContext(), "Fill All Boxes.", Toast.LENGTH_LONG).show();
                        return;
                    } else if(!(colorText.getText().toString() == matchedWords.get(i))) {
                        result = false;
                    }
                }

                editor.putBoolean("third", sp.getBoolean("second", false));
                editor.putBoolean("second", sp.getBoolean("first", false));
                editor.putBoolean("first", result);
                editor.apply();

                String textTitle;
                if(result) {
                    Toast.makeText(v.getContext(), "Correct Response.", Toast.LENGTH_LONG).show();
                    textTitle = "Congratulations, you won!";
                } else {
                    Toast.makeText(v.getContext(), "Incorrect Response.", Toast.LENGTH_LONG).show();
                    textTitle = "You lost, better luck next time";
                }

                Intent scoreIntent = new Intent(v.getContext(), ScoreActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(v.getContext(), 0, scoreIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(v.getContext(), "score")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(textTitle)
                        .setContentText("Click to view your previous results.")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(v.getContext());
                notificationManager.notify(1, builder.build());

                recreate();
            }
        });
    }

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            View view = (View) event.getLocalState();;
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if((v instanceof CardView && ((CardView) v).getChildCount() == 0) || (v instanceof LinearLayout && ((LinearLayout) v).getChildCount() < 3))
                        return true;
                    return false;

                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_LOCATION:
                case DragEvent.ACTION_DRAG_EXITED:
                    return true;

                case DragEvent.ACTION_DROP:
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    if(v instanceof CardView)
                        ((CardView) v).addView(view);
                    else if(v instanceof LinearLayout)
                        ((LinearLayout) v).addView(view);
                    v.invalidate();
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    view.setVisibility(View.VISIBLE);
                    v.invalidate();
                    return true;

                default:
                    break;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                recreate();
                return true;
            case R.id.exit:
                exitApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setColours() {
        List<Integer> choices = new ArrayList<>();
        for(int i=0; i<colourSet.length; i++) {
            choices.add(i);
        }

        colours = new ArrayList<>();
        words = new ArrayList<>();
        matchedWords = new ArrayList<>();

        for(int i=0; i<6; i++) {
            int cId = getResources().getIdentifier("c" + (i+1), "id", getPackageName());
            int tId = getResources().getIdentifier("t" + (i+1), "id", getPackageName());
            colours.add(findViewById(cId));
            words.add(findViewById(tId));
        }

        Random random = new Random();

        for(int i=0; i<6; i++) {
            int randNum = choices.remove(random.nextInt(choices.size()));
            colours.get(i).setCardBackgroundColor(Color.parseColor(colourSet[randNum]));
            matchedWords.add(wordSet[randNum]);
        }

        choices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        for(int i=0; i<6; i++) {
            int randNum = choices.remove(random.nextInt(choices.size()));

            TextView word = words.get(i);
            word.setText(matchedWords.get(randNum - 1));
        }
    }

    private void exitApp() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory( Intent.CATEGORY_HOME );
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "scoreNotification";
            String description = "";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("score", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
