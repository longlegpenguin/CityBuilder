package engine.world;

import engine.entities.Entity;
import engine.renderEngine.Loader;
import engine.terrain.Terrain;
import engine.terrain.ZoneTile;
import engine.textures.TextureAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the entire grid of tiles in a matrix.
 */
public class WorldGrid {

    private static final int WORLD_SIZE = 20;
    private Tile[][] worldmatrix = new Tile[WORLD_SIZE][WORLD_SIZE];

    private List<Terrain> terrains = new ArrayList<Terrain>();
    private List<ZoneTile> zones = new ArrayList<ZoneTile>();
    private List<Entity> buildables = new ArrayList<Entity>();
    private List<Entity> zoneBuildables = new ArrayList<Entity>();

    /**
     * Generates the complete grid of all terrains.
     * @param loader
     * @param texture
     */
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
    
    public List<ZoneTile> getZoneList() {
        zones.clear();
        for (int i = 0; i < WORLD_SIZE; i++) {
            for (int j = 0; j < WORLD_SIZE; j++) {
                if (worldmatrix[i][j].getZone() != null) {
                    zones.add(worldmatrix[i][j].getZone());
                }
            }
        }
        return zones;
    }

    public List<Entity> getBuildableList() {
        buildables.clear();
        for (int i = 0; i < WORLD_SIZE; i++) {
            for (int j = 0; j < WORLD_SIZE; j++) {
                if (worldmatrix[i][j].getBuildable() != null) {
                    buildables.add(worldmatrix[i][j].getBuildable());
                }
            }
        }
        return buildables;
    }

    public List<Entity> getZoneBuildableList() {
        zoneBuildables.clear();
        for (int i = 0; i < WORLD_SIZE; i++) {
            for (int j = 0; j < WORLD_SIZE; j++) {
                if (worldmatrix[i][j].getZoneBuildable() != null) {
                    zoneBuildables.add(worldmatrix[i][j].getZoneBuildable());
                }
            }
        }
        return zoneBuildables;
    }

    public void addBuildable(int x, int z, Entity buildable) {
        worldmatrix[x][z].setBuildable(buildable);
    }

    public void addZone(int x, int z, ZoneTile zoneTile) {worldmatrix[x][z].setZone(zoneTile);}

    public void addZoneBuildable(int x, int z, Entity zoneBuildable) {worldmatrix[x][z].setZoneBuildable(zoneBuildable);}

    public void clearGrid() {
        for (int i = 0; i < WORLD_SIZE; i++) {
            for (int j = 0; j < WORLD_SIZE; j++) {
                worldmatrix[i][j].setZone(null);
                worldmatrix[i][j].setBuildable(null);
                worldmatrix[i][j].setZoneBuildable(null);
            }
        }
    }

}
