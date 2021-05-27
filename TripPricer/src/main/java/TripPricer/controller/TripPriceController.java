package TripPricer.controller;

import TripPricer.service.TripPricerService;
import TripPricer.service.TripPricerServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripPriceController {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private TripPricerServiceInterface tripPricerServiceInterface;

    public TripPriceController() {
        logger.info("TripPriceController()");

        tripPricerServiceInterface = new TripPricerService();
    }

    public TripPriceController(TripPricerServiceInterface tripPricerServiceInterface) {
        logger.info("TripPriceController(" + tripPricerServiceInterface + ")");

        this.tripPricerServiceInterface = tripPricerServiceInterface;
    }

    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) throws JsonProcessingException {
        logger.info("getTripDeals(" + userName + ")");

        return objectMapper.writeValueAsString(tripPricerServiceInterface.getTripDeals(userName));
    }
}
