package engine.world;

import engine.entities.Entity;
import engine.renderEngine.Loader;
import engine.terrain.Terrain;
import engine.terrain.ZoneTile;
import engine.textures.TextureAttribute;

/**
 * Single Tile class contains all necessary data for one tile of the grid.
 * Tile can contain a zone or a buildable
 * Always contains a terrain.
 */
public class Tile {

    private Terrain terrain;
    private ZoneTile zone;
    private Entity buildable;
    private Entity zoneBuildable;

    boolean isSelected = false;
    public Tile(int gridX, int gridZ, Loader loader, TextureAttribute texture) {
        this.terrain = new Terrain(gridX, gridZ, loader, texture);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public ZoneTile getZone() {
        return zone;
    }

    public Entity getBuildable() {
        return buildable;
    }

    public void setZone(ZoneTile zone) {
        this.zone = zone;
    }

    public void setBuildable(Entity buildable) {
        this.buildable = buildable;
    }

    public Entity getZoneBuildable() {
        return zoneBuildable;
    }

    public void setZoneBuildable(Entity zoneBuildable) {
        this.zoneBuildable = zoneBuildable;
    }
}
