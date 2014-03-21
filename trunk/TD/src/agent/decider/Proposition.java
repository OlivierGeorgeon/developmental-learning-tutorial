package agent.decider;

import coupling.Experience;

public class Proposition implements Comparable<Proposition>{
	
	private Experience experience;
	private int proclivity;

	public Proposition(Experience experience, int weight){
		this.experience = experience;
		this.proclivity = weight;
	}

	public int compareTo(Proposition proposition){
		return new Integer(proposition.getProclivity()).compareTo(proclivity);
	}

	public boolean equals(Object otherProposition)
	{
		return ((Proposition)otherProposition).getExperience() == this.experience;
	}
	
	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}

	public int getProclivity() {
		return proclivity;
	}

	public void addProclivity(int proclivity) {
		this.proclivity += proclivity;
	}
	
	public String toString(){
		return this.experience.getLabel() + "," + this.proclivity;
	}
}
