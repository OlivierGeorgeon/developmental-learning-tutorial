package agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;

import tracer.Trace;

import coupling.Experiment050;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction060;
import existence.Existence060;

public class Phenomenon060 implements Phenomenon {
	
	public static final int TOP_PLAY = 3; 

	private Interaction060 persistentInteraction;
	private List<Interaction> preInteractions = new ArrayList<Interaction>();
	private List<Interaction> postInteractions = new ArrayList<Interaction>();
	
	private List<Record060> postRecords = new ArrayList<Record060>();
	
	private String label;
	private boolean consistent = true;
	
	public Phenomenon060(Interaction060 interaction){
		this.label = interaction.getLabel();
		this.persistentInteraction = interaction;
	}
	
	public Phenomenon060(String label){
		this.label = label;
	}
	
	@Override
	public String getLabel(){
		return this.label;
	}

	@Override
	public Interaction060 getPersistentInteraction() {
		return this.persistentInteraction;
	}

	@Override
	public void addPreInteraction(Interaction060 interaction) {
		if (!this.preInteractions.contains(interaction))
			this.preInteractions.add(interaction);
	}

	@Override
	public List<Interaction> getPreInteractions() {
		return this.preInteractions;
	}

	@Override
	public void addPostInteraction(Interaction060 interaction) {
		if (!this.postInteractions.contains(interaction))
			this.postInteractions.add(interaction);
	}
	
	public void addPostExperiment(Experiment050 experiment){
		Record060 record = new Record060(experiment, 1);
		int i = this.postRecords.indexOf(record);
		if (i < 0)
			this.postRecords.add(record);
		else
			this.postRecords.get(i).addCount(1); 
	}
	
//	public int getPostExperimentCount(Experiment050 experiment){
//		Record060 record = new Record060(experiment, 0);
//		int i = this.postRecords.indexOf(record);
//		if (i < 0)
//			return 0;
//		else
//			return this.postRecords.get(i).getCount(); 		
//	}

	@Override
	public List<Interaction> getPostInteractions() {
		return this.postInteractions;
	}

	@Override
	public void trace(){
		Element e = Trace.addEventElement("phenomenon");
		Trace.addSubelement(e,"label", this.getLabel());
		
		for (Interaction interaction : this.getPreInteractions()){
			Trace.addSubelement(e,"pre_interaction", interaction.getLabel());
		}
		
		for (Interaction interaction : this.getPostInteractions()){
			Trace.addSubelement(e,"post_interaction", interaction.getLabel());
		}
		
		if (!this.isConsistent())
			Trace.addSubelement(e,"inconsistent");
	}

	@Override
	public void setConsistent(boolean consistent) {
		this.consistent = consistent;
	}

	@Override
	public boolean isConsistent() {
		return consistent;
	}
	
	@Override
	public boolean isAlreadyTried(Experiment050 experiment){
		if (experiment.getIntendedInteraction() == this.getPersistentInteraction())
			return true;
		else if (experiment.getEnactedInteractions().contains(this.getPersistentInteraction()))
			return true;
		else if (this.getPostInteractions().contains(experiment.getIntendedInteraction()))
			return true;
		else{ 
			for (Interaction interaction : experiment.getEnactedInteractions())
				if (this.getPostInteractions().contains(interaction))
					return true;
			return false;
		}
	}
	
	@Override
	public Experiment050 getPlayExperiment(){
		if (this.postRecords.size() > 0){
			Collections.sort(this.postRecords);
			if (this.postRecords.get(0).getCount() < Existence060.TOP_EXCITEMENT)
				return this.postRecords.get(0).getExperiment();
			else return null;
		}
		else 
			return null;
	}

}
