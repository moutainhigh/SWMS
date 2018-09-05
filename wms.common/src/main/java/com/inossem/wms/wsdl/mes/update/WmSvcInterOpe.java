
package com.inossem.wms.wsdl.mes.update;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import com.inossem.wms.util.UtilProperties;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WmSvcInterOpe", targetNamespace = "http://www.mes30.com/wm_webservices", wsdlLocation = "http://10.122.1.129/MES30/WebService/mv/wm/Interope/WmSvcInterOpe.asmx?WSDL")
public class WmSvcInterOpe
    extends Service
{

    private final static URL WMSVCINTEROPE_WSDL_LOCATION;
    private final static WebServiceException WMSVCINTEROPE_EXCEPTION;
    private final static QName WMSVCINTEROPE_QNAME = new QName("http://www.mes30.com/wm_webservices", "WmSvcInterOpe");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL(UtilProperties.getInstance().getMes_url());
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WMSVCINTEROPE_WSDL_LOCATION = url;
        WMSVCINTEROPE_EXCEPTION = e;
    }

    public WmSvcInterOpe() {
        super(__getWsdlLocation(), WMSVCINTEROPE_QNAME);
    }

    public WmSvcInterOpe(WebServiceFeature... features) {
        super(__getWsdlLocation(), WMSVCINTEROPE_QNAME, features);
    }

    public WmSvcInterOpe(URL wsdlLocation) {
        super(wsdlLocation, WMSVCINTEROPE_QNAME);
    }

    public WmSvcInterOpe(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WMSVCINTEROPE_QNAME, features);
    }

    public WmSvcInterOpe(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WmSvcInterOpe(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WmSvcInterOpeSoap
     */
    @WebEndpoint(name = "WmSvcInterOpeSoap")
    public WmSvcInterOpeSoap getWmSvcInterOpeSoap() {
        return super.getPort(new QName("http://www.mes30.com/wm_webservices", "WmSvcInterOpeSoap"), WmSvcInterOpeSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WmSvcInterOpeSoap
     */
    @WebEndpoint(name = "WmSvcInterOpeSoap")
    public WmSvcInterOpeSoap getWmSvcInterOpeSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.mes30.com/wm_webservices", "WmSvcInterOpeSoap"), WmSvcInterOpeSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WMSVCINTEROPE_EXCEPTION!= null) {
            throw WMSVCINTEROPE_EXCEPTION;
        }
        return WMSVCINTEROPE_WSDL_LOCATION;
    }

}
