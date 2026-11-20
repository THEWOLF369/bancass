package com.star.Ba;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.star.Ba.dto.QuittancesClient;


@WebService(name ="GetDetailsQuittance")
@SOAPBinding(style = Style.RPC)
public interface IQuittanceWS {
	
	@WebMethod QuittancesClient getdetailsquittance( @WebParam(name="numeroQuittance") int numquit);

}
