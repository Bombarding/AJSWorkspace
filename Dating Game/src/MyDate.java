/**

MyDate
CLASSPATH=$CLASSPATH:.
*/

import java.io.*;
import java.util.*;
import java.util.GregorianCalendar;
import java.util.Calendar;

class MyDate implements DateInterface
{
	int day;
	int month;
	int year;
	int weekday;
	int today;

	  /** @return the day of the month (1-31) */
	  public int getDay()
	  {
		return day;
	  }
	  /** @return the day of the week (0-6) */
	  public int getDow()
	  {
		return weekday;		
	  }
	  /** @return the month of the year (1-12) */
	  public int getMonth()
	  {
		return month;
	  }
	  /** @return the year (four digits) */
	  public int getYear()
	  {
		return year;
	  }
	  /** sets the date
		  @param m the month of the year (1-12)
		  @param d the day of the mongth (1-31)
		  @param y the year (four digits)
		  @param dow the day of the week (0-6) */
	  public void set(int m, int d, int y, int dow)
	  {
		
		month=m;
		day=d;
		year=y;
		weekday=dow;
		
	  }
	  /** moves the date forward by exactly one day, and accounts for leap years */
	  public void tomorrow()
	  {
		day++;
		weekday++;
		if (weekday==7)
		{
			weekday=0;
		}
		if (month==1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
		{
			if (day==32)
			{
				day=1;
				month++;
				if (month==13)
				{
					month=1;
					year++;
				}
			}
		}
		if (month==4 || month == 6 || month == 9 || month == 11)
		{
			if (day==31)
			{
				day=1;
				month++;
			}
		}
		if (month==2)
		{
			if (year%4==0)
			{
				if (year%100!=0)
				{
					if (day==30)
					{
						day=1;
						month++;
					}
				}
				else if (year%100==0)
				{
					if (year%400==0)
					{
						if (day==30)
						{
							day=1;
							month++;
						}
					}
					else if (year%400!=0)
					{
						if (day==29)
						{
							day=1;
							month++;
						}
					}
				}
			}
			else
			{
				if (day==29)
				{
					day=1;
					month++;
				}
			}
		}
	  }
	 
	  /** @return the date as a String in the format "Monday March 18, 2002" */
	  /** Use string Charlie to add script for dates */
	  public String toString()
	  {
		String Charlie="";
		{
			if(month==1){Charlie+="January ";}
			else if (month==2){Charlie+="February ";}
			else if (month==3){Charlie+="March ";}
			else if (month==4){Charlie+="April ";}
			else if (month==5){Charlie+="May ";}
			else if (month==6){Charlie+="June ";}
			else if (month==7){Charlie+="July ";}
			else if (month==8){Charlie+="August ";}
			else if (month==9){Charlie+="September ";}
			else if (month==10){Charlie+="October ";}
			else if (month==11){Charlie+="November ";}
			else if (month==12){Charlie+="December ";}
		}

		Charlie+=" "+day+", ";
		Charlie+=year+" ";	
		{
			if (weekday==0){Charlie+="is a Sunday. ";}
			else if (weekday==1){Charlie+="is a Monday. ";}
			else if (weekday==2){Charlie+="is a Tuesday. ";}
			else if (weekday==3){Charlie+="is a Wednesday. ";}
			else if (weekday==4){Charlie+="is a Thursday. ";}
			else if (weekday==5){Charlie+="is a Friday. ";}
			else if (weekday==6){Charlie+="is a Saturday. ";}
		}
		
		return Charlie;
	  }
		
	  /** sets the date to today; */
	  public void today()
	  {
		Calendar today= Calendar.getInstance(); //Creates A Calendar Named "Today"
		weekday=today.get(Calendar.DAY_OF_WEEK); //Returns Value for Weekday (1-7)
		day=today.get(Calendar.DAY_OF_MONTH); //Returns Value for Day (1-31)
		year=today.get(Calendar.YEAR); //Returns Value for Year
		month=today.get(Calendar.MONTH); //Returns Value for Month (0-11)
		weekday--; //Adjusts for the Difference Between Gregorian Calendar and string "Charlie"
		month++; //Adjusts for the Difference Between Gregorian Calendar and string "Charlie"
		toString();
	  }
	  /** Moves the date backword by exactly one day; , and accounts for leap years */
	  public void yesterday()
	  {
		day=day-1;
		weekday=weekday-1;
		if (weekday==-1)
		{
			weekday=6;
		}
		if (month == 5 || month == 7 || month == 10 || month == 12)
		{
			if (day==0)
			{
				day=30;
				month=month-1;
			}
		}
		if (month==1 || month == 2 || month==4 || month == 6 || month == 8 || month == 9 || month == 11)
		{
			if (day==0)
			{
				day=31;
				month=month-1;
				if (month==0)
				{
					month=12;
					year=year-1;
				}
			}
		}
		if (month==3)
		{
			if (year%4==0)
			{
				if (year%100!=0)
				{
					if (day==0)
					{
						day=29;
						month--;
					}
				}
				else if (year%100==0)
				{
					if (year%400==0)
					{
						if (day==0)
						{
							day=29;
							month--;
						}
					}
					else if (year%400!=0)
					{
						if (day==0)
						{
							day=28;
							month--;
						}
					}
				}
			}
			else
			{
				if (day==0)
				{
					day=28;
					month--;
				}
			}
			
	    }
	 }
	 /*
	 public void Julian()
	 {
		double A = year/100;
		double B = A/4;
		double C = 2-A+B;
		double E = 365.25*(year+4716);
		double F = 30.6001*(month+1);
		double J = C+day+E+F-1524.5;
	 }
	 */
}