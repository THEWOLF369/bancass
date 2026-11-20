/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.txt.avenirjeunesse;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

import com.star.Ba.model.TauxInteret;
import com.star.Ba.service.IParametresAvjService;

/**
 *
 * @author houssema
 */

public class Module1 {
    
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
double Fr_gestion ;
double Fr_acq1 ;
double Fr_acq2 ;
double Fr_acq ;
double i_lt ;
double i_ct ;
double age_ass ;
double age_enf ;
int dur ;
double v ;


 

public Module1(double age_enf){
	
	age_enf=this.age_enf;
}


public double lx(int x) {
  //lx = Sheets(1).Cells(x + 9, 2)  
  int[] tab ={100000,97104,96869,96727,96624,96541,96471,96410,96356,96306,96258,96211,96163,96111,96052,
  95985,95908,95821,95722,95614,95496,95372,95242,95108,94971,94834,94696,94558,94420,94283,94145,
  94007,93867,93724,93578,93426,93268,93102,92926,92739,92538,92323,92089,91837,91562,91263,90937,
  90580,90190,89764,89297,88786,88226,87614,86944,86211,85410,84536,83582,82542,81409,80178,78842,
  77393,75826,74134,72312,70354,68257,66017,63632,61103,58432,55623,52686,49629,46469,43222,39911,
  36560,33200,29861,26580,23390,20328,17428,14722,12238,9997,8013,6292,4832,3623,2647,1876,1286,
  850,539,326,187,101,51,24,10,4,1};
  int val;
   
  
  return (double)tab[x];
}


public double axn(int x  , int n  , int m  , double i  ) {
double v ;
double j ;
v = 1 / (1 + i);
double axn = 0;
j = 0;
while(j < n){
 axn = axn + lx((int) (x + j)) / lx(x) * Math.pow(v,j)    ;
         j = j + 1;
}

return axn;
 
        }


public double axnECh(int x  , int n  , int m  , double i  ) {
int j ;
v = (double)1 / (1 + i);
double axnECh = 0;
j = 1;
while(j < n + 1){
  axnECh = axnECh + ((double) lx(x + j) / lx(x)) * Math.pow(v , j)  ;
          j = j + 1;
}

 return axnECh;
        }
 
   
public double axnm(int x , int n , int m , double i ) {
v = 1 / (1 + i);
return axnECh(x, n, m, i) + ((m + 1) / (2 * m)) * (1 - ((lx(x + n) / lx(x)) * Math.pow(v,n)));
        }


public double axnmECh(int x , int n , int m , double i) {
v = (double)1 / (1 + i);
         
return axnECh(x, n, m, i) + ((double)(m - 1) / (2 * m)) * (1 - (double) lx(x + n) / lx(x) * Math.pow(v , n));
}


public double axnmp(int x , int n , int m , double i, int p ) {
v = (double) 1 / (1 + i);

return axnm(x + p, n, m, i) * (lx(x + p) / lx(x)) * Math.pow(v,p);
        }


public double  axynmp(int x  , int y  , int n  , int m  , double i  , int p  ){
v = (double)1 / (1 + i);
        
return axynm(x + p, y + p, n, m, i) * ((double) lx(x + p) * lx(y + p) / (lx(x) * lx(y))) *  Math.pow(v , p);
}


public double anm(int n  , int m  , double i  ) {

v = (double)1 / (1 + i);
return (double) ((double)1 / m) * (1 - Math.pow(v , n)) / (1 - Math.pow(v , ((double)1 / m)));
        }


public double axyn(int x  , int y  , int n  , double i  ) {
double j ;
v = 1 / (1 + i);
double axyn = 0;
j = 0;
while(j < n){
   axyn = axyn + (lx((int) (x + j)) / lx(x)) * (lx((int) (y + j)) / lx(y)) * (Math.pow(v,j));
   j = j + 1;
}
 return axyn;
        }


public double axynECh(int x  , int y  , int n  , int m  , double i  ) {
int j ;
v = (double)1 / (1 + i);
double axynECh = 0;
j = 1;
while(j < n + 1){
 axynECh = axynECh + ((double) lx(x + j) / lx(x)) * ((double) lx(y + j) / lx(y)) * Math.pow(v , j);
  j = j + 1;  
}
return axynECh;
        }


public double axynm(int x , int y  , int n  , int m  , double i  ) {
v = (double)1 / (1 + i);
  
return axynECh(x, y, n, m, i) + ((double) (m + 1) / (2 * m)) * (1 - ((double) lx(x + n) / lx(x)) * ((double) lx(y + n) / lx(y)) * Math.pow(v , n));
}


public double axynmECh(int x  , int y , int n  , int m , double i  ){
v = (double) 1 / (1 + i);
 
        
return axynECh(x, y, n, m, i) + (double)(m - 1) / (2 * m) * (1 - ((double) lx(x + n) / lx(x)) * ((double) lx(y + n) / lx(y)) * Math.pow(v , n));
}

 

public double dure(int l) {
//age_enf =Sheets(2).Cells(3, 3)
	
return l - age_enf;
        }


public double tmg(int l  ) {
	i_lt = 0.04; //i_lt = 0.035;//Sheets(1).Cells(4, 2)
	i_ct =  0.0362; //i_ct = 0.035;//Sheets(1).Cells(5, 2)
if (dure(l) < 10) 
return i_ct;
else
  return i_lt;
        }


/**** a regarder la fct si dessus ****/
@EJB private IParametresAvjService iParametresAvjService;

private TauxInteret currentTauxInteret;

public TauxInteret getCurrentTauxInteret() {
	return currentTauxInteret;
}
public void setCurrentTauxInteret(TauxInteret currentTauxInteret) {
	this.currentTauxInteret = currentTauxInteret;
}




public double __tmg(int dureeContract  ) {
	 System.out.println("annee="+Calendar.getInstance().get(Calendar.YEAR));
	currentTauxInteret=iParametresAvjService.getTauxInteretByAnnee(2017);
	
	System.out.println("currentTauxInteret= "+currentTauxInteret);
 
  return (dureeContract<=10)?currentTauxInteret.getDureeInf10ans().doubleValue():currentTauxInteret.getDureeSup10ans().doubleValue();
        }

/***************************************************/

public double i_eq(double i , double s  ) {

return (double)(i - s) / (1 + s);

        }

public double d(double i  ) {
return (double) i / (1 + i);
        }

public double dm(double i  , int m  ){
 
return m * (1 - Math.pow((1 + i) , ((double)-1 / m)));
        }



public Double cb(double b  , double d  , int l  , int w , double r  ) {
double a ;
double c ;
if (r == tmg(l))  
return b * (w - l) * (((1 - Math.pow((1 + tmg(l)),-1)) / ((((Math.pow((1 + tmg(l)),((double)1 / 12)))) - 1))));
        else{

a = ((1 - Math.pow((1 + tmg(l)) , (-1))) / (Math.pow((1 + tmg(l)) , ((double)1 / 12)) - 1));
c = ((1 -  Math.pow(1 + ((double)(tmg(l) - r) / (1 + r)),l - w))) / ((double)(tmg(l) - r) / (1 + tmg(l)));

    return b * a * c;
            }
        }

 

public double ci(double b  , double d  , int l  , int w  , double r  ) {
return d * ((double)(1 -  Math.pow(1 + tmg(l),l - w))) / ((double) tmg(l) / (1 + tmg(l)));
        }

public double capital(double b  , double d  , int l  , int w  , double r  ) {
return cb(b, d, l, w, r) + ci(b, d, l, w, r);
        }



/************************ depuis module 2 *************************/

//prime d'épargne
public double pe0(double c  , double i  , int m  , int l  , int y  , double s  , double r  ) {
double a ;
double b ;
double f ;





a = (double)(c *  dm(i, m)) /  d(i);
b = (1 - Math.pow((1 +  i_eq(i, s)) , (y - l))) * Math.pow((1 + i) , (l - y)) * ((1 +  i_eq(i, s)));
f = (l - y) * Math.pow((1 + i) , (l - y));

//'pe0 = a / f

if (i == s)  
return (double)a / f;
else
return (double) a *  i_eq(i, s) / b;


      }


public double pek(double c  , double i  , int m  , int l  , int y  , double s  , double r  , int k  ) {

return ((double)pe0(c, i, m, l, y, s, r) / m) * Math.pow(1 + s,(int)(k / m));
      }



public double pekcomme(double c  , double i  , int m  , int l  , int y  , double s  , double r  , int k , int x    , double lamda1    , double lamda2    ) {

return pek(c, i, m, l, y, s, r, k) * ratiocommi(x, y, l, m, i, s, lamda1, lamda2);
}


public double pupA(int x  , int m  , double i  , double s  , int y  , int l  , double pe0  )  {  
double S1 ;
int j ;
j = 0;
S1 = 0;
double iprim;
iprim = (double)(i - s) / (1 + s);
while(j < l - y){
   
  S1 = S1 + Math.pow((1 + s) , (j)) *  axynmp(x, y, 1, m, i, j);
  j = j + 1;
  
}


if (i == s ) 
return pe0 * (((l - y) *  anm(1, m, i)) - S1);

else
//if (i != s)  
return pe0 * (( anm(l - y, 1, iprim) *  anm(1, m, i)) - S1);

      }



public double papA(int x  , int n  , int m  , double i  , double s  , int y  , int l  , double pe0  ) {

return (double) pupA(x, m, i, s, y, l, pe0) / (( axynm(x, y, (int)((2 * n) / 3), m,  i_eq(i, s))));
      }

public double pppA(int x  , int n  , int m  , double i  , double s  , int y  , int l  , double pe0  , int k  ) {
if (k > (int)(2 * m * (l - y) / 3) - 1) 
return 0;
else
return ((double)papA(x, n, m, i, s, y, l, pe0) / m) * Math.pow((1 + s) , (int)(k / m));

      }


public double pppAcomm(int x  , int n  , int m  , double i  , double s  , int y  ,int l  , double pe0  , int k  , double lamda1  , double lamda2  ) {
return pppA(x, n, m, i, s, y, l, pe0, k) * ratiocommi2_3(x, y, l, m, i, s, lamda1, lamda2);
      }

public double pppcomm(double c  ,double r ,double alpha  , int x  , int n  , int m  ,double i  , double s , int y  , int l  ,double  pe0  , int k  ,double  lamda1 ,double lamda2  ) {
return pppAcomm(x, n, m, i, s, y, l, pe0, k, lamda1, lamda2) + pekcomme(c, i, m, l, y, s, r, k, x, lamda1, lamda2) + FraisComme(c, alpha, x, y, l, i, s, m, k, lamda1, lamda2);
}

public double pupB(int x  , int y  , int l  ,double i  ,double s  ,double g  ) {
return g * 12 * ( axnmECh(y, l - y, 12, i) -  axynmECh(x, y, l - y, 12, i));

}

public double papB(int x  ,int y  ,int l  ,double i ,double s  ,double g  ,int m  ) {
return pupB(x, y, l, i, s, g) /  axynm(x, y, (int)(2 * (l - y) / 3), m,  i_eq(i, s));
      }



public double pppB(int x , int y  , int l  , double i  , double s  , double g  , int m  , int k  ) {
if (k > (int)(2 * m * (l - y) / 3) - 1) 
return 0;
else
return (papB(x, y, l, i, s, g, m) / m) * Math.pow((1 + s) , (int)(k / m));

}

public double pppBcomme(int x  , int y  , int l  , double i  , double s  , double g  , int m  , int k  , double lamda1 , double lamda2  ) {

return pppB(x, y, l, i, s, g, m, k) * ratiocommi2_3(x, y, l, m, i, s, lamda1, lamda2);

}



public double Fraisannul(double c  ,double alpha , int x  ,int y  ,int l  ,double i  ,double s  ,int m  ,int k  ) {
if(s == 0 )
return (double)(c * alpha *  axyn(x, y, l - y, i)) / ( axynm(x, y, l - y, m,  i_eq(i, s)));
else
return (double)(c * alpha *  axyn(x, y, l - y, i)) / ( axynm(x, y, (int)(2 * (l - y) / 3), m,  i_eq(i, s)));

      }

public double Frais1(double c  ,double alpha  , int x  , int y  , int l  ,double i ,double s  ,int m  ,int k  ) {
if(s == 0 )
return (double) Fraisannul(c, alpha, x, y, l, i, s, m, k) * (Math.pow((1 + s) ,(int)(k / m))) / m;
else{
if( k > (int)(2 * m * (l - y) / 3) - 1 )
return 0;
else
return (double) Fraisannul(c, alpha, x, y, l, i, s, m, k) * (Math.pow((1 + s) , (int)(k / m))) / m;

      }

}

public double FraisComme(double c  ,double alpha  ,int x  ,int y  ,int l  ,double i  ,double s  ,int m  ,int k  ,double lamda1  ,double lamda2  ) {
if(s == 0)
return Frais1(c, alpha, x, y, l, i, s, m, k) * ratiocommi(x, y, l, m, i, s, lamda1, lamda2);
else{
if (k > (int)(2 * m * (l - y) / 3) - 1 )
return 0;
else
return Frais1(c, alpha, x, y, l, i, s, m, k) * ratiocommi2_3(x, y, l, m, i, s, lamda1, lamda2);

      }

      }




public double ratiocommi(int x  ,int y  ,int l  ,int m  ,double i  ,double s  ,double lamda1 ,double lamda2  ) {
double lamda ;
lamda = ((double)lamda1 /  axynm(x, y, l - y, m,  i_eq(i, s))) + lamda2;
return (double)1 / (1 - lamda);

}

public double ratiocommi2_3(int x  , int y  ,int l  ,int m  ,double i  ,double s  ,double lamda1 ,double lamda2 ) {
double lamda;
lamda = ((double) lamda1 /  axynm(x, y, (int)((l - y) * 2 / 3), m,  i_eq(i, s))) + lamda2;
return   1 / (1 - lamda);

      }

/************************ depuis module 3 *************************/

public Date ConvertStringToDate(String sDate)  {
    
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
     Date date = null;
    try {
        date= df.parse(sDate);
    } catch (ParseException ex) {
        Logger.getLogger(Module1.class.getName()).log(Level.SEVERE, null, ex);
    }
return date;
}


public Date DateAdd(String x,int duree,Date Date_effet){
    Calendar c = Calendar.getInstance();
    c.setTime(Date_effet);
    
    if(x.equalsIgnoreCase("yyyy")) 
        c.add(Calendar.YEAR, duree);
          
    if(x.equalsIgnoreCase("m"))
        c.add(Calendar.MONTH, duree);
         
     return c.getTime();
}

public Date date_fin(Date Date_effet,int duree){
    
    Date resultat = DateAdd("yyyy",duree,Date_effet);
    System.out.println("Full Date : " + resultat);
    
    return resultat;
}


public Date date_eche (Date Date_effet  , int duree  ,int k  , int m ){
    
    int j ;
    j = (m * duree);
    if (j > k ) return DateAdd("m", k * 12 / m, Date_effet);
            else {
        
        return ConvertStringToDate("01/01/1900");
    }
}



public int t_an(Date Date_effet  , Date date_inve)  {
        int j_effet;
        int m_effet ;
        int a_effet ;

        int j_inve ;
        int m_inve ;
        int a_inve ;

        Calendar cal = Calendar.getInstance();
        cal.setTime(Date_effet);

        j_effet = cal.get(Calendar.DAY_OF_MONTH);
        m_effet =  cal.get(Calendar.MONTH);
        a_effet = cal.get(Calendar.YEAR);

        cal.setTime(date_inve);

        j_inve = cal.get(Calendar.DAY_OF_MONTH);
        m_inve = cal.get(Calendar.MONTH);
        a_inve = cal.get(Calendar.YEAR);

        if (j_inve == 31) j_inve = 30;
        //else
        //j_inve = j_inve;

        if (j_effet == 31) j_effet = 30;
        //else
        //j_effet = j_effet;

        return (int)(((a_inve - a_effet) * 360 + (m_inve - m_effet) * 30 + (j_inve - j_effet)) / 360);

    }


public int moisDiff(Date date_eche  , Date dateinvent  , int m) {
    
    DateTime dt1 = new DateTime(date_eche);
DateTime dt2 = new DateTime(dateinvent);

    int month = Months.monthsBetween(dt1, dt2).getMonths();

    System.out.println("Full Date : " + month);
    return month;//DateDiff("m", date_eche, dateinvent);
    
    }

public int jourDiff(Date date_eche  , Date dateinvent  , int m) {
    
    DateTime dt1 = new DateTime(date_eche);
DateTime dt2 = new DateTime(dateinvent);

    int day = Days.daysBetween(dt1, dt2).getDays();

    System.out.println("Full Date : " + day);
    return day;//DateDiff("m", date_eche, dateinvent);
    
    }



public int ancient_police(Date date_ech   , Date dateinevent  ,int m  ) {
    
        int a ;
        int b ;
        int c ;
        int d ;
        int w ;

        DateTime dt1 = new DateTime(date_ech);
        DateTime dt2 = new DateTime(dateinevent);

        a = dt1.getMonthOfYear();
        b = dt2.getMonthOfYear();
        c = dt1.getYear();
        d = dt2.getYear();
        w = (moisDiff(date_ech, dateinevent, m) * m / 12);

        if (a <= b) return d - c;
        else
        return d - c - 1;

    }


public Date date_annive(Date Date_effet  , Date dateinevent  ,int m  ) { 
    int j ;
    int k ;
    k = t_an(Date_effet, dateinevent);
    ///////////////////////////////////////////à voir
    //j = (m * duree);  

    return DateAdd("yyyy", k, Date_effet);

    }


public double alpha(Date Date_effet , Date dateinevent  , int m  ) {
    int a ;
    int b ;
    int c ;
    int d ;
    int e ;
    int f ;
    int f1 ;
    int w ;
    int k ;
    k = ancient_police(Date_effet, dateinevent, m);
    Date ww = date_annive(Date_effet, dateinevent, m);

     DateTime dtww = new DateTime(ww);
     DateTime dtdateinevent = new DateTime(dateinevent);

    a = dtww.getMonthOfYear();
    b = dtdateinevent.getMonthOfYear();
    c = dtww.getYear();
    d = dtdateinevent.getYear();
    e = dtww.getDayOfMonth();
    f = dtdateinevent.getDayOfMonth();

    return (((d - c) * 360 + (b - a) * 30 + f - e) / 360);

    }


public int numéro_pai(Date Date_effet  , Date dateinevent  , int m  ) {

    return (int) ((t_an(Date_effet, dateinevent)) * m + ((alpha(Date_effet, dateinevent, m)) * m));

    }


/************************ depuis module 4 *************************/
/*
public double pupA(int x  , int m  , double i  , double s  , int y  , int l  , double pe0  ){
    double t = 0;
    return t;
}  
////predefi mod1

public double papA(int x , int n  , int m  , double i  , double s  , int y  , int l  , double pe0){
double t = 0;
return t;
}  
////predefi mod1

public double axynm(int x , int y  , int n  , int m  , double i){
double t = 0;
return t;
}  
////predefi mod1

public double pupB(int x  , int y  , int l  , double i  , double s  ,double g  ){
double t = 0;
return t;
}  
////predefi mod2

public double papB(int x , int y  , int l  , double i  , double s  , double g ,int m  ){
double t = 0;
return t;
}  
////predefi mod2


public double Fraisannul(double c  , double alpha  , int x  , int y  , int l  , double i , double s  , int m  , int k  ){
double t = 0;
return t;
}  
////predefi mod2

public double axyn(int x  , int y  , int n  , double i  ){
double t = 0;
return t;
}  
////predefi mod1

*/

public double proviA(double pe0  , double i  , int x  , int y  , int l  , double s , int m  , int k  ) { 
double a ;
double d ;
double ieq ;
int w ;

ieq = (i - s) / (1 + s);
w = k - (int)((l - y) * 2 / 3);
a = (pupA(x + k, m, i, s, y + k, l, pe0)) * Math.pow((1 + s),k)   ;

if(w < 0) {  
d = papA(x, l - y, m, i, s, y, l, pe0) * axynm(x + k, y + k, ((l - y) * 2 / 3) - k, m, ieq) * Math.pow((1 + s),k);
return a - d;
        }
else
return a;

}


public double proviB(double g  , double i  , int x  , int y  , int l  , double s , int m  , int k ) {
double a   ;
double d   ;
double ieq ;
int j ;

ieq = (i - s) / (1 + s);
a = pupB(x + k, y + k, l, i, s, g);
j = k - (((l - y) * 2 / 3));

if (j < 0){
   d = (papB(x, y, l, i, s, g, m) * Math.pow((1 + s),k)) * axynm(x + k, y + k, ((int)((l - y) * 2 / 3)) - k, m, ieq);
return a - d ;
}

else
return a;

}



public double prov_frais(double c , double alpha  , int x  , int y  , int l  , double i  , double s  , int m  , int k  ) {
double a ;
double b ;
double b1 ;
double d ;
double ieq ;
int w ;
ieq = (i - s) / (1 + s);
w = k - ((int)((l - y) * 2 / 3));


b1 = Fraisannul(c, alpha, x, y, l, i, s, m, 0) * Math.pow((1 + s),k) * axynm(x + k, y + k, l - y - k, m, ieq);
a = c * alpha * axyn(x + k, y + k, l - y - k, i);


if( s == 0)  
return a - b1;
else {

if (w < 0){
   b = Fraisannul(c, alpha, x, y, l, i, s, m, 0) * Math.pow((1 + s),k) * (axynm(x + k, y + k, ((int)((l - y) * 2 / 3)) - k, m, ieq));
return  a - b; 
}

else
return a;
}
}



public double  provi_dateA(double nbres  , double pe0 , double i  , int x  , int y  , 
    int l  , double s  , int m  , Date Date_effet  , Date dateinevent   ) {

double alp ;
int k = 0 ; /// à voir
Date w ;
double a ;
double d ;
double c ;
int yy ;
int z ;
double b;  /// à voir  non déf

yy = (int) (((t_an(Date_effet, dateinevent) + 1) * m) - nbres);

z = yy - (int)(m * (l - y) * 2 / 3);

w = date_eche(Date_effet, l - y, k, m); // à voir k = 0
k = t_an(Date_effet, dateinevent);
alp = alpha(Date_effet, dateinevent, m);


a = proviA(pe0, i, x, y, l, s, m, k);
b = (papA(x, l - y, m, i, s, y, l, pe0) * Math.pow((1 + s),k));
c = proviA(pe0, i, x, y, l, s, m, k + 1);

if (z < 0) 

return((1 - alp)) * (a + b) + (alp * c) - (nbres / m) * b;
else
return ((1 - alp)) * (a) + (alp * c);

}



public double provi_dateB(double nbres  , double g  , double i  , int x  , int y  , int l  , double s  , int m  , Date Date_effet  , Date dateinevent  ) {
double alp ;
int k = 0 ;  // a voir k
Date w ;
double a ;
double d ;
double c ;
int yy  ;
int z ;
double b; // à voir

yy = (int) (((t_an(Date_effet, dateinevent) + 1) * m) - nbres);
z = yy - (int)(m * (l - y) * 2 / 3);
w = date_eche(Date_effet, l - y, k, m); // à voir k
k = t_an(Date_effet, dateinevent);
alp = alpha(Date_effet, dateinevent, m);
a = proviB(g, i, x, y, l, s, m, k);
b = (papB(x, y, l, i, s, g, m) * Math.pow((1 + s),k));
c = proviB(g, i, x, y, l, s, m, k + 1);
if (z < 0)  
return ((1 - alp)) * (a + b) + (alp * c) - (nbres / m) * b;
else
return ((1 - alp)) * (a) + (alp * c);

}




public double provi_dateFrais(double nbres  , double cap  , double alpha_F  , 
    double i  , int x  , int y  , int l  , double s  , int m  , 
    Date Date_effet  , Date dateinevent  ) {
double alp ;
int k = 0 ; // a voir k
Date w ;
double a ;
double d ;
double c ;
int yy ;
int z ;
double b; // a voir

yy = yy = (int) (((t_an(Date_effet, dateinevent) + 1) * m) - nbres);
z = yy - (int)(m * (l - y) * 2 / 3);
w = date_eche(Date_effet, l - y, k, m); // a voir k 
k = t_an(Date_effet, dateinevent);
alp = alpha(Date_effet, dateinevent, m);
a = prov_frais(cap, alpha_F, x, y, l, i, s, m, k);
b = (Fraisannul(cap, alpha_F, x, y, l, i, s, m, 0) * Math.pow((1 + s),k));
c = prov_frais(cap, alpha_F, x, y, l, i, s, m, k + 1);
if (s == 0) 
return((1 - alp)) * (a + b) + (alp * c) - nbres * b;
else{
if (z < 0) 
return ((1 - alp)) * (a + b) + (alp * c) - (nbres / m) * b;
else
return((1 - alp)) * (a) + (alp * c);

        }

}


/************************ depuis module 5 *************************/

public double ipb(int annee  ) {
    return 0;//  Sheets("A").Cells(annee - 1993, 2);
 } /////////////////////////////////////////////////////////// a voir 
 

 
 public double Aj(Date Date_prime   , Date Date_calcul    ) {
     int ann_prim =0 ;
     int ann_calc =0 ;
     int mois_prim ;
     int mois_cal ;
     int jou_prim ;
     int jou_cal ;
     double a   ;
     double b   ;
     int w ;
     int j ;
     double prod ;

     w = ann_calc - ann_prim;

     DateTime dtDate_calcul = new DateTime(Date_calcul);
     DateTime dtDate_prime = new DateTime(Date_prime);

     ann_calc = dtDate_calcul.getYear();  
     mois_cal = dtDate_calcul.getMonthOfYear(); 
     jou_cal =  dtDate_calcul.getDayOfMonth();   
     if (jou_cal == 31)  
     jou_cal = 30;
     //else
     //jou_cal = jou_cal

     ann_prim = dtDate_prime.getYear();  
     mois_prim = dtDate_prime.getMonthOfYear(); 
     jou_prim =  dtDate_prime.getDayOfMonth();   
     if (jou_prim == 31)  
     jou_prim = 30;
     //Else
     //jou_prim = jou_prim
     //End If

     a = (30 * (mois_cal - mois_prim) + (jou_cal - jou_prim)) / 360;
     b = (30 * (12 - mois_prim) + (30 - jou_prim)) / 360;
             int c = (30 * (mois_cal - 1) + (jou_cal)) / 360;

     if (ann_prim == ann_calc)  
     return Math.pow((1 + ipb(ann_calc)),a);
     else if (ann_prim == ann_calc - 1 )
     return Math.pow((1 + ipb(ann_prim)),b) * Math.pow((1 + ipb(ann_calc)),c);
     else{

         j = 1;
     prod = 1;
     while (j < ann_calc - ann_prim) {
                 prod = prod * (1 + ipb(ann_prim + j));
                 j = j + 1;
             }

     return Math.pow((1 + ipb(ann_prim)),b) * prod * Math.pow((1 + ipb(ann_calc)),c);

}

     }

 
 
 
 public double pme(double pe0  , int dur  , double s  , int alp  , int m  , Date Date_effet  , Date Date_calcul  ) {

double b ;
int j ;
int k ;
int c ;
c = t_an(Date_effet, Date_calcul);
b = 0;
//'c = Aj(date_eche(Date_effet, dur, 1, m), Date_calcul)
//'d = Aj(date_eche(Date_effet, dur, 2, m), Date_calcul)
k = (t_an(Date_effet, Date_calcul) + 1) * m - alp;
for(j=0;j<k-1;j++){
b = b + (Math.pow((1 + s),(int)(j / m))) * Aj(date_eche(Date_effet, dur, j, m), Date_calcul) ; 
}

if (Date_calcul == date_eche(Date_effet, dur, k, m) )    
return b * pe0 / m + ( pe0 * Math.pow((1 + s),(int)(k / m)) ) / m;
else
return b * pe0 / m;

     }
 
}
