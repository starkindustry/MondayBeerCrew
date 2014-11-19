
public class TomatoSauce extends ToppingDecorator{

	public TomatoSauce(Pizza newPizza) {
		super(newPizza);
		
		System.out.println("Adding Sause");
		

	}

	
	
	public String getDescription() {
	
		return tempPizza.getDescription() + ", Tomato Sauce";
	}


	public double getCost() {

		return tempPizza.getCost() + 0.50;
	}

}
