package com.example.passage.tab;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.passage.R;

import com.example.passage.article.ArticalFragment;
import com.example.passage.shelf.ShelfFragment;
import com.example.passage.voice.VoiceFragment;

public class FragmentGenerator {
    public static final String[] mTabTitles = {"文章", "声音", "书架"};

    public static Fragment[] getFragments() {
        Fragment[] fragments = new Fragment[3];
        fragments[0] = ArticalFragment.newInstance();
        fragments[1] = VoiceFragment.newInstance();
        fragments[2] = ShelfFragment.newInstance();
        return fragments;
    }

    public static View getTabView(Context context, int positon) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        TextView text = (TextView) view.findViewById(R.id.tab_text);
        text.setText(mTabTitles[positon]);
        return view;
    }
}
