
package com.inossem.wms.wsdl.sap.inputc;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SI_CW_WMS_CANCEL_MATDOC_S_OUT", targetNamespace = "urn:sinopec:ecc:iwms:cw")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SICWWMSCANCELMATDOCSOUT {


    /**
     * 
     * @param mtCWWMSCANCELMATDOCREQ
     * @return
     *     returns com.inossem.wms.wsdl.sap.inputc.DTCWWMSCANCELMATDOCRESP
     */
    @WebMethod(operationName = "SI_CW_WMS_CANCEL_MATDOC_S_OUT", action = "http://sap.com/xi/WebService/soap1.1")
    @WebResult(name = "MT_CW_WMS_CANCEL_MATDOC_RESP", targetNamespace = "urn:sinopec:ecc:iwms:cw", partName = "MT_CW_WMS_CANCEL_MATDOC_RESP")
    public DTCWWMSCANCELMATDOCRESP siCWWMSCANCELMATDOCSOUT(
        @WebParam(name = "MT_CW_WMS_CANCEL_MATDOC_REQ", targetNamespace = "urn:sinopec:ecc:iwms:cw", partName = "MT_CW_WMS_CANCEL_MATDOC_REQ")
        DTCWWMSCANCELMATDOCREQ mtCWWMSCANCELMATDOCREQ);

}