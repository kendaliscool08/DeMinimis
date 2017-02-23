package com.ups.ttg.bsis.queue.listener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ups.ttg.bsis.shipment.model.ShipmentType;
import com.ups.ttg.bsis.shipment.model.ShipmentsType;
import com.ups.ttg.bsis.utils.RestfulWebClient;

import redis.clients.jedis.Jedis;

@Component
public class QueueListener implements MessageListener {
	
	@Autowired
	private Jedis jedis;
	
	@Value("${consolidatedShipments.url}")
	private String consolidatedShipmentsUrl;
	
	private static final Logger LOGGER = LogManager.getLogger(QueueListener.class);

	public void onMessage(Message message) {
		try {
			TextMessage msg = (TextMessage) message;
			System.out.println("Consumed message: " + msg.getText());
			LOGGER.info("Consumed message: " + msg.getText());
			
			ShipmentsType shipments = ShipmentsType.getAsObject(msg.getText());			
			
			List<String> shipmentNumbersConsolidated = new ArrayList<String>();
			List<String> shipmentsThatDoNotNeedConsolidation = new ArrayList<String>();
			
			for (ShipmentType shipment : shipments.getShipment()) {
				String countryCode = shipment.getImportCountry().getCode();
				BigDecimal amountValue = new BigDecimal(shipment.getAmount().getValue());
				String currencyCode = shipment.getAmount().getCurrencyCode();
					
				System.out.println("Country Code: " + countryCode + ", Amount value: " + amountValue + ", Amount currency code: " + currencyCode);
				LOGGER.info("Country Code: " + countryCode + ", Amount value: " + amountValue + ", Amount currency code: " + currencyCode);
				
				System.out.println("Getting redis values  "+ jedis.get(countryCode));
				LOGGER.info("Getting redis values  "+ jedis.get(countryCode));
								
				BigDecimal countryDeminimisValue;
				try {
					if (StringUtils.isNotBlank(jedis.get(countryCode))) {
						countryDeminimisValue = new BigDecimal(jedis.get(countryCode));
					}
					else {
						countryDeminimisValue = new BigDecimal(-9999.99);
					}
				}
				catch (Exception e) {
					countryDeminimisValue = new BigDecimal(-9999.99);
				}
				
				switch (amountValue.compareTo(countryDeminimisValue)){
					// Does not require consolidation because shipment value is less than the DeMinimis
					case -1:
					case 0:
						shipmentsThatDoNotNeedConsolidation.add(shipment.getShipmentNumber());
						break;
						
					// Requires consolidation because shipment value is equal or greater than the DeMinimis
					case 1:
						shipmentNumbersConsolidated.add(shipment.getShipmentNumber());
						break;
				}
			} // End For
			
			System.out.println("Non-consolidated: " + shipmentsThatDoNotNeedConsolidation + ", Consolidated: " + shipmentNumbersConsolidated);
			LOGGER.info("Non-consolidated: " + shipmentsThatDoNotNeedConsolidation + ", Consolidated: " + shipmentNumbersConsolidated);
			
			RestfulWebClient.sendPostRequest(consolidatedShipmentsUrl, "Non-consolidated: " + shipmentsThatDoNotNeedConsolidation + ", Consolidated: " + shipmentNumbersConsolidated);
		} 
		catch (Exception e) {
			if (jedis != null && jedis.isConnected()) {
				jedis.disconnect();
			}
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public String getConsolidatedShipmentsUrl() {
		return consolidatedShipmentsUrl;
	}

	public void setConsolidatedShipmentsUrl(String consolidatedShipmentsUrl) {
		this.consolidatedShipmentsUrl = consolidatedShipmentsUrl;
	}

	@Override
	public String toString() {
		return "QueueListener {\"jedis\"=\"" + jedis + "}";
	}
}
