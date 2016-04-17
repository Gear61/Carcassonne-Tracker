package com.randomappsinc.carcassonnetracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by alexanderchiou on 4/16/16.
 */
public class TileServer {
    private static TileServer instance;

    public static TileServer get() {
        if (instance == null) {
            instance = new TileServer();
        }
        return instance;
    }

    private List<Tile> tiles;
    private List<Tile> currentTiles;

    private TileServer () {
        tiles = new ArrayList<>();

        tiles.add(new Tile("Single monastery",
                R.drawable.single_monastery, 4));
        tiles.add(new Tile("Single monastery with road",
                R.drawable.monastery_r, 2));
        tiles.add(new Tile("Pure castle, 4 sides",
                R.drawable.pure_castle, 1));
        tiles.add(new Tile("3 edge castle",
                R.drawable.three_edge_castle, 3));
        tiles.add(new Tile("3 edge castle with shield",
                R.drawable.three_edge_castles, 1));

        tiles.add(new Tile("3 edge castle with road",
                R.drawable.three_edge_castler, 1));
        tiles.add(new Tile("3 edge castle with road and shield",
                R.drawable.three_edge_castlesr, 2));
        tiles.add(new Tile("Half castle",
                R.drawable.half_castle, 3));
        tiles.add(new Tile("Half castle with shield",
                R.drawable.half_castle_shield, 2));
        tiles.add(new Tile("Half castle with curvy road",
                R.drawable.half_castle_curve, 3));

        tiles.add(new Tile("Half castle with shield and curvy road",
                R.drawable.half_castles_curve, 2));
        tiles.add(new Tile("Torso castle",
                R.drawable.torso_castle, 1));
        tiles.add(new Tile("Torso castle",
                R.drawable.torso_castle_shield, 2));
        tiles.add(new Tile("Castle end piece top and left edges",
                R.drawable.castle_tl, 2));
        tiles.add(new Tile("Castle end piece top and bottom edges",
                R.drawable.castle_tb, 3));

        tiles.add(new Tile("Castle end piece top edge",
                R.drawable.castle_t, 5));
        tiles.add(new Tile("Castle end piece top, curvy road left to bottom",
                R.drawable.castle_t_lb, 3));
        tiles.add(new Tile("Castle end piece top, curvy road right to bottom",
                R.drawable.castle_t_rb, 3));
        tiles.add(new Tile("Castle end piece top, 3 way intersection",
                R.drawable.castle_top_3way, 3));
        tiles.add(new Tile("Castle end piece top, straight road",
                R.drawable.castle_t_str8, 3));

        tiles.add(new Tile("Straight road",
                R.drawable.straight_road, 8));
        tiles.add(new Tile("Curvy road",
                R.drawable.curvy_road, 9));
        tiles.add(new Tile("3 way intersection",
                R.drawable.three_way, 4));
        tiles.add(new Tile("4 way intersection",
                R.drawable.four_way, 1));

        Collections.sort(tiles);
    }

    private List<Tile> getFreshTileList() {
        List<Tile> freshTiles = new ArrayList<>();
        for (Tile tile : tiles) {
            freshTiles.add(new Tile(tile));
        }
        return freshTiles;
    }

    public List<Tile> initialize() {
        currentTiles = getFreshTileList();
        return currentTiles;
    }

    public List<Tile> getFilteredTiles(boolean ignoreEmpties, String searchTerm) {
        List<Tile> filteredTiles = new ArrayList<>();
        for (Tile tile : currentTiles) {
            if (tile.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                if (ignoreEmpties) {
                    if (tile.getNumRemaining() > 0) {
                        filteredTiles.add(tile);
                    }
                } else {
                    filteredTiles.add(tile);
                }
            }
        }
        return filteredTiles;
    }
}
