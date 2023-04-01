package engine.world;

import engine.entities.Entity;
import engine.renderEngine.Loader;
import engine.terrain.Terrain;
import engine.textures.TextureAttribute;

public class Tile {

    private static Terrain terrain;
    private static Entity zone;
    private static Entity buildable;

    public Tile(int gridX, int gridZ, Loader loader, TextureAttribute texture) {
        this.terrain = new Terrain(gridX, gridZ, loader, texture);
    }

    public static Terrain getTerrain() {
        return terrain;
    }

    public static Entity getZone() {
        return zone;
    }

    public static Entity getBuildable() {
        return buildable;
    }

    public static void setZone(Entity zone) {
        Tile.zone = zone;
    }

    public static void setBuildable(Entity buildable) {
        Tile.buildable = buildable;
    }
}
