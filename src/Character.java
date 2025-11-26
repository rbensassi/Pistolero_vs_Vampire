import javafx.beans.property.SimpleIntegerProperty;

public  abstract class Character extends Sprite{
	SimpleIntegerProperty life;
	int sens;

	// Enums for animation states and directions
	public enum AnimationState {
		IDLE, WALKING, ATTACKING, HURT, DEATH
	}

	public enum Direction {
		DOWN(0),   // facing down
		LEFT(1),   // facing left
		RIGHT(2),  // facing right
		UP(3);     // facing up

		private final int row;
		Direction(int row) {
			this.row = row;
		}
		public int getRow() {
			return row;
		}
	}

	protected AnimationState currentState;
	protected Direction currentDirection;
	protected Direction lastMovementDirection;

	public Character(double posX, double posY,
			double speed, double maxX, double maxY, double width, double height,double offsetX,double offsetY,int life) {
		super(posX, posY, speed, maxX, maxY, width, height,offsetX,offsetY);
		this.life = new SimpleIntegerProperty(life);
		this.currentState = AnimationState.IDLE;
		this.currentDirection = Direction.DOWN;
		this.lastMovementDirection = Direction.DOWN;
	}
	
	public  abstract boolean getHurt(int dammage);
	
	
	public boolean isAlive(){
		if(life.getValue()>0)
			return true;
		else
			return false;
	}

	// Getters and setters for animation state and direction
	public AnimationState getAnimationState() {
		return currentState;
	}

	public void setAnimationState(AnimationState state) {
		this.currentState = state;
	}

	public Direction getDirection() {
		return currentDirection;
	}

	public void setDirection(Direction direction) {
		this.currentDirection = direction;
		this.lastMovementDirection = direction;
	}

	public Direction getLastMovementDirection() {
		return lastMovementDirection;
	}

	// Update direction based on movement
	public void updateDirectionFromMovement() {
		if (moveX != 0 || moveY != 0) {
			// Prioritize horizontal movement for direction
			if (Math.abs(moveX) > Math.abs(moveY)) {
				currentDirection = moveX > 0 ? Direction.RIGHT : Direction.LEFT;
			} else {
				currentDirection = moveY > 0 ? Direction.DOWN : Direction.UP;
			}
			lastMovementDirection = currentDirection;
			currentState = AnimationState.WALKING;
		} else {
			currentState = AnimationState.IDLE;
		}
	}
}
