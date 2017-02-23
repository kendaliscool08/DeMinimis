package com.ups.ttg.bsis.shipment.controller.impl;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ups.ttg.bsis.shipment.controller.ShipmentServiceController;
import com.ups.ttg.bsis.shipment.model.AmountType;
import com.ups.ttg.bsis.shipment.model.ImportCountryType;
import com.ups.ttg.bsis.shipment.model.ShipmentType;
import com.ups.ttg.bsis.shipment.model.ShipmentsType;

@RestController
@EnableWebMvc
public class ShipmentServiceControllerImpl implements ShipmentServiceController {
	
	@Autowired
	private ActiveMQConnectionFactory ConnectionFactory;
	
	@Value("${amq.queueName}")
	private String shipmentQueueName;
	
	private static final Logger LOGGER = LogManager.getLogger(ShipmentServiceControllerImpl.class);
	
	public ShipmentServiceControllerImpl() {
		System.out.println("Transaction Information instantiated ... ");
	}

	@Async
	@RequestMapping(value = "/shipments", method = RequestMethod.GET)
	public Object getShipmentInformation(HttpServletResponse response, 
			@RequestParam(value = "shipmentNumber", required = true) String shipmentNumber,
			@RequestParam(value = "brn", required = false) String brn,
			@RequestParam(value = "amountValue", required = true) float amountValue,
			@RequestParam(value = "invoiceNumber", required = true) String invoiceNumber,
			@RequestParam(value = "importCountryCode", required = true) String importCountryCode) {
		
		try {
			LOGGER.info("RECEIVE: Received new GET request... Parameters provided: shipmentNumber=" + shipmentNumber +
						", brn=" + brn + ", amountValue=" + amountValue + ", invoiceNumber=" + invoiceNumber + 
						", importCountryCode=" + importCountryCode);
			
			LOGGER.debug("Attempting to write request ");
			
			ShipmentsType shipments = new ShipmentsType(new ShipmentType(shipmentNumber, 
																		 brn, 
																		 new AmountType(amountValue), 
																		 invoiceNumber,
																		 new ImportCountryType(importCountryCode)));
			
			String xml = shipments.getAsXml();
			
			LOGGER.debug("Writing message to the shipment queue: " + xml);
			
			boolean successfulWrite = writeToQueue(shipmentQueueName, shipments.getAsXml());
			if (successfulWrite) {
				LOGGER.info("Message was successfully written to the shipment queue");
	            return shipments;
			}
			else {
	            LOGGER.error("Message wasn't written to the shipment queue... Returning error response");
	            return "Unable to process message";
			}
        }
        catch (Exception e) {
        	e.printStackTrace();
            LOGGER.error(e.getMessage());
            return "Unable to process message";
        }
	}
	
	@Async
	@RequestMapping(value = "/shipments-post", method = RequestMethod.POST)
	public Object getShipmentPostInformation(HttpServletResponse response, @RequestBody ShipmentsType shipments) {
		try {
			LOGGER.info("RECEIVE: Received new POST request... Body: " + shipments.getAsXml());
						
			boolean successfulWrite = writeToQueue(shipmentQueueName, shipments.getAsXml());
			if (successfulWrite) {
				LOGGER.info("Message was successfully written to the shipment queue");
	            return shipments;
			}
			else {
	            LOGGER.error("Message wasn't written to the shipment queue... Returning error response");
	            return "Unable to process message";
			}
        }
        catch (Exception e) {
        	e.printStackTrace();
            LOGGER.error(e.getMessage());
            return "Unable to process message";
        }
	}
	
	/**
	 * 
	 * @param queueName
	 * @param message
	 * @return true if message was successfully writen
	 */
	public boolean writeToQueue(String queueName, String message) {
		if (message == null || message.length() <= 0) {
			return false;
		}
		try {
			LOGGER.trace("Creating new connection");
            // Create a Connection
            Connection connection = ConnectionFactory.createConnection();
            connection.start();
            LOGGER.trace("Connection successfully created");
            
            // Create a Session
            LOGGER.trace("Creating new session");
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            LOGGER.trace("Session successfully created");

            // Create the destination (Topic or Queue)
            LOGGER.trace("Creating new destination queue");
            Destination destination = session.createQueue(queueName);
            LOGGER.trace("Destination queue successfully created");

            // Create a MessageProducer from the Session to the Topic or Queue
            LOGGER.trace("Creating new message producer");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            LOGGER.trace("Message producer successfully created");

            // Create a messages
            LOGGER.trace("Sending message created with param");
            TextMessage testMessage = session.createTextMessage(message);
        	producer.send(testMessage);
        	LOGGER.trace("Message successfully sent to queue");
            
            // Clean up
        	LOGGER.trace("Closing session and connection");
            session.close();
            connection.close();
            LOGGER.trace("All connections closed");
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
		return true;
	}
	
	public Object sendTransactionInformation(Object body) throws Exception {
		return body;
	}

	public ActiveMQConnectionFactory getConnectionFactory() {
		return ConnectionFactory;
	}

	public void setConnectionFactory(ActiveMQConnectionFactory connectionFactory) {
		ConnectionFactory = connectionFactory;
	}

	public String getShipmentQueueName() {
		return shipmentQueueName;
	}

	public void setShipmentQueueName(String shipmentQueueName) {
		this.shipmentQueueName = shipmentQueueName;
	}
}
