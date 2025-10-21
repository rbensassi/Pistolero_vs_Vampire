import java.util.ArrayList;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Container {
	public ArrayList<Vampire> vampList;
	public ArrayList<Bullet> bullets;
	public ArrayList<Obstacle> obstacles;
	public Pistoleros pist;
	SimpleDoubleProperty gameSpeedVampire;
	SimpleDoubleProperty gameSpeedPistolero;
	SimpleIntegerProperty timer;
	double timerI;
	int change;
	int []choix;
	boolean pause;
	public int pauseTime;
	public boolean endpartie = false;
	public ArrayList<VampireHitInfo> vampireHits;
	public Container(ArrayList<Vampire> vampList, Pistoleros pist,	ArrayList<Obstacle> obstacles) {
		this.vampList = vampList;
		bullets = new ArrayList<Bullet>();
		this.pist = pist;
		this.obstacles=obstacles;
		choix = new int[vampList.size()];
		change=0;
		gameSpeedVampire = new SimpleDoubleProperty();
		gameSpeedPistolero =  new SimpleDoubleProperty();
		timer = new SimpleIntegerProperty() ;
		vampireHits = new ArrayList<VampireHitInfo>();

		pauseTime=20;
		pause = false;
	}
	public void update(double speed){
		if(pist.kc.pause() && pauseTime==20){
			pause = pause?false:true;
			pauseTime=0;
		}
		if(!pause){
			timerI =timerI+speed;
			timer.setValue(timerI);
			pistMove(speed);
			bulletMove(speed*2);
			updateTime();
			vampMove(speed);
			checkCollides(speed);

		}
		else{
			stop();
			if(pauseTime < 20)
				pauseTime++;
		}
	}

	public void updateTime(){
		if(pauseTime < 20)
			pauseTime++;
		if(pist.reloadTime != 20)
			pist.reloadTime++;
		if(pist.hurtTime !=50){
			pist.hurtTime++;
			if(pist.hurtTime==50)
				pist.getHurt=false;
		}

	}




	public void bulletMove(double speed){
		for(int i=0;i<bullets.size();i++){
			bullets.get(i).update(speed);
		}
		if(pist.gun.nbBullet.getValue()==0){
			if(!pist.gun.reload())
				pist.gun.reloadTime++;
		}
		if(pist.kc.moveSpace() && pist.shoot()){
			Bullet bulls = new Bullet(pist,400,10,10,0,0);
			bullets.add(bulls);
		}
	}


	public void pistMove(double speed){
		if(pist.kc.moveUp()){

			pist.offsetY=0;
			pist.moveUp();
			pist.sens=0;
		}
		else if(pist.kc.moveDown()){

			pist.offsetY=36*2;
			pist.moveDown();
			pist.sens=2;

		}	
		else if(pist.kc.moveRight()){

			pist.offsetY=36;
			pist.moveRight();
			pist.sens=1;

		}
		else if(pist.kc.moveLeft()){

			pist.offsetY=36*3;
			pist.moveLeft();
			pist.sens=3;
		}
		pist.move(speed*gameSpeedPistolero.getValue());
		if(!pist.kc.moveLeft() && !pist.kc.moveRight())
			pist.stopVert();
		if(!pist.kc.moveDown() && !pist.kc.moveUp())
			pist.stopHor();
	}

	public void stop(){
		pist.moveX=0;
		pist.moveY=0;
		for(int i=0;i<vampList.size();i++){
			vampList.get(i).moveX=0;
			vampList.get(i).moveY=0;
		}
	}

	public void vampMove(double speed){
		for(int i=0;i<vampList.size();i++){
			if(vampList.get(i).getChange()>=(vampList.get(i).getTimeChange()/gameSpeedVampire.getValue())){
				vampList.get(i).setChange(0);
				vampList.get(i).changeTimeChange();
			}
			if(vampList.get(i).getChange()==0){
				choix [i]= (int) (Math.random()*4);
			}



			switch(choix[i]){
			case 0:
				vampList.get(i).moveRight();
				vampList.get(i).offsetY=36;
				vampList.get(i).moveY=0;
				vampList.get(i).sens=1;
				break;
			case 1:
				vampList.get(i).moveLeft();
				vampList.get(i).offsetY=3*36;
				vampList.get(i).moveY=0;
				vampList.get(i).sens=3;

				break;
			case 2:
				vampList.get(i).moveUp();
				vampList.get(i).offsetY=0;
				vampList.get(i).moveX=0;
				vampList.get(i).sens=0;
				break;
			case 3:
				vampList.get(i).moveDown();
				vampList.get(i).offsetY=2*36;
				vampList.get(i).moveX=0;
				vampList.get(i).sens = 2;
				break;	
			}
			vampList.get(i).move(speed*gameSpeedVampire.getValue());
			vampList.get(i).updateChange();
		}
		change++;

	}
	public void checkCollides(double speed){

		for(int i=0;i<vampList.size();i++){
			for(int j=i+1;j<vampList.size();j++){
				if(vampList.get(i).collides(vampList.get(j)) || vampList.get(j).collides(vampList.get(i))){
					vampList.get(i).life.setValue(0);
					break;
				}
			}
			for(int j=0;j<bullets.size();j++){
				if((vampList.get(i).collides(bullets.get(j))|| bullets.get(j).collides(vampList.get(i))) && vampList.get(i).isAlive() && !bullets.get(j).explose){
					pist.up_kill_scoring();

					// Determine bullet direction from movement
					int bulletDir = 0;
					if(bullets.get(j).moveX > 0) bulletDir = 1;
					else if(bullets.get(j).moveX < 0) bulletDir = 3;
					else if(bullets.get(j).moveY > 0) bulletDir = 2;
					else if(bullets.get(j).moveY < 0) bulletDir = 0;

					// Apply knockback to vampire
					double knockbackForce = 30;
					if(bullets.get(j).moveX != 0) {
						vampList.get(i).posX += (bullets.get(j).moveX / Math.abs(bullets.get(j).moveX)) * knockbackForce;
					}
					if(bullets.get(j).moveY != 0) {
						vampList.get(i).posY += (bullets.get(j).moveY / Math.abs(bullets.get(j).moveY)) * knockbackForce;
					}

					vampList.get(i).getHurt(pist.getDammage());

					// Record hit for visual effects
					boolean isDeath = !vampList.get(i).isAlive();
					VampireHitInfo hitInfo = new VampireHitInfo(
						vampList.get(i),
						bullets.get(j).posX,
						bullets.get(j).posY,
						bulletDir,
						isDeath
					);
					vampireHits.add(hitInfo);

					bullets.get(j).explose=true;
					break;
				}
			}
			if((vampList.get(i).collides(pist) || pist.collides(vampList.get(i))) && pist.isAlive() && vampList.get(i).isAlive()){
				pist.getHurt(vampList.get(i).getDammage());
				
			}
			for(int j =0;j<obstacles.size();j++){
				if(vampList.get(i).collides(obstacles.get(j))|| obstacles.get(j).collides(vampList.get(i))){

					if(vampList.get(i).sens==0)
						vampList.get(i).posY+=vampList.get(i).speed*speed;
					else if(vampList.get(i).sens==1)
						vampList.get(i).posX-=vampList.get(i).speed*speed;
					else if(vampList.get(i).sens==2)
						vampList.get(i).posY-=vampList.get(i).speed*speed;
					else
						vampList.get(i).posX +=vampList.get(i).speed*speed;
					vampList.get(i).setChange(vampList.get(i).getTimeChange()/gameSpeedVampire.getValue());
				}
			}			
		}
		obstacleCollides(speed);


	}

	public void obstacleCollides(double speed){
		for(int i =0;i<obstacles.size();i++){
			if(pist.collides(obstacles.get(i))|| obstacles.get(i).collides(pist)){
				if(pist.sens==0){
					if(pist.speed>obstacles.get(i).poids ){
						double oldY = obstacles.get(i).posY;
						obstacles.get(i).moveY=-(pist.speed-obstacles.get(i).poids);
						obstacles.get(i).move(speed);
						if(collidesVamps(obstacles.get(i)) || collidesObstacles(obstacles.get(i)))
							obstacles.get(i).posY=oldY;
						obstacles.get(i).moveY=0;
						
					}
						pist.posY =obstacles.get(i).posY+obstacles.get(i).height+2;
				}
				else if(pist.sens==1){
					if(pist.speed>obstacles.get(i).poids ){
						double oldX = obstacles.get(i).posX;

						obstacles.get(i).moveX=(pist.speed-obstacles.get(i).poids);
						obstacles.get(i).move(speed);
						if(collidesVamps(obstacles.get(i)) || collidesObstacles(obstacles.get(i)))
							obstacles.get(i).posX=oldX;
						obstacles.get(i).moveX=0;
					}
						pist.posX=obstacles.get(i).posX-pist.width-2;
				}
				else if(pist.sens==2){
					if(pist.speed>obstacles.get(i).poids ){
						double oldY = obstacles.get(i).posY;
						obstacles.get(i).moveY=(pist.speed-obstacles.get(i).poids);
						obstacles.get(i).move(speed);
						if(collidesVamps(obstacles.get(i)) || collidesObstacles(obstacles.get(i)))
							obstacles.get(i).posY=oldY;
						obstacles.get(i).moveY=0;
						
					}
					pist.posY =obstacles.get(i).posY-pist.height-2;

				}
				else{
					if(pist.speed>obstacles.get(i).poids ){
						double oldX = obstacles.get(i).posX;

						obstacles.get(i).moveX=-(pist.speed-obstacles.get(i).poids);
						obstacles.get(i).move(speed);
						if(collidesVamps(obstacles.get(i)) || collidesObstacles(obstacles.get(i)))
							obstacles.get(i).posX=oldX;
						obstacles.get(i).moveX=0;
					}

					pist.posX =obstacles.get(i).posX+obstacles.get(i).width+2;
				}
				


			}
			for(int j=0;j<bullets.size();j++){
				if((obstacles.get(i).collides(bullets.get(j))|| bullets.get(j).collides(obstacles.get(i)))  && !bullets.get(j).explose)
					bullets.get(j).explose = true;
			}
		}
	}
	
	public boolean collidesObstacles(Sprite s){
		for(int i=0;i<obstacles.size();i++){

			if(obstacles.get(i)!=s)
				if(s.collides(obstacles.get(i)) || obstacles.get(i).collides(s)){
					return true;
				}
		}
		return false;
	}
	public boolean collidesVamps(Sprite s){
		for(int i=0;i<vampList.size();i++){
			if(s.collides(vampList.get(i))||vampList.get(i).collides(s)){
				if(vampList.get(i).getChange()!=0)
				vampList.get(i).setChange(vampList.get(i).getTimeChange()/gameSpeedVampire.getValue());
				return true;
			}
		}
		return false;
	}

}




