package agent;

import coupling.Experiment;

/**
 * An Anticipation031 is created for each experience.
 * Anticipations031 are equal if they propose the same experience.
 * An Anticipation031 is greater than another if its proclivity is greater that the other's.
 */
public class Anticipation031 implements Anticipation {
	
	Experiment experience;
	int proclivity;
	
	public Anticipation031(Experiment experience, int proclivity){
		this.experience = experience;
		this.proclivity = proclivity;
	}
	
	public int compareTo(Anticipation anticipation){
		return new Integer(((Anticipation031)anticipation).getProclivity()).compareTo(this.proclivity);
	}

	public boolean equals(Object otherProposition){
		return ((Anticipation031)otherProposition).getExperience() == this.experience;
	}
	
	public Experiment getExperience() {
		return this.experience;
	}

	public void setExperience(Experiment experience) {
		this.experience = experience;
	}

	public int getProclivity() {
		return proclivity;
	}

	public void addProclivity(int proclivity) {
		this.proclivity += proclivity;
	}
	
	public String toString(){
		return this.experience.getLabel() + " proclivity " + this.proclivity;
	}

}
