package coupling;

public class Obtention1 implements Obtention {
	
	private Result result;
	
	public Obtention1(Result result){
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
