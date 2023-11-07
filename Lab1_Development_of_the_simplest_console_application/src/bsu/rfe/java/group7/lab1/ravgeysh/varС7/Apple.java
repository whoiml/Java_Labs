package bsu.rfe.java.group7.lab1.ravgeysh.varС7;

public class Apple extends Food implements Nutritious {

	private String size;
	
	public Apple(String size) {
		super("Яблоко");
		this.size = size;
	}
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return super.toString() + " размера '" + size.toUpperCase() + "'";
		}
	
	@Override
	public void consume() {
		System.out.println(this + " съедено");
	}

	public boolean equals(Object arg0) {
		if (super.equals(arg0)) {
			if (!(arg0 instanceof Apple)) return false; // если принадлежит классу apple
			return size.equals(((Apple)arg0).size);
		}
		else
			return false;
	}

    @Override
    public int calculateCalories() {
        switch (size) {
			case "small" :
				return 50;
			case "medium" :
				return 75;
			case "big" :
				return 100;
			default :
				return 0;
        }
    }
}
