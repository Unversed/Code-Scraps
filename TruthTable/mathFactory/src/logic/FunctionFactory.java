package logic;

public class FunctionFactory {

   public Function getFunction(String operation)
   {
      switch (operation)
      { 
      case "addition":
           return new Addition();
      case "subtraction":
           return new Subtraction();
      case "multiplication":
           return new Multiplication();
      case "division":
           return new Division();
      default:
           System.out.println("invalid operation");
           return null;
      }
   }
}
