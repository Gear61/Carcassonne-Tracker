package com.randomappsinc.carcassonnetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexanderchiou on 4/17/16.
 */
public class TilesAdapter extends BaseAdapter {

    private View noContent;
    private List<Tile> tiles;
    private boolean ignoreEmpties;

    public TilesAdapter(View noContent) {
        this.noContent = noContent;
        this.tiles = TileServer.get().initialize();
        setNoContent();
    }

    public void refreshList(boolean ignoreEmpties, String searchTerm) {
        this.ignoreEmpties = ignoreEmpties;
        tiles = TileServer.get().getFilteredTiles(ignoreEmpties, searchTerm);
        refreshUI();
    }

    public void setNoContent() {
        if (tiles.isEmpty()) {
            noContent.setVisibility(View.VISIBLE);
        } else {
            noContent.setVisibility(View.GONE);
        }
    }

    public void refreshUI() {
        notifyDataSetChanged();
        setNoContent();
    }

    public void resetTiles() {
        tiles = TileServer.get().initialize();
    }

    @Override
    public int getCount() {
        return tiles.size();
    }

    @Override
    public Tile getItem(int position) {
        return tiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class TileViewHolder {
        @BindView(R.id.tile_image) ImageView tileImage;
        @BindView(R.id.count) TextView numLeft;

        private int position;

        public TileViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void loadTile(int position) {
            this.position = position;
            Tile tile = getItem(position);
            tileImage.setImageResource(tile.getResourceId());
            numLeft.setText(String.valueOf(tile.getNumRemaining()));
        }

        @OnClick(R.id.count_increment)
        public void increaseCount() {
            getItem(position).increaseNumRemaining();
            refreshUI();
        }

        @OnClick(R.id.count_decrement)
        public void decreaseCount() {
            Tile tile = getItem(position);
            tile.decreaseNumRemaining();
            if (ignoreEmpties && tile.getNumRemaining() <= 0) {
                tiles.remove(position);
            }
            refreshUI();
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TileViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.tile_cell, parent, false);
            holder = new TileViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (TileViewHolder) view.getTag();
        }
        holder.loadTile(position);
        return view;
    }
}
