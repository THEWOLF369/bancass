package com.star.Ba.jsf.util;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class FrequencyService {
	
	
	public static String getPreviousDateWith28(Date date, int freq) {

	    LocalDate result;
	    
	    LocalDate effDate = convertToJodaLocalDate(date); 

	    switch (freq) {
	        case 1: // Annual
	            result = effDate.minusYears(1);
	            break;

	        case 2: // Semiannual
	            result = effDate.minusMonths(6);
	            break;

	        case 3: // Quarterly
	            result = effDate.minusMonths(3);
	            break;

	        case 4: // Monthly
	            result = effDate.minusMonths(1);
	            break;

	        case 5: // Prime unique
	            result = effDate;
	            break;

	        default:
	            throw new IllegalArgumentException("Fréquence inconnue");
	    }

	    // 👉 Toujours jour = 28
	    return getDateAsString(result.withDayOfMonth(28));
	}
	


	public static LocalDate getNextDate(LocalDate effDate, int freq) {

	    switch (freq) {
	        case 1: // Annual
	            return effDate.plusYears(1);

	        case 2: // Semiannual
	            return effDate.plusMonths(6);

	        case 3: // Quarterly
	            return effDate.plusMonths(3);

	        case 4: // Monthly
	            return effDate.plusMonths(1);

	        case 5: // Prime unique
	            return effDate; // pas de prochaine date

	        default:
	            throw new IllegalArgumentException("Fréquence inconnue");
	    }
	}
	
	
	
	public static LocalDate convertToJodaLocalDate(Date date) {
	    // Joda-Time accepte directement un long timestamp
	    return new LocalDate(date.getTime());
	}
	
	
	 // Méthode pour convertir LocalDate → String format dd/MM/yyyy
    public static String getDateAsString(LocalDate nextDate) {
       
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        return nextDate.toString(formatter);
    }
    
    
    public static String getNextDateAsString(Date effDate, int freq) {
    	
    	LocalDate jodaEffDate = convertToJodaLocalDate(effDate);
    	
        LocalDate nextDate = getNextDate(jodaEffDate, freq);
        
        // 👉 Forcer le jour à 03
        nextDate = nextDate.withDayOfMonth(3);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        return nextDate.toString(formatter);
    }

}
