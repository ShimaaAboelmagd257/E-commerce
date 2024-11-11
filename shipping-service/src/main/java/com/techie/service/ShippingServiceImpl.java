package com.techie.service;

import com.shippo.Shippo;
import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;
import com.shippo.model.*;
//import com.techie.ecommerce.repository.ShipmentRepository;
import com.techie.domain.shippo.ShipmentDto;
import com.techie.domain.shippo.TransactionResponseDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service
public class ShippingServiceImpl implements ShippingService{
    private static final Log log = LogFactory.getLog(ShippingServiceImpl.class);

//    @Autowired
//    private ShipmentRepository repository;
    private RestTemplate restTemplate;
    @Value("${shippo.api.url}")
    private String shippoApiUrl;

    @Value("${shippo.api.key}")
    private String shippoApiToken;
    @Autowired
    private ModelMapper modelMapper;


    public TransactionResponseDto createShipment(ShipmentDto shipmentDto) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException {
        Shippo.setApiKey(shippoApiToken);
        String carrierAccountId = getActiveCarrierAccountId();

        HashMap<String ,Object> shippingMap = new HashMap<>();
        shippingMap.put("address_from", shipmentDto.getAddress_from());
        shippingMap.put("address_to", shipmentDto.getAddress_to());
        shippingMap.put("parcels", shipmentDto.getParcels());
        shippingMap.put("async", shipmentDto.isAsync());

        if (shipmentDto.getAddress_to() == null || shipmentDto.getAddress_from() == null) {
            throw new InvalidRequestException("Both 'address_to' and 'address_from' are required.", "Address", null);
        }
        if (shipmentDto.getParcels() == null) {
            throw new InvalidRequestException("At least one parcel is required.", "Parcels", null);
        }

            HashMap<String, Object> transactionMap = new HashMap<>();
            transactionMap.put("shipment", shippingMap);
            transactionMap.put("servicelevel_token", "usps_priority");
            transactionMap.put("carrier_account", carrierAccountId);


            Transaction transaction = Transaction.create(transactionMap);

            if (transaction.getStatus().equals("SUCCESS")) {
                log.info("Transaction label Url: "+ transaction.getLabelUrl());
                return modelMapper.map(transaction, TransactionResponseDto.class);
            } else {
                System.out.println(String.format("An error has occurred: %s", transaction.getMessages()));
                 throw new InvalidRequestException("transaction error"," "+transaction.getMessages(),new Throwable());
            }

    }
   /* public void listCarrierAccounts() throws APIException, APIConnectionException, AuthenticationException, InvalidRequestException {
        // Set Shippo API key
        Shippo.setApiKey(shippoApiToken);

        // Retrieve the list of carrier accounts
     //   CarrierAccountCollection carrierAccounts = CarrierAccount.all();
        CarrierAccountCollection carrierAccounts = CarrierAccount.all();
        List<CarrierAccount> accountList = carrierAccounts.getData();
        for (CarrierAccount account : accountList) {
            if (account.getActive()) {
                System.out.println("Carrier Account ID: " + account.getAccountId());
                System.out.println("Carrier: " + account.getCarrier());
                System.out.println("Is Test Account: " + account.getTest());
            } else {
                System.out.println("Carrier Account " + account.getObjectId() + " is inactive.");
            }
        }
    }*/
    private String getActiveCarrierAccountId() throws APIException, APIConnectionException, AuthenticationException, InvalidRequestException {
        CarrierAccountCollection accounts = CarrierAccount.all();
        for (CarrierAccount account : accounts.getData()) {
            if (account.getActive() && !account.getTest()) {
                return account.getObjectId();  // Return the first active non-test account
            }
        }
        throw new APIException("No active carrier account found",null);
    }
    @Override
    public ShipmentDto getShipmentByTrackingNumber(String trackingNumber) {
        String url = shippoApiUrl+ "/tracks/carrier/"+trackingNumber;
        ResponseEntity<ShipmentDto> response = restTemplate.getForEntity(url, ShipmentDto.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            return response.getBody();
        }else {
            throw new RuntimeException("Failed to fetch Shipment details from external API");

        }
    }

    @Override
    public List<ShipmentDto> getAllShipments() {
        String url = shippoApiUrl + "/shipments" ;
        ResponseEntity<List<ShipmentDto>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ShipmentDto>>() {});
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            return response.getBody();
        }else {
            throw new RuntimeException("Failed to fetch list of Shipments  from external API");
        }
    }

    @Override
    public ShipmentDto updateShipment(Long id, ShipmentDto shipmentDto) {
        String url = shippoApiUrl + "/transactions/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ShipmentDto> request = new HttpEntity<>(shipmentDto, headers);

        try {
            ResponseEntity<ShipmentDto> response = restTemplate.exchange(url, HttpMethod.PUT, request, ShipmentDto.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();  // Return updated shipment data
            } else {
                throw new RuntimeException("Failed to update shipment. Status code: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Failed to update shipment: " + e.getMessage(), e);
        }
    }


    @Override
    public void deleteShipment(Long id) {
        String url = shippoApiUrl+ "/transactions/"+id;
        restTemplate.delete(url);

    }
}
