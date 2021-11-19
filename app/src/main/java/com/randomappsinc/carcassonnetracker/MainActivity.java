package com.randomappsinc.carcassonnetracker;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.rey.material.widget.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.search_input) EditText searchInput;
    @BindView(R.id.empties_toggle) CheckBox emptiesToggle;
    @BindView(R.id.no_tiles) View noTiles;
    @BindView(R.id.tiles) ListView tilesList;

    private TilesAdapter tilesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tilesAdapter = new TilesAdapter(noTiles);
        tilesList.setAdapter(tilesAdapter);
        tilesList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                UIUtils.hideKeyboard(MainActivity.this);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {}
        });
    }

    @OnTextChanged(value = R.id.search_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(Editable input) {
        tilesAdapter.refreshList(emptiesToggle.isChecked(), input.toString().trim());
    }

    @OnClick(R.id.clear_search)
    public void clearSearch() {
        UIUtils.hideKeyboard(this);
        searchInput.setText("");
    }

    @OnClick(R.id.empties_toggle)
    public void toggleFavorites() {
        tilesAdapter.refreshList(emptiesToggle.isChecked(), searchInput.getText().toString().trim());
    }

    @OnItemClick(R.id.tiles)
    public void onTileClicked(int position) {
        Tile tile = tilesAdapter.getItem(position);
        new MaterialDialog.Builder(this)
                .title(R.string.tile_info)
                .content(Html.fromHtml(tile.getInfoBlurb(this)))
                .positiveText(android.R.string.yes)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.reset_tiles).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_refresh)
                        .colorRes(R.color.white)
                        .actionBarSize());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reset_tiles) {
            tilesAdapter.resetTiles();
            tilesAdapter.refreshList(emptiesToggle.isChecked(), searchInput.getText().toString().trim());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
