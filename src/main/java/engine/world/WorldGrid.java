package engine.world;

import engine.renderEngine.Loader;
import engine.terrain.Terrain;
import engine.textures.TextureAttribute;

import java.util.ArrayList;
import java.util.List;

public class WorldGrid {

    private static final int WORLD_SIZE = 10;
    private Tile[][] worldmatrix = new Tile[WORLD_SIZE][WORLD_SIZE];

    private List<Terrain> terrains = new ArrayList<Terrain>();
    public WorldGrid(Loader loader, TextureAttribute texture) {
        for (int i = 0; i < WORLD_SIZE; i++) {
            for (int j = 0; j < WORLD_SIZE; j++) {
                worldmatrix[i][j] = new Tile(i, j, loader, texture);

                terrains.add(worldmatrix[i][j].getTerrain());
            }
        }
    }

    public int getWorldSize() {
        return WORLD_SIZE;
    }

    public Tile[][] getWorldmatrix() {
        return worldmatrix;
    }

    public List<Terrain> getTerrainList() {
        return terrains;
    }

}
