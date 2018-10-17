package plecagene;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PlecaGene
{
    public static void main(String[] args)
    {
        Logic log = new Logic();
        log.showItems();
        
        System.out.println("\n");
        
        
        int generations = inputInt("Podaj liczbę pokoleń:");
        
        int weight = inputInt("Podaj max wage plecaka:");
       
       
       
        log.setMaxWeight(weight);
       
        log.start(generations, 1);  
    }
    

    private static int inputInt(String message)
    {
       Scanner input = new Scanner(System.in);
       
       String inputValue = "";
       
       Pattern p = Pattern.compile("[1-9][0-9]*");
       Matcher m = p.matcher(inputValue);
       
       
       while (!m.matches())
       {
           System.out.print(message + " ");
           inputValue = input.next();
           m = p.matcher(inputValue);
       }
       input.close();
       return Integer.parseInt(inputValue);
    }
}
