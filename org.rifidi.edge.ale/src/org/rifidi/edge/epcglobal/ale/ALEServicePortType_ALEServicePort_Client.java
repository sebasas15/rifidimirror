
package org.rifidi.edge.epcglobal.ale;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.4
 * 2015-12-12T14:01:05.497-05:00
 * Generated source version: 3.1.4
 * 
 */
public final class ALEServicePortType_ALEServicePort_Client {

    private static final QName SERVICE_NAME = new QName("urn:epcglobal:ale:wsdl:1", "ALEService");

    private ALEServicePortType_ALEServicePort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = ALEService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        ALEService ss = new ALEService(wsdlURL, SERVICE_NAME);
        ALEServicePortType port = ss.getALEServicePort();  
        
        {
        System.out.println("Invoking immediate...");
        org.rifidi.edge.epcglobal.ale.Immediate _immediate_parms = null;
        try {
            org.rifidi.edge.epcglobal.ale.ECReports _immediate__return = port.immediate(_immediate_parms);
            System.out.println("immediate.result=" + _immediate__return);

        } catch (ImplementationExceptionResponse e) { 
            System.out.println("Expected exception: ImplementationExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (SecurityExceptionResponse e) { 
            System.out.println("Expected exception: SecurityExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (ECSpecValidationExceptionResponse e) { 
            System.out.println("Expected exception: ECSpecValidationExceptionResponse has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getECSpecNames...");
        org.rifidi.edge.epcglobal.ale.EmptyParms _getECSpecNames_parms = null;
        try {
            org.rifidi.edge.epcglobal.ale.ArrayOfString _getECSpecNames__return = port.getECSpecNames(_getECSpecNames_parms);
            System.out.println("getECSpecNames.result=" + _getECSpecNames__return);

        } catch (ImplementationExceptionResponse e) { 
            System.out.println("Expected exception: ImplementationExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (SecurityExceptionResponse e) { 
            System.out.println("Expected exception: SecurityExceptionResponse has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking undefine...");
        org.rifidi.edge.epcglobal.ale.Undefine _undefine_parms = null;
        try {
            org.rifidi.edge.epcglobal.ale.VoidHolder _undefine__return = port.undefine(_undefine_parms);
            System.out.println("undefine.result=" + _undefine__return);

        } catch (ImplementationExceptionResponse e) { 
            System.out.println("Expected exception: ImplementationExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (NoSuchNameExceptionResponse e) { 
            System.out.println("Expected exception: NoSuchNameExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (SecurityExceptionResponse e) { 
            System.out.println("Expected exception: SecurityExceptionResponse has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking define...");
        org.rifidi.edge.epcglobal.ale.Define _define_parms = null;
        try {
            org.rifidi.edge.epcglobal.ale.VoidHolder _define__return = port.define(_define_parms);
            System.out.println("define.result=" + _define__return);

        } catch (ImplementationExceptionResponse e) { 
            System.out.println("Expected exception: ImplementationExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (SecurityExceptionResponse e) { 
            System.out.println("Expected exception: SecurityExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (ECSpecValidationExceptionResponse e) { 
            System.out.println("Expected exception: ECSpecValidationExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (DuplicateNameExceptionResponse e) { 
            System.out.println("Expected exception: DuplicateNameExceptionResponse has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking unsubscribe...");
        org.rifidi.edge.epcglobal.ale.Unsubscribe _unsubscribe_parms = null;
        try {
            org.rifidi.edge.epcglobal.ale.VoidHolder _unsubscribe__return = port.unsubscribe(_unsubscribe_parms);
            System.out.println("unsubscribe.result=" + _unsubscribe__return);

        } catch (ImplementationExceptionResponse e) { 
            System.out.println("Expected exception: ImplementationExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (NoSuchSubscriberExceptionResponse e) { 
            System.out.println("Expected exception: NoSuchSubscriberExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (NoSuchNameExceptionResponse e) { 
            System.out.println("Expected exception: NoSuchNameExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (SecurityExceptionResponse e) { 
            System.out.println("Expected exception: SecurityExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (InvalidURIExceptionResponse e) { 
            System.out.println("Expected exception: InvalidURIExceptionResponse has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking poll...");
        org.rifidi.edge.epcglobal.ale.Poll _poll_parms = null;
        try {
            org.rifidi.edge.epcglobal.ale.ECReports _poll__return = port.poll(_poll_parms);
            System.out.println("poll.result=" + _poll__return);

        } catch (ImplementationExceptionResponse e) { 
            System.out.println("Expected exception: ImplementationExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (NoSuchNameExceptionResponse e) { 
            System.out.println("Expected exception: NoSuchNameExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (SecurityExceptionResponse e) { 
            System.out.println("Expected exception: SecurityExceptionResponse has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getSubscribers...");
        org.rifidi.edge.epcglobal.ale.GetSubscribers _getSubscribers_parms = null;
        try {
            org.rifidi.edge.epcglobal.ale.ArrayOfString _getSubscribers__return = port.getSubscribers(_getSubscribers_parms);
            System.out.println("getSubscribers.result=" + _getSubscribers__return);

        } catch (ImplementationExceptionResponse e) { 
            System.out.println("Expected exception: ImplementationExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (NoSuchNameExceptionResponse e) { 
            System.out.println("Expected exception: NoSuchNameExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (SecurityExceptionResponse e) { 
            System.out.println("Expected exception: SecurityExceptionResponse has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking subscribe...");
        org.rifidi.edge.epcglobal.ale.Subscribe _subscribe_parms = null;
        try {
            org.rifidi.edge.epcglobal.ale.VoidHolder _subscribe__return = port.subscribe(_subscribe_parms);
            System.out.println("subscribe.result=" + _subscribe__return);

        } catch (ImplementationExceptionResponse e) { 
            System.out.println("Expected exception: ImplementationExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (NoSuchNameExceptionResponse e) { 
            System.out.println("Expected exception: NoSuchNameExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (SecurityExceptionResponse e) { 
            System.out.println("Expected exception: SecurityExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (InvalidURIExceptionResponse e) { 
            System.out.println("Expected exception: InvalidURIExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (DuplicateSubscriptionExceptionResponse e) { 
            System.out.println("Expected exception: DuplicateSubscriptionExceptionResponse has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getECSpec...");
        org.rifidi.edge.epcglobal.ale.GetECSpec _getECSpec_parms = null;
        try {
            org.rifidi.edge.epcglobal.ale.ECSpec _getECSpec__return = port.getECSpec(_getECSpec_parms);
            System.out.println("getECSpec.result=" + _getECSpec__return);

        } catch (ImplementationExceptionResponse e) { 
            System.out.println("Expected exception: ImplementationExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (NoSuchNameExceptionResponse e) { 
            System.out.println("Expected exception: NoSuchNameExceptionResponse has occurred.");
            System.out.println(e.toString());
        } catch (SecurityExceptionResponse e) { 
            System.out.println("Expected exception: SecurityExceptionResponse has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getStandardVersion...");
        org.rifidi.edge.epcglobal.ale.EmptyParms _getStandardVersion_parms = null;
        try {
            java.lang.String _getStandardVersion__return = port.getStandardVersion(_getStandardVersion_parms);
            System.out.println("getStandardVersion.result=" + _getStandardVersion__return);

        } catch (ImplementationExceptionResponse e) { 
            System.out.println("Expected exception: ImplementationExceptionResponse has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getVendorVersion...");
        org.rifidi.edge.epcglobal.ale.EmptyParms _getVendorVersion_parms = null;
        try {
            java.lang.String _getVendorVersion__return = port.getVendorVersion(_getVendorVersion_parms);
            System.out.println("getVendorVersion.result=" + _getVendorVersion__return);

        } catch (ImplementationExceptionResponse e) { 
            System.out.println("Expected exception: ImplementationExceptionResponse has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
