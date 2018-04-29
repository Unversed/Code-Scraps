package logic;

public class Division implements Function
{
   @Override
   public void operate(float term1, float term2)
   {
      if(term2 != 0)
         System.out.println(term1/term2);   
      else 
         System.out.println("Cannot divide by 0");
      
   }

}
