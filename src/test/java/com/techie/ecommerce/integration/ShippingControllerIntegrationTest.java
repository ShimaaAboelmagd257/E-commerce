package com.techie.ecommerce.integration;

import com.shippo.Shippo;
import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;
import com.shippo.model.*;
import com.techie.ecommerce.domain.dto.shippo.*;
import com.techie.ecommerce.service.ShippingService;
import com.techie.ecommerce.service.ShippingServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ShippingControllerIntegrationTest {
    private static final Log log = LogFactory.getLog(ProductControllerIntegrationTest.class);


    @Mock
    private ModelMapper modelMapper;
    private HashMap<String, Object> shipmentMap;
    @InjectMocks
    private ShippingService shipmentService = new ShippingServiceImpl();
    @Value("${shippo.api.key}")
    private String shippoApiToken;

    private ShipmentDto shipmentDto;
    private HashMap<String, Object> transactionDto;
    private Transaction transaction;
    private TransactionResponseDto transactionResponseDto;


    @BeforeEach
    public void setUp() {
       // Shippo.setApiKey(shippoApiToken);
        MockitoAnnotations.initMocks(this);

    }
    private String getActiveCarrierAccountId() throws APIException, APIConnectionException, AuthenticationException, InvalidRequestException {
        CarrierAccountCollection accounts = CarrierAccount.all();
        for (CarrierAccount account : accounts.getData()) {
            if (account.getActive() && !account.getTest()) {
                return account.getObjectId();  // Return the first active non-test account
            }
        }
        throw new APIException("No active carrier account found",null);
    }

    @Test
    void testCreateShipment_SuccessfulTransaction() throws Exception {
        Shippo.setApiKey(shippoApiToken);
        AddressDto addressFrom = new AddressDto();
        addressFrom.setName("Ms Hippo");
        addressFrom.setCompany("San Diego Zoo");
        addressFrom.setStreet1("2920 Zoo Drive");
        addressFrom.setCity("San Diego");
        addressFrom.setState("CA");
        addressFrom.setZip("92101");
        addressFrom.setCountry("US");
        addressFrom.setPhone("+1 619 231 1515");
        addressFrom.setEmail("mshippo@goshipppo.com");

        AddressDto addressTo = new AddressDto();
        addressTo.setName("Mr Hippo");
        addressTo.setCompany("Shippo");
        addressTo.setStreet1("215 Clayton St.");
        addressTo.setCity("San Francisco");
        addressTo.setState("CA");
        addressTo.setZip("94117");
        addressTo.setCountry("US");
        addressTo.setPhone("+1 555 341 9393");
        addressTo.setEmail("mrhippo@goshipppo.com");

        // Create valid ShipmentDto
        ShipmentDto shipmentDto = new ShipmentDto();
        shipmentDto.setAddress_from(addressFrom);
        shipmentDto.setAddress_to(addressTo);

        // Create the parcel (assuming it's a simple DTO as well)
        ParcelDto parcel = new ParcelDto();
        parcel.setLength("5");
        parcel.setWidth("5");
        parcel.setHeight("5");
        parcel.setDistance_unit("in");
        parcel.setWeight("2");
        parcel.setMass_unit("lb");

        shipmentDto.setParcels(parcel);
        shipmentDto.setAsync(false);

        Transaction transactionMock = mock(Transaction.class);
        when(transactionMock.getStatus()).thenReturn("SUCCESS");
        when(transactionMock.getLabelUrl()).thenReturn("http://label.url");

        TransactionResponseDto responseDto = new TransactionResponseDto();
        when(modelMapper.map(any(Transaction.class), eq(TransactionResponseDto.class))).thenReturn(responseDto);

        TransactionResponseDto result = shipmentService.createShipment(shipmentDto);

        assertNotNull(result);
        verify(modelMapper, times(1)).map(any(Transaction.class), eq(TransactionResponseDto.class));
    }
    @Test
    public void testCreateShipmentSuccess() throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException {
//        HashMap<String, Object> shipmentMap = new HashMap<>();
//        when(modelMapper.map(shipmentDto, new TypeToken<HashMap<String, Object>>() {}.getType())).thenReturn(shipmentMap);
//        log.info("created shipment is empty? "+ shipmentMap.isEmpty());
//        // Mock Transaction creation
//        log.info("created Transaction status? "+ transaction.getStatus());
        HashMap<String, Object> addressToMap = new HashMap<String, Object>();
        addressToMap.put("name", "Mr Hippo");
        addressToMap.put("company", "Shippo");
        addressToMap.put("street1", "215 Clayton St.");
        addressToMap.put("city", "San Francisco");
        addressToMap.put("state", "CA");
        addressToMap.put("zip", "94117");
        addressToMap.put("country", "US");
        addressToMap.put("phone", "+1 555 341 9393");
        addressToMap.put("email", "mrhippo@goshipppo.com");

// From Address
        HashMap<String, Object> addressFromMap = new HashMap<String, Object>();
        addressFromMap.put("name", "Ms Hippo");
        addressFromMap.put("company", "San Diego Zoo");
        addressFromMap.put("street1", "2920 Zoo Drive");
        addressFromMap.put("city", "San Diego");
        addressFromMap.put("state", "CA");
        addressFromMap.put("zip", "92101");
        addressFromMap.put("country", "US");
        addressFromMap.put("email", "mshippo@goshipppo.com");
        addressFromMap.put("phone", "+1 619 231 1515");
        addressFromMap.put("metadata", "Customer ID 123456");

// Parcel
        HashMap<String, Object> parcelMap = new HashMap<String, Object>();
        parcelMap.put("length", "5");
        parcelMap.put("width", "5");
        parcelMap.put("height", "5");
        parcelMap.put("distance_unit", "in");
        parcelMap.put("weight", "2");
        parcelMap.put("mass_unit", "lb");

// Shipment
        HashMap<String, Object> shipmentMap = new HashMap<String, Object>();
        shipmentMap.put("address_to", addressToMap);
        shipmentMap.put("address_from", addressFromMap);
        shipmentMap.put("parcels", parcelMap);
        shipmentMap.put("async", false);

// Transaction
        HashMap<String, Object> transactionMap = new HashMap<String, Object>();
        transactionMap.put("shipment", shipmentMap);
        transactionMap.put("servicelevel_token", "usps_priority");
        transactionMap.put("carrier_account", "shippo_test_usps");

        Transaction transaction = Transaction.create(transactionMap);
       // when(Transaction.create(any(HashMap.class))).thenReturn(transaction);
        log.info("created Transaction status? "+ transaction.getStatus());

        // Mock mapping of Transaction to TransactionResponseDto
        when(modelMapper.map(transaction, TransactionResponseDto.class)).thenReturn(transactionResponseDto);

        // Test the createShipment method
        TransactionResponseDto response = shipmentService.createShipment(shipmentDto);

        // Assertions
        assertEquals("http://test-label-url.com", response.getLabel_url());
        assertEquals("TRACK123456", response.getTracking_number());
    }

    @Test
    public void testCreateShipmentFailure() throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException {
        // Mocking a failed shipment creation
        Shipment mockShipment = mock(Shipment.class);
        when(mockShipment.getStatus()).thenReturn("FAILED");

        // Simulate failed shipment creation
        assertThrows(InvalidRequestException.class, () -> {
            shipmentService.createShipment(mockShipmentDto());
        });
    }

    // Mock data for address, parcel, and rates
    private HashMap<String, Object> mockAddressTo() {
        HashMap<String, Object> addressToMap = new HashMap<>();
        addressToMap.put("name", "Mr Hippo");
        addressToMap.put("company", "Shippo");
        addressToMap.put("street1", "215 Clayton St.");
        addressToMap.put("city", "San Francisco");
        addressToMap.put("state", "CA");
        addressToMap.put("zip", "94117");
        addressToMap.put("country", "US");
        addressToMap.put("phone", "+1 555 341 9393");
        addressToMap.put("email", "mrhippo@goshipppo.com");
        return addressToMap;
    }

    private HashMap<String, Object> mockAddressFrom() {
        HashMap<String, Object> addressFromMap = new HashMap<>();
        addressFromMap.put("name", "Ms Hippo");
        addressFromMap.put("company", "San Diego Zoo");
        addressFromMap.put("street1", "2920 Zoo Drive");
        addressFromMap.put("city", "San Diego");
        addressFromMap.put("state", "CA");
        addressFromMap.put("zip", "92101");
        addressFromMap.put("country", "US");
        addressFromMap.put("phone", "+1 619 231 1515");
        addressFromMap.put("email", "mshippo@goshipppo.com");
        return addressFromMap;
    }

    private HashMap<String, Object> mockParcel() {
        HashMap<String, Object> parcelMap = new HashMap<>();
        parcelMap.put("length", "5");
        parcelMap.put("width", "5");
        parcelMap.put("height", "5");
        parcelMap.put("distance_unit", "in");
        parcelMap.put("weight", "2");
        parcelMap.put("mass_unit", "lb");
        return parcelMap;
    }

    private List<Rate> mockRates() {
        Rate rate = mock(Rate.class);
        when(rate.getObjectId()).thenReturn("rate123");
        return List.of(rate);
    }

    private ShipmentDto mockShipmentDto() {
        ShipmentDto dto = new ShipmentDto();
        // Populate with mock data as needed
        return dto;
    }

}
