import java.util.ArrayList;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

public class ContainerView extends VBox{
	public static double WIDTH = 1280;
	public static double HEIGHT = 768;
	public ImageView background;
	public Container container;
	private PistoleroView player;
	private ArrayList<BulletView> bullets;
	private SimpleListProperty<VampireView> vamps;
	private SimpleListProperty<ObstacleView>  obstacles;
	private AnimationBleed bleed;

	private ArrayList<AnimationExplosion>  animation;
	private ArrayList<AnimationDeath>  death;
	private ArrayList<AnimationMuzzleFlash> muzzleFlashes;
	private ArrayList<AnimationBloodSplatter> bloodSplatters;
	private ScreenShake screenShake;
	private LowHealthVignette lowHealthVignette;
	private HitPause hitPause;
	private CameraZoom cameraZoom;
	Label tv;
	Pane p;
	private boolean begin = true;

	public ContainerView(Container container, ImageView b, Pane p){
		this.p = p;
		this.background = b;
		loadBackGround();
		this.container = container;
		setPlayer(new PistoleroView(container.pist,p));
		bullets =new ArrayList<BulletView>();
		getPlayer().add();
		loadVampire();
		loadObstacle();
		animation = new ArrayList<AnimationExplosion>();
		death = new ArrayList<AnimationDeath>();
		muzzleFlashes = new ArrayList<AnimationMuzzleFlash>();
		bloodSplatters = new ArrayList<AnimationBloodSplatter>();
		screenShake = new ScreenShake(p);
		lowHealthVignette = new LowHealthVignette(p, WIDTH, HEIGHT);
		hitPause = new HitPause(container);
		cameraZoom = new CameraZoom(p);
		bleed = new AnimationBleed(getPlayer());
	}

	public void update(){
		p.requestFocus();
		relocateAll();
		removingDeath();

		// Update low health vignette effect
		lowHealthVignette.update(container.pist.life.getValue(), 3);
	}

	public void relocateAll(){
		getPlayer().update();
		if(((Pistoleros)getPlayer().getCharact()).getHurt && (bleed.isFinished || begin )){
			bleed.isFinished = false;
			begin = false;
			bleed.play();
		}
		for(int i=0;i<vamps.size();i++){
			vamps.get(i).update();
		}
		for(int i=0;i<container.bullets.size();i++){
			if(i>=bullets.size()){
				AudioClip sonTire = new AudioClip("file:res/tire.mp3");
				sonTire.play();
				bullets.add(new BulletView(container.bullets.get(i),p));
				bullets.get(i).add();

				// Add muzzle flash and screen shake when shooting
				Pistoleros pist = container.pist;
				double flashX = pist.posX + pist.width / 2;
				double flashY = pist.posY + pist.height / 2;
				AnimationMuzzleFlash flash = new AnimationMuzzleFlash(flashX, flashY, pist.sens, p);
				flash.play();
				muzzleFlashes.add(flash);
				screenShake.lightShake();
			}
			bullets.get(i).update();
		}
		for(int i=0;i<obstacles.size();i++){
			obstacles.get(i).imageView.relocate(obstacles.get(i).caisse.posX,obstacles.get(i).caisse.posY);
		}

		// Clean up finished muzzle flashes
		for(int i = muzzleFlashes.size() - 1; i >= 0; i--) {
			if(muzzleFlashes.get(i).isFinished) {
				muzzleFlashes.remove(i);
			}
		}

		// Clean up finished blood splatters
		for(int i = bloodSplatters.size() - 1; i >= 0; i--) {
			if(bloodSplatters.get(i).isFinished) {
				bloodSplatters.remove(i);
			}
		}
	}
	
	public void removingDeath(){
		// Process vampire hits for visual effects
		for(int i = 0; i < container.vampireHits.size(); i++) {
			VampireHitInfo hit = container.vampireHits.get(i);

			// Add blood splatter
			AnimationBloodSplatter blood = new AnimationBloodSplatter(
				hit.hitX,
				hit.hitY,
				hit.bulletDirection,
				p
			);
			blood.play();
			bloodSplatters.add(blood);

			// Screen shake, hit pause, and camera zoom based on hit type
			if(hit.isDeath) {
				screenShake.heavyShake();
				hitPause.heavyPause();
				cameraZoom.mediumZoom();
			} else {
				screenShake.mediumShake();
				hitPause.lightPause();
				cameraZoom.smallZoom();
			}
		}
		container.vampireHits.clear();

		for(int i=0;i<animation.size();i++){
			if(animation.get(i).isFinished){
				animation.remove(i);
			}
		} for(int i=0;i<death.size();i++){
			if(death.get(i).isFinished)
				for(int j = 0; j <  vamps.size(); j++){
					if(death.get(i).v == vamps.get(j)){
						vamps.get(j).remove();
						vamps.remove(j);
						container.vampList.remove(j);
					}
				}
		}
		
		for(int i=0;i<container.bullets.size();i++){

			if(container.bullets.get(i).explose){
				if(container.bullets.get(i).moveX>0)
					animation.add(new AnimationExplosion(container.bullets.get(i).posX,container.bullets.get(i).posY, p));
				else
					animation.add(new AnimationExplosion(container.bullets.get(i).posX,container.bullets.get(i).posY, p));

				animation.get(animation.size()-1).play();
				container.bullets.remove(i);
				bullets.get(i).remove();
				bullets.remove(i);
				break;
			}
		}
		
		for(int i=0;i<vamps.size();i++){
			if(!vamps.get(i).charact.isAlive()){
				death.add(new AnimationDeath(vamps.get(i)));
				death.get(death.size()-1).play();			
			}			
		}
		
		if(!getPlayer().getCharact().isAlive()){
			getPlayer().remove();
			((Pistoleros)getPlayer().charact).up_time_score(container.timer);
		}
	}
	
	protected boolean check_game_win() {
		return (container.vampList.size() == 0);		
	}

	protected boolean check_game_over() {
		return (container.pist.life.getValue() == 0);
	}
	
	public SimpleListProperty<VampireView> getVamps() {
		return vamps;
	}

	public void setVamps(SimpleListProperty<VampireView> vamps) {
		this.vamps = vamps;
	}

	public PistoleroView getPlayer() {
		return player;
	}

	public void setPlayer(PistoleroView player) {
		this.player = player;
	}
	public void loadBackGround(){
		p.getChildren().add(background);
		background.setFitWidth(ContainerView.WIDTH);
		background.setFitHeight(ContainerView.HEIGHT);
	}
	public void loadVampire(){
		vamps=new SimpleListProperty<VampireView>(FXCollections.observableArrayList());
		for(int i=0;i<container.vampList.size();i++){
			vamps.add(new VampireView(p,container.vampList.get(i)));
			vamps.get(i).add();
		}
	}
	public void loadObstacle(){
		obstacles =new SimpleListProperty<ObstacleView>(FXCollections.observableArrayList());

		for(int i=0;i<container.obstacles.size();i++){
			obstacles.add(new ObstacleView(p,container.obstacles.get(i), container.obstacles.get(i).name));
			obstacles.get(i).add();
		}
	}
}
