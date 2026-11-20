package com.star.Ba.service;

import javax.ejb.Local;

@Local
public interface Module1Impl {
	
	 
	
	

	public double tmg(int dureeContract,int ageEnf);

	public Double cb(double b, double d, int l, int w, double r,int ageEnf);

	public double pe0(double c, double i, int m, int l, int y, double s, double r);

	public double pppAcomm(int x, int n, int m, double i, double s, int y, int l,
			double pe0, int k, double lamda1, double lamda2);

	public double ci(double b, double d, int l, int w, double r,int ageEnf);

	public double pppBcomme(int x, int y, int l, double i, double s, double g, int m,
			int k, double lamda1, double lamda2);

	public double pekcomme(double c, double i, int m, int l, int y, double s,
			double r, int k, int x, double lamda1, double lamda2);

	double lx(int x);

	double axn(int x, int n, int m, double i);

	double axnECh(int x, int n, int m, double i);

	double axnm(int x, int n, int m, double i);

	double axnmECh(int x, int n, int m, double i);

	double axnmp(int x, int n, int m, double i, int p);

	double axynmp(int x, int y, int n, int m, double i, int p);

	double anm(int n, int m, double i);

	double axyn(int x, int y, int n, double i);

	double axynECh(int x, int y, int n, int m, double i);

	double axynm(int x, int y, int n, int m, double i);

	double axynmECh(int x, int y, int n, int m, double i);

	double i_eq(double i, double s);

	double d(double i);

	double dm(double i, int m);

	double capital(double b, double d, int l, int w, double r,int ageEnf);

	double pek(double c, double i, int m, int l, int y, double s, double r,
			int k);

	double pupA(int x, int m, double i, double s, int y, int l, double pe0);

	double papA(int x, int n, int m, double i, double s, int y, int l,
			double pe0);

	double pppA(int x, int n, int m, double i, double s, int y, int l,
			double pe0, int k);

	double pppcomm(double c, double r, double alpha, int x, int n, int m,
			double i, double s, int y, int l, double pe0, int k, double lamda1,
			double lamda2);

	double pupB(int x, int y, int l, double i, double s, double g);

	double papB(int x, int y, int l, double i, double s, double g, int m);

	double pppB(int x, int y, int l, double i, double s, double g, int m, int k);

	double Fraisannul(double c, double alpha, int x, int y, int l, double i,
			double s, int m, int k);

	double Frais1(double c, double alpha, int x, int y, int l, double i,
			double s, int m, int k);

	double FraisComme(double c, double alpha, int x, int y, int l, double i,
			double s, int m, int k, double lamda1, double lamda2);

	double ratiocommi(int x, int y, int l, int m, double i, double s,
			double lamda1, double lamda2);

	double ratiocommi2_3(int x, int y, int l, int m, double i, double s,
			double lamda1, double lamda2);

	double dure(int l,int ageEnf);

	double CapitalCb_Unit01(int l, int duree_bourse, double bourse_Mensuelle,
			double i, double r);

	double CapitalCb_Unit03(double Frais_annuels, int duree_bourse, double i);

	double PAC_Unit01(double CB, double s, int x, int y, int l, double i,
			int m, double g, double gamma1, double gamma2);

	double PAC_Unit02(double CB, double s, int x, int y, int l, double i,
			int m, double g, double gamma1, double gamma2, int p);

	double PAC_Unit03(double CB, double s, int x, int y, int l, double i,
			int m, double g, double gamma1, double gamma2);

	double PAC_Unit04(double CB, double s, int x, int y, int l, double i,
			int m, double g, double gamma1, double gamma2, int p);

	double PAC_Unit05(double Rente_mensuelle, double s, int x, int y, int l,
			double i, int m, double g, double gamma1, double gamma2, int p);

}
