package reward.proxy;

import gpsUtil.location.Attraction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * This interface allows to send requests to the gps api
 */
@FeignClient(name = "gps-api", url = "localhost:8080/gps")
public interface GpsProxy {

    /**
     * Send the attraction getting request
     * @param attractionName : Name of the attraction to found
     * @return The Attraction found (JSon)
     */
    @GetMapping(value = "/attraction")
    public Attraction getAttraction(String attractionName);

    /**
     * Send the attraction list getting request
     * @return The Attraction list (JSon)
     */
    @GetMapping(value = "/nearAttraction")
    public List<Attraction> getNearByAttractions();
}
