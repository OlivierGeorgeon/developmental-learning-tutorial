package coupling;

public class Situation1 implements Situation {
	
	private Result result;
	
	public Situation1(Result result){
		this.result = result;
	}
	
	public Result getResult(){
		return this.result;
	}

	@Override
	public String getLabel() {
		return this.result.getLabel();
	}

}
