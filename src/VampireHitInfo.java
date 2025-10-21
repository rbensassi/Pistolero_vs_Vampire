public class VampireHitInfo {
    public Vampire vampire;
    public double hitX;
    public double hitY;
    public int bulletDirection;
    public boolean isDeath;

    public VampireHitInfo(Vampire vampire, double hitX, double hitY, int bulletDirection, boolean isDeath) {
        this.vampire = vampire;
        this.hitX = hitX;
        this.hitY = hitY;
        this.bulletDirection = bulletDirection;
        this.isDeath = isDeath;
    }
}
