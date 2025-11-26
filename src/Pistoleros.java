import javafx.beans.property.SimpleIntegerProperty;

public class Pistoleros extends Character{
	Gun gun;
	KeyController kc;
	int reloadTime;
	boolean getHurt;
	int hurtTime;
	SimpleIntegerProperty score;

	// Vampire Survivors style systems
	LevelSystem levelSystem;
	UpgradeManager upgradeManager;
	AutoFireSystem autoFireSystem;
	private double baseSpeed;
	private int maxLife;
	private double damageMultiplier;
	private double armorReduction;
	private double pickupRange;

	public Pistoleros(double posX, double posY,
			double speed, double maxX, double maxY, double width, double height,double offsetX,double offsetY, int life, Gun gun,KeyController kc) {
		super(posX, posY, speed, maxX, maxY,width, height,offsetX,offsetY, life);
		this.gun = gun;
		this.kc =kc;
		sens=0;
		getHurt=false;
		reloadTime = 20;
		hurtTime = 50;
		score = new SimpleIntegerProperty();
		score.set(0);

		// Initialize Vampire Survivors systems
		this.levelSystem = new LevelSystem();
		this.upgradeManager = new UpgradeManager();
		this.autoFireSystem = new AutoFireSystem();
		this.baseSpeed = speed;
		this.maxLife = life;
		this.damageMultiplier = 1.0;
		this.armorReduction = 0.0;
		this.pickupRange = 100.0;
	}
	
	public void KeyControllerchanged(KeyController kc){
		this.kc = kc;
	}
	
	
	public boolean shoot(){
		if(reloadTime==20 && gun.nbBullet.getValue() !=0){
			reloadTime = 0;
			gun.nbBullet.setValue(gun.nbBullet.getValue()-1);
			return true;
		}
		else
			return false;
	}
	
	public int getDammage(){
		return (int) (gun.damage * damageMultiplier);
	}

	public boolean getHurt(int dam){
		if(!getHurt){
			getHurt = true;
			hurtTime=0;
			// Apply armor reduction
			int actualDamage = (int) Math.max(1, dam * (1.0 - armorReduction));
			life.setValue(life.getValue()-actualDamage);
			return true;
		}
		else
			return false;
	}

	public void up_kill_scoring() {
		score.set(score.get()+10);
	}

	public void up_time_score(SimpleIntegerProperty time) {
		score.set(score.get()+time.get());
	}

	/**
	 * Add XP to the player
	 * @param xp Amount of XP to add
	 * @return true if leveled up
	 */
	public boolean addXP(int xp) {
		return levelSystem.addXP(xp);
	}

	/**
	 * Apply an upgrade to the player
	 */
	public void applyUpgrade(Upgrade upgrade) {
		upgradeManager.applyUpgrade(upgrade);

		switch (upgrade.getType()) {
			case WEAPON_DAMAGE:
				damageMultiplier += upgrade.getValue();
				break;
			case WEAPON_SPEED:
				autoFireSystem.increaseFireRate(upgrade.getValue());
				break;
			case WEAPON_PROJECTILE_COUNT:
				autoFireSystem.addProjectile((int) upgrade.getValue());
				break;
			case MOVE_SPEED:
				speed = baseSpeed * (1.0 + upgrade.getCurrentValue());
				break;
			case MAX_HEALTH:
				maxLife += (int) upgrade.getValue();
				life.setValue(Math.min(life.getValue() + (int) upgrade.getValue(), maxLife));
				break;
			case PICKUP_RANGE:
				pickupRange = 100.0 * (1.0 + upgrade.getCurrentValue());
				break;
			case ARMOR:
				armorReduction = Math.min(0.75, armorReduction + upgrade.getValue());
				break;
			case COOLDOWN_REDUCTION:
				// Reduce reload time
				reloadTime = (int) Math.max(5, 20 * (1.0 - upgrade.getCurrentValue()));
				break;
			default:
				break;
		}
	}

	// Getters for Vampire Survivors systems
	public LevelSystem getLevelSystem() {
		return levelSystem;
	}

	public UpgradeManager getUpgradeManager() {
		return upgradeManager;
	}

	public AutoFireSystem getAutoFireSystem() {
		return autoFireSystem;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public double getPickupRange() {
		return pickupRange;
	}

	public double getDamageMultiplier() {
		return damageMultiplier;
	}

	public double getArmorReduction() {
		return armorReduction;
	}
}
