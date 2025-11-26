
public abstract class Sprite {
	protected double posX;
	protected double posY;
	protected double moveX;
	protected double moveY;
	protected double speed;
	protected double maxX;
	protected double maxY;
	protected double width;
	protected double height;
	protected double offsetX;
	protected double offsetY;
	public Sprite(double posX, double posY,
			double speed, double maxX, double maxY, double width, double height,double offsetX,double offsetY){
		this.posX = posX;
		this.posY = posY;
		this.moveX = 0;
		this.moveY = 0;
		this.speed = speed;
		this.maxX = maxX;
		this.maxY = maxY;
		this.width = width;
		this.height = height;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public void moveRight(){
		moveX = speed;
	}
	public void moveLeft(){
		moveX = -speed;
	}
	public void moveUp(){
		moveY = -speed;
	}
	
	public void moveDown(){
		moveY = speed;
	}
	public void stopVert(){
		moveX=0;
	}
	public void stopHor(){
		moveY=0;
	}
	public void move(double time){
		double deplX = moveX*time;
		double deplY = moveY*time;
		if(posX+deplX >=maxX-width)
			posX =maxX-width;
		else if(posX+deplX <=0)
			posX =0;
		else 
			posX += deplX;
		if(posY+deplY >=maxY-height)
			posY =maxY-height;
		else if(deplY+posY <=0)
			posY =0;
		else 
			posY += deplY;
		
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public double getMoveX() {
		return moveX;
	}

	public double getMoveY() {
		return moveY;
	}


	public double getSpeed() {
		return speed;
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMaxY() {
		return maxY;
	}
	// Improved collision detection using AABB (Axis-Aligned Bounding Box)
	public boolean collides(Sprite s){
		return !(posX + width < s.posX ||  // this is to the left of s
				 s.posX + s.width < posX ||  // s is to the left of this
				 posY + height < s.posY ||   // this is above s
				 s.posY + s.height < posY);  // s is above this
	}

	// Check collision with specific coordinates and dimensions
	public boolean collidesWithRect(double x, double y, double w, double h) {
		return !(posX + width < x ||
				 x + w < posX ||
				 posY + height < y ||
				 y + h < posY);
	}

	// Get the center X position
	public double getCenterX() {
		return posX + width / 2;
	}

	// Get the center Y position
	public double getCenterY() {
		return posY + height / 2;
	}

	// Calculate distance to another sprite
	public double distanceTo(Sprite s) {
		double dx = getCenterX() - s.getCenterX();
		double dy = getCenterY() - s.getCenterY();
		return Math.sqrt(dx * dx + dy * dy);
	}
	public boolean inBorder(){
		if(posX==0 || posY==0 || posX+width==maxX || posX+height==maxY)
			return true;
		else
			return false;
					
	}
}
