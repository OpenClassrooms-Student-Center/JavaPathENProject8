package unit.controller;

import TripPricer.controller.TripPriceController;
import TripPricer.service.TripPricerServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import tripPricer.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class TripPricerControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private TripPriceController tripPriceController;

    private TripPricerServiceInterface tripPricerServiceInterface = Mockito.mock(TripPricerServiceInterface.class);

    @Before
    public void beforeEach() {

        tripPriceController = new TripPriceController(tripPricerServiceInterface);
    }

    @Test
    public void calculateTripDeals() throws JsonProcessingException {

        //GIVEN
        String userName = "userName";

        //WHEN
        tripPriceController.calculateTripDeals(userName);

        //THEN
        Mockito.verify(tripPricerServiceInterface, Mockito.times(1)).calculateTripDeals(userName);
    }
}