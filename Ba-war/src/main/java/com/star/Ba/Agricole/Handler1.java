package com.star.Ba.Agricole;


import java.util.Set;
import java.util.Collections;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;


 public class Handler1 implements SOAPHandler<SOAPMessageContext>
{
  public Set<QName> getHeaders()
  {
    return Collections.emptySet();
  }

  public boolean handleMessage(SOAPMessageContext messageContext)
  {
     Boolean outboundProperty = (Boolean)
         messageContext.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);

     if (outboundProperty.booleanValue()) {
    	 
    	  try {
    		  
    	 SOAPEnvelope envelope = messageContext.getMessage().getSOAPPart().getEnvelope();
         SOAPHeader header = envelope.addHeader();

         SOAPElement security =
                 header.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

         SOAPElement usernameToken =
                 security.addChildElement("UsernameToken", "wsse");
         usernameToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

         SOAPElement username =
                 usernameToken.addChildElement("Username", "wsse");
         username.addTextNode("agricole");

         SOAPElement password =
                 usernameToken.addChildElement("Password", "wsse");
         password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
        password.addTextNode("BNAbna2020");
       
        System.out.println("** test kacem: "+messageContext.getMessage().toString());
    } catch (Exception e) {
        e.printStackTrace();
    }
    	 
        // System.out.println("\nOutbound message:");
     } else {
         System.out.println("\nInbound message:");
     }

     //System.out.println("** Response: "+messageContext.getMessage().toString());
    return true;
  }

  public boolean handleFault(SOAPMessageContext messageContext)
  {
    return true;
  }
   public void close(MessageContext messageContext)
  {
  }
}