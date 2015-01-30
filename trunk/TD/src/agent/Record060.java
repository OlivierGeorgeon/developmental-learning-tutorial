package agent;

import coupling.Experiment050;

public class Record060 implements Record {

	Experiment050 experiment;
	int count = 0;

	public Record060(Experiment050 experiment, int count){
		this.experiment = experiment;
		this.count = count;
	}

	public Experiment050 getExperiment(){
		return this.experiment;
	}

	public int compareTo(Record record){
		return new Integer(this.count).compareTo(((Record060)record).getCount());
	}

	public boolean equals(Object otherRecord){
		return ((Record060)otherRecord).getExperiment() == this.getExperiment();
	}
	
	public int getCount() {
		return count;
	}

	public void addCount(int count) {
		this.count += count;
	}
	
	@Override
	public String toString(){
		return this.getExperiment().getLabel() + " count " + this.getCount();
	}
}
