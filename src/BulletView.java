import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;

public class BulletView extends SpriteView{
	public static final String image = "file:res/bulletf.png";
	Bullet bullet;
	BulletTrail trail;

	public BulletView(Bullet bullet,Pane p) {
		super(image, p);
		this.bullet = bullet;
		this.trail = new BulletTrail(bullet, p);
		imageView.setViewport(new Rectangle2D(bullet.offsetX,bullet.offsetY,bullet.width,bullet.height));
		if(bullet.moveX<0)
		imageView.setRotate(180);
		else if(bullet.moveY>0)
			imageView.setRotate(90);
		else if(bullet.moveY<0)
			imageView.setRotate(270);

	}
	public void update(){
		imageView.relocate(bullet.getPosX(),bullet.getPosY());
		trail.update();
		if(bullet.explose) {
			trail.clear();
			remove();
		}

	}
}
