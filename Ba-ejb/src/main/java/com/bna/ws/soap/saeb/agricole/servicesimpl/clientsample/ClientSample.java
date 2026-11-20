package com.bna.ws.soap.saeb.agricole.servicesimpl.clientsample;

import com.bna.ws.soap.saeb.agricole.services.IDecisionCreditsAgr;
import com.bna.ws.soap.saeb.agricole.servicesimpl.*;

public class ClientSample {

	public static void main(String[] args) {
	        System.out.println("***********************");
	        System.out.println("Create Web Service Client...");
	        DecisionCreditsAgrImplService service1 = new DecisionCreditsAgrImplService();
	        System.out.println("Create Web Service...");
	        IDecisionCreditsAgr port1 = service1.getDecisionCreditsAgrImplPort();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port1.getDecisionCreditAgr(null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Create Web Service...");
	        IDecisionCreditsAgr port2 = service1.getDecisionCreditsAgrImplPort();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port2.getDecisionCreditAgr(null,null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("***********************");
	        System.out.println("Call Over!");
	}
}
