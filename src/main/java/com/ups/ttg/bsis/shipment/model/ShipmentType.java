//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.13 at 04:13:53 PM EST 
//


package com.ups.ttg.bsis.shipment.model;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShipmentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ShipmentNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BrokerageReferenceNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Amount" type="{}AmountType"/>
 *         &lt;element name="InvoiceNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ImportCountry" type="{}ImportCountryType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentType", propOrder = {
    "shipmentNumber",
    "brokerageReferenceNumber",
    "amount",
    "invoiceNumber",
    "importCountry"
})
public class ShipmentType {

    @XmlElement(name = "ShipmentNumber", required = true)
    protected String shipmentNumber;
    @XmlElement(name = "BrokerageReferenceNumber", required = true)
    protected String brokerageReferenceNumber;
    @XmlElement(name = "Amount", required = true)
    protected AmountType amount;
    @XmlElement(name = "InvoiceNumber", required = true)
    protected String invoiceNumber;
    @XmlElement(name = "ImportCountry", required = true)
    protected ImportCountryType importCountry;

    public ShipmentType() { }
    
    /**
     * 
     * @param shipmentNumber
     * @param amount
     * @param invoiceNumber
     * @param importCountry
     */
    public ShipmentType(String shipmentNumber, AmountType amount, String invoiceNumber,
			ImportCountryType importCountry) {
		super();
		this.shipmentNumber = shipmentNumber;
		this.amount = amount;
		this.invoiceNumber = invoiceNumber;
		this.importCountry = importCountry;
	}
    
    /**
     * 
     * @param shipmentNumber
     * @param brokerageReferenceNumber
     * @param amount
     * @param invoiceNumber
     * @param importCountry
     */
    public ShipmentType(String shipmentNumber, String brokerageReferenceNumber, AmountType amount, String invoiceNumber,
			ImportCountryType importCountry) {
		super();
		this.shipmentNumber = shipmentNumber;
		this.brokerageReferenceNumber = brokerageReferenceNumber;
		this.amount = amount;
		this.invoiceNumber = invoiceNumber;
		this.importCountry = importCountry;
	}
    
    /**
     * 
     * @param str
     * @return
     * @throws JAXBException
     */
    public static ShipmentsType getAsObject(String str) throws JAXBException {
    	try {
	  		JAXBContext jc = JAXBContext.newInstance(new Class[]{com.ups.ttg.bsis.shipment.model.ShipmentType.class});
	  		Unmarshaller unmarshaller = jc.createUnmarshaller();

  			return (ShipmentsType) unmarshaller.unmarshal(new ByteArrayInputStream(str.trim().getBytes()));
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
  	}
    
    /**
     * 
     * @param body
     * @return
     * @throws JAXBException
     */
    public static String getAsXml(ShipmentType body) throws JAXBException {
  		JAXBContext jc = JAXBContext.newInstance(new Class[]{com.ups.ttg.bsis.shipment.model.ShipmentType.class});
  		Marshaller marshaller = jc.createMarshaller();
  		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
  		StringWriter sw = new StringWriter();
  		marshaller.marshal(body, sw);
  		jc = null;
  		
  		return sw.toString();
  	}
    
    /**
     * 
     * @return
     * @throws JAXBException
     */
    public String getAsXml() throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(new Class[]{com.ups.ttg.bsis.shipment.model.ShipmentType.class});
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
		StringWriter sw = new StringWriter();
		marshaller.marshal(this, sw);
		jc = null;
		
		return sw.toString();
	}

	/**
     * Gets the value of the shipmentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipmentNumber() {
        return shipmentNumber;
    }

    /**
     * Sets the value of the shipmentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipmentNumber(String value) {
        this.shipmentNumber = value;
    }

    /**
     * Gets the value of the brokerageReferenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrokerageReferenceNumber() {
        return brokerageReferenceNumber;
    }

    /**
     * Sets the value of the brokerageReferenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrokerageReferenceNumber(String value) {
        this.brokerageReferenceNumber = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setAmount(AmountType value) {
        this.amount = value;
    }

    /**
     * Gets the value of the invoiceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Sets the value of the invoiceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }

    /**
     * Gets the value of the importCountry property.
     * 
     * @return
     *     possible object is
     *     {@link ImportCountryType }
     *     
     */
    public ImportCountryType getImportCountry() {
        return importCountry;
    }

    /**
     * Sets the value of the importCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportCountryType }
     *     
     */
    public void setImportCountry(ImportCountryType value) {
        this.importCountry = value;
    }

	@Override
	public String toString() {
		return "ShipmentType {\"shipmentNumber\"=\"" + shipmentNumber + "\", brokerageReferenceNumber\"=\""
				+ brokerageReferenceNumber + "\", amount\"=\"" + amount + "\", invoiceNumber\"=\"" + invoiceNumber
				+ "\", importCountry\"=\"" + importCountry + "}";
	}

}
