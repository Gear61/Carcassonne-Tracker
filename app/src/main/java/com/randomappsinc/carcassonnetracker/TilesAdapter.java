package com.randomappsinc.carcassonnetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 4/17/16.
 */
public class TilesAdapter extends BaseAdapter {
    private Context context;
    private View noContent;
    private List<Tile> tiles;

    public TilesAdapter(Context context, View noContent) {
        this.context = context;
        this.noContent = noContent;
        refreshList(false, "");
    }

    public void refreshList(boolean ignoreEmpties, String searchTerm) {
        if (!ignoreEmpties && searchTerm.isEmpty()) {
            tiles = TileServer.get().getTileList();
        } else {
            tiles = TileServer.get().getFilteredTiles(ignoreEmpties, searchTerm);
        }
        notifyDataSetChanged();
        setNoContent();
    }

    public void setNoContent() {
        if (tiles.isEmpty()) {
            noContent.setVisibility(View.VISIBLE);
        } else {
            noContent.setVisibility(View.GONE);
        }
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
        @Bind(R.id.tile_image) ImageView tileImage;
        @Bind(R.id.count) TextView numLeft;

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
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TileViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
