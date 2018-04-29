package logic;

import java.util.Scanner;

public class FactoryPatternDemo 
{
   public static void main(String args[]) {
      
      Scanner input = new Scanner(System.in);
      
      String operation = input.next();
      
      float term1 = input.nextFloat();
      
      float term2 = input.nextFloat();
      
      FunctionFactory mathFunctions = new FunctionFactory();
      
      Function computer = mathFunctions.getFunction(operation);
      computer.operate(term1, term2);
      
   }
}
