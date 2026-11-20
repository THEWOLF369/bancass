package com.star.Ba.commun;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

 
public class AgeCalculator
{
   public  static Age calculateAge(Date birthDate)
   {
      int years = 0;
      int months = 0;
      int days = 0;
      //create calendar object for birth day
      Calendar birthDay = Calendar.getInstance();
      birthDay.setTimeInMillis(birthDate.getTime());
      //create calendar object for current day
      long currentTime = System.currentTimeMillis();
      Calendar now = Calendar.getInstance();
      now.setTimeInMillis(currentTime);
      //Get difference between years
      years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
      int currMonth = now.get(Calendar.MONTH) + 1;
      int birthMonth = birthDay.get(Calendar.MONTH) + 1;
      //Get difference between months
      months = currMonth - birthMonth;
      //if month difference is in negative then reduce years by one and calculate the number of months.
      if (months < 0)
      {
         years--;
         months = 12 - birthMonth + currMonth;
         if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
            months--;
      } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
      {
         years--;
         months = 11;
      }
      //Calculate the days
      if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
         days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
      else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
      {
         int today = now.get(Calendar.DAY_OF_MONTH);
         now.add(Calendar.MONTH, -1);
         days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
      } else
      {
         days = 0;
         if (months == 12)
         {
            years++;
            months = 0;
         }
      }
      //Create new Age object 
      return new Age(days, months, years);
   }
   
   public  static Age calculateDifference(Date dateEffet, Date dateEcheance)
   {
      int years = 0;
      int months = 0;
      int days = 0;
      //create calendar object for birth day
      Calendar effet = Calendar.getInstance();
      effet.setTimeInMillis(dateEffet.getTime());
      //create calendar object for current day
      Calendar echeance = Calendar.getInstance();
      echeance.setTimeInMillis(dateEcheance.getTime());
      //Get difference between years
      years = echeance.get(Calendar.YEAR) - effet.get(Calendar.YEAR);
      int currMonth = echeance.get(Calendar.MONTH) + 1;
      int birthMonth = effet.get(Calendar.MONTH) + 1;
      //Get difference between months
      months = currMonth - birthMonth;
      //if month difference is in negative then reduce years by one and calculate the number of months.
      if (months < 0)
      {
         years--;
         months = 12 - birthMonth + currMonth;
         if (echeance.get(Calendar.DATE) < effet.get(Calendar.DATE))
            months--;
      } else if (months == 0 && echeance.get(Calendar.DATE) < effet.get(Calendar.DATE))
      {
         years--;
         months = 11;
      }
      //Calculate the days
      if (echeance.get(Calendar.DATE) > effet.get(Calendar.DATE))
         days = echeance.get(Calendar.DATE) - effet.get(Calendar.DATE);
      else if (echeance.get(Calendar.DATE) < effet.get(Calendar.DATE))
      {
         int today = echeance.get(Calendar.DAY_OF_MONTH);
         echeance.add(Calendar.MONTH, -1);
         days = echeance.getActualMaximum(Calendar.DAY_OF_MONTH) - effet.get(Calendar.DAY_OF_MONTH) + today;
      } else
      {
         days = 0;
         if (months == 12)
         {
            years++;
            months = 0;
         }
      }
      //Create new Age object 
      return new Age(days, months, years);
   }
   
   
   public static void main(String []args) throws ParseException{
       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date birthDate = sdf.parse("03/07/1989"); 
      Age age = calculateAge(new Date());
      //My age is
      System.out.println(age.getDays());
      String a=null; 
      System.out.println(a==null);
      
      Date dateEffet = sdf.parse("06/09/2018");
      Date dateEcheance = sdf.parse("07/12/2018"); 
      
      Age age2=calculateDifference(dateEffet, dateEcheance);
      System.out.println("day"+age2.getDays());
      System.out.println("MONTH"+age2.getMonths());
      System.out.println("year"+age2.getYears());
      
      
   }
}