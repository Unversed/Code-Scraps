package logic;

public class Multiplication implements Function
{
   @Override
   public void operate(float term1, float term2) 
   {
      System.out.println(term1*term2); 
   }

}
