package coupling;

import coupling.interaction.Interaction031;

public class Intention4 extends Intention1{
	
	private Experience primitiveExperience;

	public Intention4(Experience experience){
		super(experience);
	}

	protected void setPrimitiveExperience(Experience experience){
		this.primitiveExperience = experience;
	}
	
	protected Experience getPrimitiveExperience(){
		return this.primitiveExperience;
	}	
}
