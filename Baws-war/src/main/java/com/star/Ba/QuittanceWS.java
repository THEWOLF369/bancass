package com.star.Ba;


import javax.ejb.EJB;
import javax.ejb.Stateless;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.star.Ba.dto.QuittancesClient;





@Stateless

@WebService(endpointInterface = "com.star.Ba.IQuittanceWS")
public class QuittanceWS implements IQuittanceWS {
	
@EJB
private com.star.Ba.service.IParametreAgricoleService serviceagr;

@Resource
WebServiceContext wsctx;


@Override
public QuittancesClient getdetailsquittance(int numquit) {
	
	
	
	
	
	
	return serviceagr.getMontantPackByNumQuittance(numquit);
	
	
	//SOAPSecurityHandler soapSecurityHandler =  new SOAPSecurityHandler("starbna","2020");
	
	//if (soapSecurityHandler.getUserId().equals("starbna")&&soapSecurityHandler.getUserId().equals("2020")) {
	
	/*MessageContext mctx = wsctx.getMessageContext();
	
	 Map<?,?> http_headers = (Map<?,?>) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
     List<?> userList = (List<?>) http_headers.get("Username");
     List<?> passList = (List<?>) http_headers.get("Password");

     String username = "";
     String password = "";
     
     if(userList!=null){
     	//get username
     	username = userList.get(0).toString();
     }
     	
     if(passList!=null){
     	//get password
     	password = passList.get(0).toString();
     }
     
     if (username.equals("mkyong") && password.equals("password")){
    	 
    	 return serviceagr.getMontantPackByNumQuittance(numquit);
     	
     }else{
     	return serviceagr.getMontantPackByNumQuittance2(numquit);
     }
	*/
	
	
 

	
}
	

}

	
	
	
	

	
	

	
	
	
	
	
	






