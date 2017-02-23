package com.ups.ttg.bsis.shipment.controller;

import javax.servlet.http.HttpServletResponse;

import com.ups.ttg.bsis.shipment.model.ShipmentsType;

public interface ShipmentServiceController {
	public Object getShipmentInformation(HttpServletResponse response, String shipmentNumber, String brn, float amountValue, String invoiceNumber, String importCountryCode);
	public Object getShipmentPostInformation(HttpServletResponse response, ShipmentsType shipments);
	public Object sendTransactionInformation(Object body) throws Exception;
}
