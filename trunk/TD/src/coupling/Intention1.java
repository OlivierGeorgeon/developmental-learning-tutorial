package coupling;

/**
 * An intention1 consists of an Experience
 */
public class Intention1 implements Intention {
	
	private Experience experience;
	
	public Intention1(Experience experience){
		this.experience = experience;
	}

	public Experience getExperience(){
		return this.experience;
	}
	
	@Override
	public String getLabel() {
		return this.experience.getLabel();
	}

}
