package fr.anthonyquere.fizzbuzz;

import java.util.ArrayList;
import java.util.List;

public class FizzBuzz {

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        startFizzBuzz(10).forEach(System.out::println);
    }

/* 
     * @param count Le nombre d'éléments à générer
     * @return Une liste de chaînes de caractères selon les règles du jeu FizzBuzz
     */
    public static List<String> startFizzBuzz(int count) {
        List<String> result = new ArrayList<>();
        
        // Si count est 0, retourner une liste vide
        if (count <= 0) {
            return result;
        }
        
        // Générer les éléments de la liste
        for (int i = 0; i < count; i++) {
            int number = i + 1;
            boolean divisibleBy3 = number % 3 == 0;
            boolean divisibleBy5 = number % 5 == 0;
            
            if (divisibleBy3 && divisibleBy5) {
                result.add("FizzBuzz");
            } else if (divisibleBy3) {
                result.add("Fizz");
            } else if (divisibleBy5) {
                result.add("Buzz");
            } else {
                result.add(String.valueOf(number));
            }
        }
        
        return result;
    }
}
