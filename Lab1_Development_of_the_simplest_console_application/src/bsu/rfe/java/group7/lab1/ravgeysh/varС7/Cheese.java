package bsu.rfe.java.group7.lab1.ravgeysh.varС7;

public class Cheese extends Food implements Nutritious {
	
	private String sort;
	
    public Cheese(String sort) {
        super("Сыр");
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
    
    public void consume() {
        System.out.println(this + " съеден");
    }

    public String toString() {
        return super.toString() + " сорт '" + sort.toUpperCase() + "'";
    }
    
    @Override
    public boolean equals(Object arg0) {
        if (super.equals(arg0)) {
            if (!(arg0 instanceof Cheese)) return false; // если принадлежит классу IceCream
            return sort.equals(((Cheese)arg0).sort);
        }
        else
            return false;
    }
    
    @Override
    public int calculateCalories() {
        return 500;
    }
}
