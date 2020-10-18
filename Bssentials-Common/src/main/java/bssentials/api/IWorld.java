package bssentials.api;

public interface IWorld {

    public void generateTree(double x, double y, double z);

    public String getName();

    public int getLoadedChunkCount();

    public int getEntityCount();

    public int getBlockAt(int x, int y, int z);

    public void setStorm(boolean b);

    public void spawnEntity(Location location, String mobName, Object implobject);

}