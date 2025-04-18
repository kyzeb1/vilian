package com.brilliant.cr.custom.main.tab;


import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.brilliant.cr.R;
import com.brilliant.cr.custom.util.LinearLayoutManagerWrapper;
import com.brilliant.cr.custom.util.Utils;
import com.nvidia.devtech.NvEventQueueActivity;

import java.util.ArrayList;
import java.util.List;

public class Tab {

    private final ConstraintLayout mInputLayout;

    private final EditText mSearch;

    private final ImageView mSearchImg;
    private final ImageView mClearImg;

    private final TextView mOnline;

    private final RecyclerView mPlayersList;

    private final List<PlayerData> mPlayerData;

    private TabAdapter mTabAdapter;

    public Tab(Activity activity) {
        this.mInputLayout = activity.findViewById(R.id.hassle_tab_main);

        this.mSearch = activity.findViewById(R.id.search_view);
        this.mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                mTabAdapter.getFilter().filter(editable);

                setVisibleIconInSearchView(editable.toString());
            }
        });

        this.mSearchImg = activity.findViewById(R.id.icon_search_view);
        this.mClearImg = activity.findViewById(R.id.icon_clear_search_text);
        this.mClearImg.setOnClickListener(view -> {
            this.mSearch.setText("");
            setVisibleIconInSearchView("");
        });

        this.mOnline = activity.findViewById(R.id.players_list_value_players);

        this.mPlayersList = activity.findViewById(R.id.players_list);

        this.mPlayerData = new ArrayList<>();

        this.mTabAdapter = new TabAdapter(this.mPlayerData);

        Button close = activity.findViewById(R.id.players_list_close);
        close.setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            NvEventQueueActivity.getInstance().showHud(true);

            Utils.HideLayout(this.mInputLayout, true);
            NvEventQueueActivity.getInstance().onTabClose();

            this.mPlayersList.getRecycledViewPool().clear();
            TabAdapter adapter = (TabAdapter) this.mPlayersList.getAdapter();
            if (adapter != null)
                adapter.notifyDataSetChanged();

            this.mPlayerData.clear();
        });

        Utils.HideLayout(this.mInputLayout, false);
    }

    public void clearStat() {
        this.mPlayerData.clear();
    }

    public void setStat(Integer id, String name, Integer score, Integer ping) {
        this.mPlayerData.add(new PlayerData(id, name, score, ping));
    }

    public void show(boolean isAnim) {
        NvEventQueueActivity.getInstance().hideHud(true);

        this.mOnline.setText(this.mPlayerData.size() + "/1000");

        this.mTabAdapter = new TabAdapter(this.mPlayerData);

        this.mPlayersList.setLayoutManager(new LinearLayoutManagerWrapper(NvEventQueueActivity.getInstance()));
        this.mPlayersList.setAdapter(this.mTabAdapter);

        this.mSearch.setText("");
        setVisibleIconInSearchView("");

        Utils.ShowLayout(this.mInputLayout, isAnim);
    }

    private void setVisibleIconInSearchView(String str) {
        if (str.isEmpty()) {
            this.mSearchImg.setVisibility(View.VISIBLE);
            this.mClearImg.setVisibility(View.INVISIBLE);
        } else {
            this.mSearchImg.setVisibility(View.INVISIBLE);
            this.mClearImg.setVisibility(View.VISIBLE);
        }
    }

    public ConstraintLayout getInputLayout() {
        return mInputLayout;
    }

}
