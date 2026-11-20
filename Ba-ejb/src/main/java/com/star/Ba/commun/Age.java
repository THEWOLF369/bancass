/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.commun;

/**
 *
 * @author star
 */
public class Age {

    private int days;
    private int months;
    private int years;

    private Age() {
        //Prevent default constructor
    }

    public Age(int days, int months, int years) {
        this.days = days;
        this.months = months;
        this.years = years;
    }

    public int getDays() {
        return this.days;
    }

    public int getMonths() {
        return this.months;
    }

    public int getYears() {
        return this.years;
    }

    @Override
    public String toString() {
        String ageString="";
        if(years==1)
        {
            ageString+= years + " An ";
        }else if(years>1){
            ageString+= years + " Ans ";
        }
        if(months!=0){
            ageString+= months + " Mois ";
        }
        if(days!=0)
        {
            ageString+= days + " Jours ";
        }
        return ageString;
    }
}