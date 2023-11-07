package bsu.rfe.java.group7.lab1.ravgeysh.varС7;
import java.util.Arrays;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Product {
	public static void main(String[] args) {
        Food[] breakfast = new Food[20];

        int itemsSoFar = 0;
        boolean countCalories = true;
        boolean doSort = true;

        for (String arg : args) {
            if (arg.startsWith("-")) {
                if (arg.equals("-calories")) {
                    countCalories = true;
                } else if (arg.startsWith("-sort")) {
                    doSort = true;
                }

            } else {
                String[] parts = arg.split("/");
                String className = parts[0];
                try {
                    Class<?> productClass = Class.forName("bsu.rfe.java.group7.lab1.ravgeysh.varС7." + className);
                    if (parts.length == 3) {
                    	Constructor<?> constructor = productClass.getConstructor(String.class,String.class);
                    	Object product = constructor.newInstance(parts[1],parts[2]);
                    	breakfast[itemsSoFar] = (Food) product;
                    }
                    else {
                    	Constructor<?> constructor = productClass.getConstructor(String.class);
                    	Object product = constructor.newInstance(parts[1]);
                    	breakfast[itemsSoFar] = (Food) product;
                    }
                    itemsSoFar++;
                } catch (ClassNotFoundException e) {
                    System.out.println("Продукт " + className + " не найден и не может быть включен в завтрак.");
                } catch (NoSuchMethodException e) {
                    System.out.println("У продукта " + className + " нет конструктора с одним параметром типа String и он не может быть включен в завтрак.");
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    System.out.println("Ошибка при создании экземпляра продукта " + className);
                }
            }
        }
        
        int IceCreamSearching_ChocolateX2 = 0;
        IceCream iceCreamSearch_ChocolateX2 = new IceCream("chocolate","chocolate");
        for (Food iceCream : breakfast) {
            if (iceCream instanceof IceCream) {
                if ((iceCream).equals(iceCreamSearch_ChocolateX2))
                    IceCreamSearching_ChocolateX2++;
            }
        }
        System.out.println("Количество " + iceCreamSearch_ChocolateX2 + " всего " + IceCreamSearching_ChocolateX2);

        if (countCalories) {
            int sum = 0;
            for (Food item : breakfast) {
                if (item != null) {
                    sum += item.calculateCalories();
                } else break;
            }
            System.out.println("Всего калорий: " + sum);
        }

        if (doSort) {
            Arrays.sort(breakfast, new FoodComparator());
            for (Food food : breakfast) {
                if (food == null) break;
                food.consume();
            }
        }
	}
}

