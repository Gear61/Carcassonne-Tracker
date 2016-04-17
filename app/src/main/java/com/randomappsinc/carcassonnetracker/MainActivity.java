package com.randomappsinc.carcassonnetracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.rey.material.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.search_input) EditText searchInput;
    @Bind(R.id.empties_toggle) CheckBox emptiesToggle;
    @Bind(R.id.no_tiles) View noTiles;
    @Bind(R.id.tiles) ListView tilesList;

    private TilesAdapter tilesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tilesAdapter = new TilesAdapter(this, noTiles);
        tilesList.setAdapter(tilesAdapter);
    }

    @OnTextChanged(value = R.id.search_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable input) {
        tilesAdapter.refreshList(emptiesToggle.isChecked(), input.toString().trim());
    }

    @OnClick(R.id.clear_search)
    public void clearSearch() {
        searchInput.setText("");
    }

    @OnClick(R.id.empties_toggle)
    public void toggleFavorites() {
        tilesAdapter.refreshList(emptiesToggle.isChecked(), searchInput.getText().toString().trim());
    }
}
