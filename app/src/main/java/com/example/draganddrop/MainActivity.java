package com.example.draganddrop;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Activity {

    private final String TAG = getClass().getSimpleName();
    private ListView listView;
    private Button btn;
    private LinearLayout llNowPlaying;
    private ViewGroup _root;
    private int _xDelta;
    private int _yDelta;

    private int viewHeight, viewWidth;
    private int rootHeight;
    private int nowPlayingHeight;

//    private boolean

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _root = (ViewGroup)findViewById(R.id.root);

        listView = (ListView) findViewById(R.id.listView);
        llNowPlaying = (LinearLayout) findViewById(R.id.ll_now_playing);
        btn = (Button)findViewById(R.id.button);

//        btn.setOnTouchListener(this); // TODO
        new DragBottomMenuControl(btn, _root, llNowPlaying);

        // ListView
        final String[] catnames = new String[] {
                "Рыжик", "Барсик", "Мурзик", "Мурка", "Васька",
                "Томасина", "Кристина", "Пушок", "Дымка", "Кузя",
                "Китти", "Масяня", "Симба"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1, catnames);
        listView.setAdapter(adapter);


    }
}
