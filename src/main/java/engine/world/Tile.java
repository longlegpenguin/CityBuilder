package engine.world;

import engine.entities.Entity;
import engine.renderEngine.Loader;
import engine.terrain.Terrain;
import engine.textures.TextureAttribute;

/**
 * Single Tile class contains all necessary data for one tile of the grid.
 * Tile can contain a zone or a buildable
 * Always contains a terrain.
 */
public class Tile {

    private Terrain terrain;
    private Entity zone;
    private Entity buildable;

    boolean isSelected = false;
    public Tile(int gridX, int gridZ, Loader loader, TextureAttribute texture) {
        this.terrain = new Terrain(gridX, gridZ, loader, texture);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Entity getZone() {
        return zone;
    }

    public Entity getBuildable() {
        return buildable;
    }

    public void setZone(Entity zone) {
        this.zone = zone;
    }

    public void setBuildable(Entity buildable) {
        this.buildable = buildable;
    }
}
