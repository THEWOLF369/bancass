package com.star.Ba.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

 



import com.star.Ba.model.TauxInteret;


@Stateless
public class Module1Service implements Module1Impl {
	
	
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
	
	
	
	
	
//	public Module1Service(double age_enf) {
//		 
//		this.age_enf = age_enf;
//	}

	@Override
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

	@Override
		public double axn(int x  , int n  , int m  , double i  ) {
		double v ;
		int j ;
		v = (double)1 / (1 + i);
		double axn = 0;
		j = 0;
		while(j < n){
		 axn = axn + (double)lx(x + j) / lx(x) * Math.pow(v,j)    ;
		         j = j + 1;
		}

		return axn;
		 
		        }

	@Override
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
		 
	@Override  
		public double axnm(int x , int n , int m , double i ) {
		v = (double)1 / (1 + i);
		return axnECh(x, n, m, i) + ((double)(m + 1) / (2 * m)) * (1 - (((double)lx(x + n) / lx(x)) * Math.pow(v,n)));
		        }

	@Override
		public double axnmECh(int x , int n , int m , double i) {
		v = (double)1 / (1 + i);
		         
		return axnECh(x, n, m, i) + ((double)(m - 1) / (2 * m)) * (1 - (double) lx(x + n) / lx(x) * Math.pow(v , n));
		}

	@Override
		public double axnmp(int x , int n , int m , double i, int p ) {
		v = (double) 1 / (1 + i);

		return axnm(x + p, n, m, i) * ((double)lx(x + p) / lx(x)) * Math.pow(v,p);
		        }

	@Override
		public double  axynmp(int x  , int y  , int n  , int m  , double i  , int p  ){
		v = (double)1 / (1 + i);
		        
		return axynm(x + p, y + p, n, m, i) * ((double) lx(x + p) * lx(y + p) / (lx(x) * lx(y))) *  Math.pow(v , p);
		}

	@Override
		public double anm(int n  , int m  , double i  ) {

		v = (double)1 / (1 + i);
		return (double) ((double)1 / m) * (1 - Math.pow(v , n)) / (1 - Math.pow(v , ((double)1 / m)));
		        }

	@Override
		public double axyn(int x  , int y  , int n  , double i  ) {
		int j ;
		v = (double)1 / (1 + i);
		double axyn = 0;
		j = 0;
		while(j < n){
		   axyn = axyn + (lx(x + j) / lx(x)) * (lx(y + j) / lx(y)) * (Math.pow(v,j));
		   j = j + 1;
		}
		 return axyn;
		        }

	@Override
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

	@Override
		public double axynm(int x , int y  , int n  , int m  , double i  ) {
		v = (double)1 / (1 + i);
		  
		return axynECh(x, y, n, m, i) + ((double) (m + 1) / (2 * m)) * (1 - ((double) lx(x + n) / lx(x)) * ((double) lx(y + n) / lx(y)) * Math.pow(v , n));
		}

	@Override
		public double axynmECh(int x  , int y , int n  , int m , double i  ){
		v = (double) 1 / (1 + i);
		 
		        
		return axynECh(x, y, n, m, i) + (double)(m - 1) / (2 * m) * (1 - ((double) lx(x + n) / lx(x)) * ((double) lx(y + n) / lx(y)) * Math.pow(v , n));
		}

		 
	@Override
		public double dure(int l,int ageEnf) {
		//age_enf =Sheets(2).Cells(3, 3)
			
		return l - ageEnf;
		        }

	//@Override
		public double _tmg(int l ,int ageEnf ) {
			i_lt = 0.04; //i_lt = 0.035;//Sheets(1).Cells(4, 2)
			i_ct =  0.0362; //i_ct = 0.035;//Sheets(1).Cells(5, 2)
		if (dure(l,ageEnf) < 10) 
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



		@Override
		public double tmg(int dureeContract ,int ageEnf ) {
			 System.out.println("annee="+Calendar.getInstance().get(Calendar.YEAR));
			currentTauxInteret=iParametresAvjService.getTauxInteretByAnnee(2017);
			
			System.out.println("getDureeInf10ans= "+currentTauxInteret.getDureeInf10ans().doubleValue());
			System.out.println("getDureeSup10ans= "+currentTauxInteret.getDureeSup10ans().doubleValue());
		 
		  return (dure(dureeContract,ageEnf)<=10)?(double)currentTauxInteret.getDureeInf10ans().doubleValue()/100:(double)currentTauxInteret.getDureeSup10ans().doubleValue()/100;
		        }

		/***************************************************/
		@Override
		public double i_eq(double i , double s  ) {

		return (double)(i - s) / (1 + s);

		        }
		@Override
		public double d(double i  ) {
		return (double) i / (1 + i);
		        }
		@Override
		public double dm(double i  , int m  ){
		 
		return m * (1 - Math.pow((1 + i) , ((double)-1 / m)));
		        }


		@Override
		public Double cb(double b  , double d  , int l  , int w , double r ,int ageEnf ) {
		double a ;
		double c ;
		if (r == tmg(l,ageEnf))  
		return b * (w - l) * (((1 - Math.pow((1 + tmg(l,ageEnf)),-1)) / ((((Math.pow((1 + tmg(l,ageEnf)),((double)1 / 12)))) - 1))));
		        else{

		a = ((1 - Math.pow((1 + tmg(l,ageEnf)) , (-1))) / (Math.pow((1 + tmg(l,ageEnf)) , ((double)1 / 12)) - 1));
		c = ((1 -  Math.pow(1 + ((double)(tmg(l,ageEnf) - r) / (1 + r)),l - w))) / ((double)(tmg(l,ageEnf) - r) / (1 + tmg(l,ageEnf)));

		    return b * a * c;
		            }
		        }

		 
		@Override
		public double ci(double b  , double d  , int l  , int w  , double r  ,int ageEnf) {
		return d * ((double)(1 -  Math.pow(1 + tmg(l,ageEnf),l - w))) / ((double) tmg(l,ageEnf) / (1 + tmg(l,ageEnf)));
		        }
		@Override
		public double capital(double b  , double d  , int l  , int w  , double r ,int ageEnf ) {
		return cb(b, d, l, w, r,ageEnf) + ci(b, d, l, w, r,ageEnf);
		        }


		
		
		
		/************************** new ***********************************/
		
		public double pow(double a, double b){
			
			return Math.pow(a,b);
		}
		
		@Override
		public double CapitalCb_Unit01(int l  , int duree_bourse  , double bourse_Mensuelle,   double i  , double r  ) {
			//'l: Age de début de service de(s) bourse(s)
			//'duree_bourse: Durée de service de(s) bourse(s)
			//'w : Age de fin de service de(s) bourse
			double w ;
			double C1 ;
			double C2 ;
			double C3 ;
			double C4 ;

		w = l + duree_bourse;
		//'i taux technique
		//' r = Taux de Croissance de la bourse
		C1 = ((1 - pow((1 + i) , (-1))));
		C2 = (pow((1 + i) , ((double)1 / 12)) - 1);
		C3 = 1 - pow((1 + (double)(i - r) / (1 + r)) , (l - w));
		C4 = (double)(i - r) / (1 + r);

		double CapitalCb_Unit01 = 0;

		if (i != r) {
		CapitalCb_Unit01 = bourse_Mensuelle * ((double)C1 / C2) * ((double)C3 / C4);
		}
		if (i == r) {
		CapitalCb_Unit01 = bourse_Mensuelle * ((double)C1 / C2) * (w - l);
		}

		return CapitalCb_Unit01;
		}

		
		@Override
		public double  CapitalCb_Unit03(double Frais_annuels , int duree_bourse , double i ) {
			return  Frais_annuels * aAv_nmi(duree_bourse, 1, i);
			}
		
		
		
		public double aAv_nmi(int n , int m, double i ) { //'ajouter le 10/04/2017
			double dm ;
		v = (double)1 / (1 + i);
		dm = m * (1 - pow((1 + i) , ((double)-1 / m)));
		return (double)(1 - pow(v , n)) / dm;
		}

		
		@Override
		public double PAC_Unit01(double CB  , double s  , int x  , int y  , int l  , double i  , int m  , double g , double gamma1  , double gamma2  ) {
			double i_prim ;
			double gamma_n ;
			double gamma_P ;
		i_prim =(double) (i - s) / (1 + s);
		int p ;
		p = (int)(2 * (l - y) / 3);
		gamma_n = (double)gamma1 / axynmECh(x, y, l - y, m, i_prim) + gamma2;
		gamma_P = (double)gamma1 / axynmECh(x, y, p, m, i_prim) + gamma2;

		double PAC_Unit01 = 0;
		if (s == 0) {
		PAC_Unit01 = (double)PAInv_Unit01(CB, s, x, y, l, i, m, g, gamma1, gamma2) / (1 - gamma_n);
		}

		if (s != 0) {
		PAC_Unit01 = (double)PAP_Unit01(CB, s, x, y, l, i, m, g, gamma1, gamma2) / (1 - gamma_n) + (double) g * CB * axynmECh(x, y, l - y, 1, i) / axynmECh(x, y, p, m, i_prim) / (1 - gamma_P);
		}

		return PAC_Unit01;
		}
		
		
		public double PAInv_Unit01(double CB  , double s , int x  , int y  , int l  , double i  , int m  , double g  , double gamma1  , double gamma2  ) {
			
			double i_prim ;
			int p ;
			
		p = (l - y);
		if (s > 0) {
		p = (int)(2 * (l - y) / 3);
		}

		i_prim = (double)(i - s) / (1 + s);

		double PAInv_Unit01 = 0;

		if (s == 0) {
		PAInv_Unit01 = PAP_Unit01(CB, s, x, y, l, i, m, g, gamma1, gamma2) + (double) g * CB * axynmECh(x, y, l - y, 1, i) / axynmECh(x, y, l - y, m, i_prim);
		}

		if (s != 0) {
		PAInv_Unit01 = PAP_Unit01(CB, s, x, y, l, i, m, g, gamma1, gamma2) + (double) g * CB * axynmECh(x, y, l - y, 1, i) / axynmECh(x, y, p, m, i_prim);
		}

		return PAInv_Unit01;

		}

		
		public double PAP_Unit01(double CB   , double s  , int x  , int y  , int l  , double i  ,int m  , double g  , double gamma1  , double gamma2  ) {
			double dm ;
			double D ;
			double i_prim ;
			double P1 ;
			double P2 ;
			
		dm = m * (1 - pow((1 + i) , ((double)-1 / m)));

		D = (double)i / (1 + i);
		i_prim = (double)(i - s) / (1 + s);

		double PAP_Unit01 = 0;

		if (i != s) {
		P1 = (1 - pow((1 + i_prim) , (y - l)));
		P2 = ((1 + i_prim) * pow((1 + i) , (l - y)));
		double P3 ;
		P3 = (CB * i_prim * dm);
		double P4 ;
		P4 = (P1 * P2 * D);
		PAP_Unit01 = (double)P3 / P4;
		}

		if (i == s) {
		PAP_Unit01 = (double)(CB * dm) / ((l - y) * pow((1 + i) , (l - y)) * D);
		}

		return PAP_Unit01;

		}


		@Override
		public double  PAC_Unit02(double CB , double s , int x , int y , int l , double i , int m , double g , double gamma1 , double gamma2 , int p ) {
			double i_prim;
			double gamma ;
			i_prim = (double)(i - s) / (1 + s);
			gamma = (double)gamma1 / axynmAv(x, y, p, m, i_prim) + gamma2;
			return (double)PAInv_Unit02(CB, s, x, y, l, i, m, g, gamma1, gamma2, p) / (1 - gamma);
		}

		public double axynmAv(int x  , int y  , int n  ,double m  ,double i  ) {//'ajouter le 10/04/2017
			return axynEchue(x, y, n, i) + (double)(m + 1) / (2 * m) * (1 - Exyn(x, y, n, i));
		}

		public double axynEchue(int x  , int y , int n  , double i  ) { //ajouter le 10/04/2017
			int j ;
			double axynEchue = 0;
			v = (double)1 / (1 + i);
			j = 1;

			while(j < n+1){
				axynEchue = axynEchue + Exyn(x, y, j, i);
				j = j + 1;
			}
			return axynEchue;
		}

		public double Exyn(int x, int y, int n,  double i)  {
			v = (double)1 / (1 + i);
			return ((double)lx(x + n) / lx(x)) * ((double)lx(y + n) / lx(y)) * Math.pow(v , n);
		}


		public double  PAInv_Unit02(double CB , double s , int x , int y , int l , double i , int m , double g , double gamma1 , double gamma2 , int p ) {
			return PAP_Unit02(CB, s, x, y, l, i, m, g, gamma1, gamma2, p);
		}

		public double  PAP_Unit02(double CB  , double s  , int x  , int y  , int l, double i  , int m , double g  , double gamma1  , double gamma2  , int p  ){

			double PEpargneAnnuelle_Unit01 ;

			PEpargneAnnuelle_Unit01 = PAP_Unit01(CB, s, x, y, l, i, m, g, gamma1, gamma2);

			double i_prim ;
			double PUP_Unit02 = 0 ;

			i_prim = (double)(i - s) / (1 + s);
			int j ;
			double somme ;
			somme = 0;
			j = 0;

			while(j<l - y){
				somme = somme + pow((1 + s) , j) * laxynmAvDiff(x, y, 1, m, i, j);
				j = j + 1;

			}

			double PAP_Unit02= 0;

			if (i != s){
				PUP_Unit02 = aAv_nmi(l - y, 1, i_prim) * aAv_nmi(1, m, i) - somme;
			}

			if (i == s){
				PUP_Unit02 = (l - y) * aAv_nmi(1, m, i) - somme;
			}

			PAP_Unit02 = (double)PEpargneAnnuelle_Unit01 * PUP_Unit02 / axynmAv(x, y, p, m, i_prim);

			return PAP_Unit02;
		}

		public double laxynmAvDiff(int x , int y , int n , int m , double i , int diff ) {// 'ajouter le 10/04/2017
			return laxynmAv(x + diff, y + diff, n, m, i) * Exyn(x, y, diff, i);
		}

		public double laxynmAv(int x, int y , int n , int m , double i ) {// 'ajouter le 10/04/2017
			return axynEchue(x, y, n, i) + (double) (m + 1) / (2 * m) * (1 - Exyn(x, y, n, i));
		}	
		
		
		@Override
		public double  PAC_Unit03(double CB , double s , int x , int y , int l , double i , int m , double g , double gamma1 , double gamma2 ) {
			double i_prim ;
			double gamma_n;
			double gamma_P ;

			i_prim = (double)(i - s) / (1 + s);
			int  p ;
			p = (int)(2 * (l - y) / 3);

			gamma_n = (double)gamma1 / axynmAv(x, y, l - y, m, i_prim) + gamma2;
			gamma_P = (double)gamma1 / axynmAv(x, y, p, m, i_prim) + gamma2;

			double PAC_Unit03 = 0;

			if( s == 0) {
				PAC_Unit03 = (double)PAInv_Unit03(CB, s, x, y, l, i, m, g, gamma1, gamma2) / (1 - gamma_n);
			}

			if (s != 0) {
				PAC_Unit03 = (double)PAP_Unit03(CB, s, x, y, l, i, m, g, gamma1, gamma2) / (1 - gamma_n) + (double)g * CB * axynmAv(x, y, l - y, 1, i) / axynmAv(x, y, p, m, i_prim) / (1 - gamma_P);
			}

			return PAC_Unit03;

		}

		public double  PAInv_Unit03(double CB , double s , int x , int y , int l , double i , int m , double g , double gamma1 , double gamma2 ) {

			double i_prim;
			int p ;
			p = (l - y);
			if( s > 0 ){
				p = (int)(2 * (l - y) / 3);
			}

			i_prim = (double)(i - s) / (1 + s);

			double PAInv_Unit03 = 0;

			if ( s == 0) {
				PAInv_Unit03 = PAP_Unit03(CB, s, x, y, l, i, m, g, gamma1, gamma2) + (double)g * CB * axynmAv(x, y, l - y, 1, i) / axynmAv(x, y, l - y, m, i_prim);
			}

			if (s != 0 ){
				PAInv_Unit03 = PAP_Unit03(CB, s, x, y, l, i, m, g, gamma1, gamma2) + (double) g * CB * axynmAv(x, y, l - y, 1, i) / axynmAv(x, y, p, m, i_prim);
			}

			return PAInv_Unit03;
		}


		public double  PAP_Unit03(double CB , double s , int x , int y , int l , double i , int m , double g , double gamma1 , double gamma2 ) {
			return PAP_Unit01(CB, s, x, y, l, i, m, g, gamma1, gamma2);
		}

		@Override
		public double  PAC_Unit04(double CB , double s , int x , int y , int l , double i , int m , double g , double gamma1 , double gamma2 , int p ) {
			double i_prim ;
			double gamma ;
		i_prim = (double)(i - s) / (1 + s);
		//'gamma = gamma1 / axynmAv(x, y, l - y, 1, i_prim) + gamma2
		gamma = (double)gamma1 / axynmAv(x, y, p, m, i_prim) + gamma2;
		return(double) PAInv_Unit04(CB, s, x, y, l, i, m, g, gamma1, gamma2, p) / (1 - gamma);
		}
		
		public double  PAInv_Unit04(double CB , double s , int x , int y , int l , double i , int m , double g , double gamma1 , double gamma2 , int p ) {
			return PAP_Unit04(CB, s, x, y, l, i, m, g, gamma1, gamma2, p);
			}
		
		public double  PAP_Unit04(double CB , double s , int x , int y , int l , double i , int m , double g , double gamma1 , double gamma2 , int p ) {
			double PEpargneAnnuelle_Unit03 ;
		PEpargneAnnuelle_Unit03 = PAP_Unit03(CB, s, x, y, l, i, m, g, gamma1, gamma2);
		double i_prim ;
		double PUP_Unit04 = 0 ;
		i_prim = (i - s) / (1 + s);
		int j ;
		double somme ;
		somme = 0;
		j = 0;

		while(j<l - y){
		somme = somme + pow((1 + s) , j) * laxynmAvDiff(x, y, 1, m, i, j);
		j = j + 1;
		 
		}

		double PAP_Unit04 = 0;

		if (i != s ){
		PUP_Unit04 = aAv_nmi(l - y, 1, i_prim) * aAv_nmi(1, m, i) - somme;
		}

		if( i == s ){
		PUP_Unit04 = (l - y) * aAv_nmi(1, m, i) - somme;
		}

		//'PAP_Unit04 = PEpargneAnnuelle_Unit03 * PUP_Unit04 / axynmAv(x, y, l - y, m, i_prim)
		PAP_Unit04 = (double) PEpargneAnnuelle_Unit03 * PUP_Unit04 / axynmAv(x, y, p, m, i_prim);

		return PAP_Unit04;
		}
		
		@Override
		public double  PAC_Unit05(double Rente_mensuelle  , double s  , int x  , int y  , int l , double i  , int m  , double g  , double gamma1  , double gamma2 , int p) {
			double i_prim ;
			double gamma ;

		i_prim = (double)(i - s) / (1 + s);
		//'gamma = gamma1 / axynmAv(x, y, l - y, 1, i_prim) + gamma2
		gamma = (double)gamma1 / axynmAv(x, y, p, m, i_prim) + gamma2;
		//'PAC_Unit05 = PAInv_Unit05(Rente_mensuelle, s, x, y, l, i, m, g, gamma1, gamma2) / (1 - gamma)
		return (double)PAInv_Unit05(Rente_mensuelle, s, x, y, l, i, m, g, gamma1, gamma2, p) / (1 - gamma);

		}

		public double  PAInv_Unit05(double Rente_mensuelle  , double s  , int x  , int y  , int l , double i  , int m  , double g  , double gamma1  , double gamma2 , int p  ){
			return PAP_Unit05(Rente_mensuelle, s, x, y, l, i, m, g, gamma1, gamma2, p);
			}
		
		public double  PAP_Unit05(double Rente_mensuelle  , double s  , int x  , int y  , int l  , double i  , int m  , double g  , double gamma1  , double gamma2  , int p  ){
			double i_prim ;
			double fract_rente ;
			double axnEchuem ;
			double axynEchuem ;
			double PUP_Unit05 ;
			
			double PAP_Unit05 = 0;

		i_prim = (double)(i - s) / (1 + s);
		fract_rente = 12;
		axnEchuem = axnEchue(y, l - y, i) + (double)(fract_rente - 1) / (2 * fract_rente) * (1 - Exn(y, l - y, i));
		axynEchuem = axynEchue(x, y, l - y, i) + (double)(fract_rente - 1) / (2 * fract_rente) * (1 - Exyn(x, y, l - y, i));
		PUP_Unit05 = 12 * Rente_mensuelle * (axnEchuem - axynEchuem);

		//'PAP_Unit05 = PUP_Unit05 / axynmAv(x, y, l - y, m, i_prim)
		PAP_Unit05 = (double)PUP_Unit05 / axynmAv(x, y, p, m, i_prim);
		return PAP_Unit05;

		}
		
		public double axnEchue(int x  , int n  , double i  ){// 'ajouter le 10/04/2017
			int j ;
			double axnEchue = 0;
		v = (double)1 / (1 + i);
		j = 1;
		while(j < n+1){
			axnEchue = axnEchue + Exn(x, j, i)	;
			j = j + 1;
		}

		return axnEchue;
		 
		}
		
		public double Exn(int x  , int n  , double i ) { //'ajouter le 10/04/2017
			v = (double)1 / (1 + i);
			return ((double)lx(x + n) / lx(x)) * Math.pow(v , n);
			}
		/************************ depuis module 2 *************************/

		//prime d'épargne
		@Override
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

		@Override
		public double pek(double c  , double i  , int m  , int l  , int y  , double s  , double r  , int k  ) {

		return ((double)pe0(c, i, m, l, y, s, r) / m) * Math.pow(1 + s,(int)(k / m));
		      }


		@Override
		public double pekcomme(double c  , double i  , int m  , int l  , int y  , double s  , double r  , int k , int x    , double lamda1    , double lamda2    ) {

		return pek(c, i, m, l, y, s, r, k) * ratiocommi(x, y, l, m, i, s, lamda1, lamda2);
		}

		@Override
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


		@Override
		public double papA(int x  , int n  , int m  , double i  , double s  , int y  , int l  , double pe0  ) {

		return (double) pupA(x, m, i, s, y, l, pe0) / (( axynm(x, y, (int)((2 * n) / 3), m,  i_eq(i, s))));
		      }

		@Override
		public double pppA(int x  , int n  , int m  , double i  , double s  , int y  , int l  , double pe0  , int k  ) {
		if (k > (int)(2 * m * (l - y) / 3) - 1) 
		return 0;
		else
		return ((double)papA(x, n, m, i, s, y, l, pe0) / m) * Math.pow((1 + s) , (int)(k / m));

		      }

		@Override
		public double pppAcomm(int x  , int n  , int m  , double i  , double s  , int y  ,int l  , double pe0  , int k  , double lamda1  , double lamda2  ) {
		return pppA(x, n, m, i, s, y, l, pe0, k) * ratiocommi2_3(x, y, l, m, i, s, lamda1, lamda2);
		      }

		@Override
		public double pppcomm(double c  ,double r ,double alpha  , int x  , int n  , int m  ,double i  , double s , int y  , int l  ,double  pe0  , int k  ,double  lamda1 ,double lamda2  ) {
		return pppAcomm(x, n, m, i, s, y, l, pe0, k, lamda1, lamda2) + pekcomme(c, i, m, l, y, s, r, k, x, lamda1, lamda2) + FraisComme(c, alpha, x, y, l, i, s, m, k, lamda1, lamda2);
		}

		@Override
		public double pupB(int x  , int y  , int l  ,double i  ,double s  ,double g  ) {
		return g * 12 * ( axnmECh(y, l - y, 12, i) -  axynmECh(x, y, l - y, 12, i));

		}

		@Override
		public double papB(int x  ,int y  ,int l  ,double i ,double s  ,double g  ,int m  ) {
		return (double) pupB(x, y, l, i, s, g) /  axynm(x, y, (int)(2 * (l - y) / 3), m,  i_eq(i, s));
		      }


		@Override
		public double pppB(int x , int y  , int l  , double i  , double s  , double g  , int m  , int k  ) {
		if (k > (int)(2 * m * (l - y) / 3) - 1) 
		return 0;
		else
		return ((double)papB(x, y, l, i, s, g, m) / m) * Math.pow((1 + s) , (int)(k / m));

		}

		@Override
		public double pppBcomme(int x  , int y  , int l  , double i  , double s  , double g  , int m  , int k  , double lamda1 , double lamda2  ) {

		return pppB(x, y, l, i, s, g, m, k) * ratiocommi2_3(x, y, l, m, i, s, lamda1, lamda2);

		}


		@Override
		public double Fraisannul(double c  ,double alpha , int x  ,int y  ,int l  ,double i  ,double s  ,int m  ,int k  ) {
		if(s == 0 )
		return (double)(c * alpha *  axyn(x, y, l - y, i)) / ( axynm(x, y, l - y, m,  i_eq(i, s)));
		else
		return (double)(c * alpha *  axyn(x, y, l - y, i)) / ( axynm(x, y, (int)(2 * (l - y) / 3), m,  i_eq(i, s)));

		      }

		@Override
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

		@Override
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



		@Override
		public double ratiocommi(int x  ,int y  ,int l  ,int m  ,double i  ,double s  ,double lamda1 ,double lamda2  ) {
		double lamda ;
		lamda = ((double)lamda1 /  axynm(x, y, l - y, m,  i_eq(i, s))) + lamda2;
		return (double)1 / (1 - lamda);

		}

		@Override
		public double ratiocommi2_3(int x  , int y  ,int l  ,int m  ,double i  ,double s  ,double lamda1 ,double lamda2 ) {
		double lamda;
		lamda = ((double) lamda1 /  axynm(x, y, (int)((l - y) * 2 / 3), m,  i_eq(i, s))) + lamda2;
		return   (double)1 / (1 - lamda);

		      }

		/************************ depuis module 3 *************************/

//		public Date ConvertStringToDate(String sDate)  {
//		    
//		    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//		     Date date = null;
//		    try {
//		        date= df.parse(sDate);
//		    } catch (ParseException ex) {
//		        Logger.getLogger(Module1Service.class.getName()).log(Level.SEVERE, null, ex);
//		    }
//		return date;
//		}
//
//
//		public Date DateAdd(String x,int duree,Date Date_effet){
//		    Calendar c = Calendar.getInstance();
//		    c.setTime(Date_effet);
//		    
//		    if(x.equalsIgnoreCase("yyyy")) 
//		        c.add(Calendar.YEAR, duree);
//		          
//		    if(x.equalsIgnoreCase("m"))
//		        c.add(Calendar.MONTH, duree);
//		         
//		     return c.getTime();
//		}
//
//		public Date date_fin(Date Date_effet,int duree){
//		    
//		    Date resultat = DateAdd("yyyy",duree,Date_effet);
//		    System.out.println("Full Date : " + resultat);
//		    
//		    return resultat;
//		}
//
//
//		public Date date_eche (Date Date_effet  , int duree  ,int k  , int m ){
//		    
//		    int j ;
//		    j = (m * duree);
//		    if (j > k ) return DateAdd("m", k * 12 / m, Date_effet);
//		            else {
//		        
//		        return ConvertStringToDate("01/01/1900");
//		    }
//		}
//
//
//
//		public int t_an(Date Date_effet  , Date date_inve)  {
//		        int j_effet;
//		        int m_effet ;
//		        int a_effet ;
//
//		        int j_inve ;
//		        int m_inve ;
//		        int a_inve ;
//
//		        Calendar cal = Calendar.getInstance();
//		        cal.setTime(Date_effet);
//
//		        j_effet = cal.get(Calendar.DAY_OF_MONTH);
//		        m_effet =  cal.get(Calendar.MONTH);
//		        a_effet = cal.get(Calendar.YEAR);
//
//		        cal.setTime(date_inve);
//
//		        j_inve = cal.get(Calendar.DAY_OF_MONTH);
//		        m_inve = cal.get(Calendar.MONTH);
//		        a_inve = cal.get(Calendar.YEAR);
//
//		        if (j_inve == 31) j_inve = 30;
//		        //else
//		        //j_inve = j_inve;
//
//		        if (j_effet == 31) j_effet = 30;
//		        //else
//		        //j_effet = j_effet;
//
//		        return (int)(((a_inve - a_effet) * 360 + (m_inve - m_effet) * 30 + (j_inve - j_effet)) / 360);
//
//		    }
//
//
//		public int moisDiff(Date date_eche  , Date dateinvent  , int m) {
//		    
//		    DateTime dt1 = new DateTime(date_eche);
//		DateTime dt2 = new DateTime(dateinvent);
//
//		    int month = Months.monthsBetween(dt1, dt2).getMonths();
//
//		    System.out.println("Full Date : " + month);
//		    return month;//DateDiff("m", date_eche, dateinvent);
//		    
//		    }
//
//		public int jourDiff(Date date_eche  , Date dateinvent  , int m) {
//		    
//		    DateTime dt1 = new DateTime(date_eche);
//		DateTime dt2 = new DateTime(dateinvent);
//
//		    int day = Days.daysBetween(dt1, dt2).getDays();
//
//		    System.out.println("Full Date : " + day);
//		    return day;//DateDiff("m", date_eche, dateinvent);
//		    
//		    }
//
//
//
//		public int ancient_police(Date date_ech   , Date dateinevent  ,int m  ) {
//		    
//		        int a ;
//		        int b ;
//		        int c ;
//		        int d ;
//		        int w ;
//
//		        DateTime dt1 = new DateTime(date_ech);
//		        DateTime dt2 = new DateTime(dateinevent);
//
//		        a = dt1.getMonthOfYear();
//		        b = dt2.getMonthOfYear();
//		        c = dt1.getYear();
//		        d = dt2.getYear();
//		        w = (moisDiff(date_ech, dateinevent, m) * m / 12);
//
//		        if (a <= b) return d - c;
//		        else
//		        return d - c - 1;
//
//		    }
//
//
//		public Date date_annive(Date Date_effet  , Date dateinevent  ,int m  ) { 
//		    int j ;
//		    int k ;
//		    k = t_an(Date_effet, dateinevent);
//		    ///////////////////////////////////////////à voir
//		    //j = (m * duree);  
//
//		    return DateAdd("yyyy", k, Date_effet);
//
//		    }
//
//
//		public double alpha(Date Date_effet , Date dateinevent  , int m  ) {
//		    int a ;
//		    int b ;
//		    int c ;
//		    int d ;
//		    int e ;
//		    int f ;
//		    int f1 ;
//		    int w ;
//		    int k ;
//		    k = ancient_police(Date_effet, dateinevent, m);
//		    Date ww = date_annive(Date_effet, dateinevent, m);
//
//		     DateTime dtww = new DateTime(ww);
//		     DateTime dtdateinevent = new DateTime(dateinevent);
//
//		    a = dtww.getMonthOfYear();
//		    b = dtdateinevent.getMonthOfYear();
//		    c = dtww.getYear();
//		    d = dtdateinevent.getYear();
//		    e = dtww.getDayOfMonth();
//		    f = dtdateinevent.getDayOfMonth();
//
//		    return (((d - c) * 360 + (b - a) * 30 + f - e) / 360);
//
//		    }
//
//
//		public int numéro_pai(Date Date_effet  , Date dateinevent  , int m  ) {
//
//		    return (int) ((t_an(Date_effet, dateinevent)) * m + ((alpha(Date_effet, dateinevent, m)) * m));
//
//		    }
//

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

//		public double proviA(double pe0  , double i  , int x  , int y  , int l  , double s , int m  , int k  ) { 
//		double a ;
//		double d ;
//		double ieq ;
//		int w ;
//
//		ieq = (i - s) / (1 + s);
//		w = k - (int)((l - y) * 2 / 3);
//		a = (pupA(x + k, m, i, s, y + k, l, pe0)) * Math.pow((1 + s),k)   ;
//
//		if(w < 0) {  
//		d = papA(x, l - y, m, i, s, y, l, pe0) * axynm(x + k, y + k, ((l - y) * 2 / 3) - k, m, ieq) * Math.pow((1 + s),k);
//		return a - d;
//		        }
//		else
//		return a;
//
//		}
//
//
//		public double proviB(double g  , double i  , int x  , int y  , int l  , double s , int m  , int k ) {
//		double a   ;
//		double d   ;
//		double ieq ;
//		int j ;
//
//		ieq = (i - s) / (1 + s);
//		a = pupB(x + k, y + k, l, i, s, g);
//		j = k - (((l - y) * 2 / 3));
//
//		if (j < 0){
//		   d = (papB(x, y, l, i, s, g, m) * Math.pow((1 + s),k)) * axynm(x + k, y + k, ((int)((l - y) * 2 / 3)) - k, m, ieq);
//		return a - d ;
//		}
//
//		else
//		return a;
//
//		}
//
//
//
//		public double prov_frais(double c , double alpha  , int x  , int y  , int l  , double i  , double s  , int m  , int k  ) {
//		double a ;
//		double b ;
//		double b1 ;
//		double d ;
//		double ieq ;
//		int w ;
//		ieq = (i - s) / (1 + s);
//		w = k - ((int)((l - y) * 2 / 3));
//
//
//		b1 = Fraisannul(c, alpha, x, y, l, i, s, m, 0) * Math.pow((1 + s),k) * axynm(x + k, y + k, l - y - k, m, ieq);
//		a = c * alpha * axyn(x + k, y + k, l - y - k, i);
//
//
//		if( s == 0)  
//		return a - b1;
//		else {
//
//		if (w < 0){
//		   b = Fraisannul(c, alpha, x, y, l, i, s, m, 0) * Math.pow((1 + s),k) * (axynm(x + k, y + k, ((int)((l - y) * 2 / 3)) - k, m, ieq));
//		return  a - b; 
//		}
//
//		else
//		return a;
//		}
//		}
//
//
//
//		public double  provi_dateA(double nbres  , double pe0 , double i  , int x  , int y  , 
//		    int l  , double s  , int m  , Date Date_effet  , Date dateinevent   ) {
//
//		double alp ;
//		int k = 0 ; /// à voir
//		Date w ;
//		double a ;
//		double d ;
//		double c ;
//		int yy ;
//		int z ;
//		double b;  /// à voir  non déf
//
//		yy = (int) (((t_an(Date_effet, dateinevent) + 1) * m) - nbres);
//
//		z = yy - (int)(m * (l - y) * 2 / 3);
//
//		w = date_eche(Date_effet, l - y, k, m); // à voir k = 0
//		k = t_an(Date_effet, dateinevent);
//		alp = alpha(Date_effet, dateinevent, m);
//
//
//		a = proviA(pe0, i, x, y, l, s, m, k);
//		b = (papA(x, l - y, m, i, s, y, l, pe0) * Math.pow((1 + s),k));
//		c = proviA(pe0, i, x, y, l, s, m, k + 1);
//
//		if (z < 0) 
//
//		return((1 - alp)) * (a + b) + (alp * c) - (nbres / m) * b;
//		else
//		return ((1 - alp)) * (a) + (alp * c);
//
//		}
//
//
//
//		public double provi_dateB(double nbres  , double g  , double i  , int x  , int y  , int l  , double s  , int m  , Date Date_effet  , Date dateinevent  ) {
//		double alp ;
//		int k = 0 ;  // a voir k
//		Date w ;
//		double a ;
//		double d ;
//		double c ;
//		int yy  ;
//		int z ;
//		double b; // à voir
//
//		yy = (int) (((t_an(Date_effet, dateinevent) + 1) * m) - nbres);
//		z = yy - (int)(m * (l - y) * 2 / 3);
//		w = date_eche(Date_effet, l - y, k, m); // à voir k
//		k = t_an(Date_effet, dateinevent);
//		alp = alpha(Date_effet, dateinevent, m);
//		a = proviB(g, i, x, y, l, s, m, k);
//		b = (papB(x, y, l, i, s, g, m) * Math.pow((1 + s),k));
//		c = proviB(g, i, x, y, l, s, m, k + 1);
//		if (z < 0)  
//		return ((1 - alp)) * (a + b) + (alp * c) - (nbres / m) * b;
//		else
//		return ((1 - alp)) * (a) + (alp * c);
//
//		}
//
//
//
//
//		public double provi_dateFrais(double nbres  , double cap  , double alpha_F  , 
//		    double i  , int x  , int y  , int l  , double s  , int m  , 
//		    Date Date_effet  , Date dateinevent  ) {
//		double alp ;
//		int k = 0 ; // a voir k
//		Date w ;
//		double a ;
//		double d ;
//		double c ;
//		int yy ;
//		int z ;
//		double b; // a voir
//
//		yy = yy = (int) (((t_an(Date_effet, dateinevent) + 1) * m) - nbres);
//		z = yy - (int)(m * (l - y) * 2 / 3);
//		w = date_eche(Date_effet, l - y, k, m); // a voir k 
//		k = t_an(Date_effet, dateinevent);
//		alp = alpha(Date_effet, dateinevent, m);
//		a = prov_frais(cap, alpha_F, x, y, l, i, s, m, k);
//		b = (Fraisannul(cap, alpha_F, x, y, l, i, s, m, 0) * Math.pow((1 + s),k));
//		c = prov_frais(cap, alpha_F, x, y, l, i, s, m, k + 1);
//		if (s == 0) 
//		return((1 - alp)) * (a + b) + (alp * c) - nbres * b;
//		else{
//		if (z < 0) 
//		return ((1 - alp)) * (a + b) + (alp * c) - (nbres / m) * b;
//		else
//		return((1 - alp)) * (a) + (alp * c);
//
//		        }
//
//		}
//

		/************************ depuis module 5 *************************/

//		public double ipb(int annee  ) {
//		    return 0;//  Sheets("A").Cells(annee - 1993, 2);
//		 } /////////////////////////////////////////////////////////// a voir 
//		 
//
//		 
//		 public double Aj(Date Date_prime   , Date Date_calcul    ) {
//		     int ann_prim =0 ;
//		     int ann_calc =0 ;
//		     int mois_prim ;
//		     int mois_cal ;
//		     int jou_prim ;
//		     int jou_cal ;
//		     double a   ;
//		     double b   ;
//		     int w ;
//		     int j ;
//		     double prod ;
//
//		     w = ann_calc - ann_prim;
//
//		     DateTime dtDate_calcul = new DateTime(Date_calcul);
//		     DateTime dtDate_prime = new DateTime(Date_prime);
//
//		     ann_calc = dtDate_calcul.getYear();  
//		     mois_cal = dtDate_calcul.getMonthOfYear(); 
//		     jou_cal =  dtDate_calcul.getDayOfMonth();   
//		     if (jou_cal == 31)  
//		     jou_cal = 30;
//		     //else
//		     //jou_cal = jou_cal
//
//		     ann_prim = dtDate_prime.getYear();  
//		     mois_prim = dtDate_prime.getMonthOfYear(); 
//		     jou_prim =  dtDate_prime.getDayOfMonth();   
//		     if (jou_prim == 31)  
//		     jou_prim = 30;
//		     //Else
//		     //jou_prim = jou_prim
//		     //End If
//
//		     a = (30 * (mois_cal - mois_prim) + (jou_cal - jou_prim)) / 360;
//		     b = (30 * (12 - mois_prim) + (30 - jou_prim)) / 360;
//		             int c = (30 * (mois_cal - 1) + (jou_cal)) / 360;
//
//		     if (ann_prim == ann_calc)  
//		     return Math.pow((1 + ipb(ann_calc)),a);
//		     else if (ann_prim == ann_calc - 1 )
//		     return Math.pow((1 + ipb(ann_prim)),b) * Math.pow((1 + ipb(ann_calc)),c);
//		     else{
//
//		         j = 1;
//		     prod = 1;
//		     while (j < ann_calc - ann_prim) {
//		                 prod = prod * (1 + ipb(ann_prim + j));
//		                 j = j + 1;
//		             }
//
//		     return Math.pow((1 + ipb(ann_prim)),b) * prod * Math.pow((1 + ipb(ann_calc)),c);
//
//		}
//
//		     }
//
//		 
//		 
//		 
//		 public double pme(double pe0  , int dur  , double s  , int alp  , int m  , Date Date_effet  , Date Date_calcul  ) {
//
//		double b ;
//		int j ;
//		int k ;
//		int c ;
//		c = t_an(Date_effet, Date_calcul);
//		b = 0;
//		//'c = Aj(date_eche(Date_effet, dur, 1, m), Date_calcul)
//		//'d = Aj(date_eche(Date_effet, dur, 2, m), Date_calcul)
//		k = (t_an(Date_effet, Date_calcul) + 1) * m - alp;
//		for(j=0;j<k-1;j++){
//		b = b + (Math.pow((1 + s),(int)(j / m))) * Aj(date_eche(Date_effet, dur, j, m), Date_calcul) ; 
//		}
//
//		if (Date_calcul == date_eche(Date_effet, dur, k, m) )    
//		return b * pe0 / m + ( pe0 * Math.pow((1 + s),(int)(k / m)) ) / m;
//		else
//		return b * pe0 / m;
//
//		     }
//
//


}
