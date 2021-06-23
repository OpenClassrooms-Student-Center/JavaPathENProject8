package TripPricer.controller;

import TripPricer.service.TripPricerService;
import TripPricer.service.TripPricerServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class allows to intercept trip pricer requests
 */
@RestController
public class TripPriceController {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    @Autowired
    private TripPricerServiceInterface tripPricerServiceInterface;

    @Autowired
    private TripPricerService tripPricerService;

    /**
     * Creates a new TripPriceController
     */
    public TripPriceController() {
        logger.info("TripPriceController()");

        tripPricerServiceInterface = tripPricerService;
    }

    /**
     * Creates a new TripPriceController with the specified TripPricerServiceInterface
     * @param tripPricerServiceInterface : service that this controller will use
     */
    public TripPriceController(TripPricerServiceInterface tripPricerServiceInterface) {
        logger.info("TripPriceController(" + tripPricerServiceInterface + ")");

        this.tripPricerServiceInterface = tripPricerServiceInterface;
    }

    /**
     * Intercepts the provider list getting request
     * @param userName : Name of the User to use for creating the Provider list
     * @return The Provider list (JSon)
     */
    @GetMapping("/calculateTripDeals")
    public String calculateTripDeals(@RequestParam String userName) throws JsonProcessingException {
        logger.info("calculateTripDeals(" + userName + ")");

        return objectMapper.writeValueAsString(tripPricerServiceInterface.calculateTripDeals(userName));
    }
}