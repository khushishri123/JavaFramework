package com.thinking.machines.tmcomponents.swing;
import java.util.*;
import java.time.*;
class TMDatePicker
{
private int [][]getDays(int month,int year)
{
Date firstDayOfMonth=new Date(year-1900,month-1,1);
//Now to find out day of week of first day of month
Calendar firstDayOfMonthCalendar=Calendar.getInstance();
firstDayOfMonthCalendar.setTime(firstDayOfMonth);
int dayOfWeekOfFirstDayOfMonth=firstDayOfMonthCalendar.get(Calendar.DAY_OF_WEEK);

//Now to find out how many days that month have
YearMonth yearMonth=YearMonth.of(year,month);
int numberOfDaysInMonth=yearMonth.lengthOfMonth();

//Now finding date of last Date of Month
Date lastDayOfMonth=new Date(year-1900,month-1,numberOfDaysInMonth);

//Finding day of week of last day of month
Calendar lastDayOfMonthCalendar=Calendar.getInstance();
lastDayOfMonthCalendar.setTime(lastDayOfMonth);
int dayOfWeekOfLastDayOfMonth=lastDayOfMonthCalendar.get(Calendar.DAY_OF_WEEK);

//finding week number of last day of month
int weekNumber=lastDayOfMonthCalendar.get(Calendar.WEEK_OF_MONTH);

//This week Number will become no. of rows and columns will be 7
int days[][]=new int[weekNumber][7];

//Our loop will start from dayOfWeekOfFirstDayOfMonth
int c=dayOfWeekOfFirstDayOfMonth-1;
int r=0;

for(int i=1;i<=numberOfDaysInMonth;i++)
{
days[r][c]=i;
c++;
if(c==7)
{
c=0;
r++;
}
}
return days;
}
public static void main(String gg[])
{
int month=Integer.parseInt(gg[0]);
int year=Integer.parseInt(gg[1]);
TMDatePicker tmdp=new TMDatePicker();
int [][] days=tmdp.getDays(month,year);
for(int r=0;r<days.length;r++)
{
for(int c=0;c<days[r].length;c++)
{
System.out.printf("%2d ",days[r][c]);
}
System.out.printf("\n");
}
}
}