package bsu.rfe.java.group7.lab1.ravgeysh.varС7;

public class IceCream extends Food {

    private String sirup1;
    private String sirup2;

    public IceCream(String sirup1, String sirup2) {
        super("Мороженое");
        this.sirup1 = sirup1;
        this.sirup2 = sirup2;
    }

    public String getSirup1() {
        return sirup1;
    }
    public String getSirup2() {
        return sirup2;
    }
    public void setSirup1(String sirup1) {
        this.sirup1 = sirup1;
    }
    public void setSirup2(String sirup2) {
        this.sirup2 = sirup2;
    }
    
    public String toString() {
        return super.toString() + " сироп 1'" + sirup1.toUpperCase() + "' сироп 2'" + sirup2.toUpperCase() + "'";
    }

    @Override
    public void consume() {
            System.out.println(this + " съедено");
    }
    
    @Override
    public boolean equals(Object arg0) {
        if (super.equals(arg0)) {
            if (!(arg0 instanceof IceCream)) return false; // если принадлежит классу IceCream
            if (!(sirup1.equals(((IceCream)arg0).sirup1))) return false;
            return sirup2.equals(((IceCream)arg0).sirup2);
        }
        else
            return false;
    }

    @Override
    public int calculateCalories() {
    	int sum_calories = 0;
        switch (sirup1) {
            case "caramel" :
            	sum_calories += 450;
            case "chocolate" :
            	sum_calories += 375;
            default :
            	sum_calories += 0;
        }
        switch (sirup2) {
        case "caramel" :
        	sum_calories += 450;
        case "chocolate" :
        	sum_calories += 375;
        default :
        	sum_calories += 0;
        }
        return sum_calories;
    }

}
