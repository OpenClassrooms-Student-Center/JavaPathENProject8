package reward.proxy;

import gpsUtil.location.Attraction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "gps-api", url = "localhost:8080/gps")
public interface GpsProxy {

    @GetMapping(value = "/attraction")
    public Attraction getAttraction(String attractionName);

    @GetMapping(value = "/nearAttraction")
    public List<Attraction> getNearByAttractions();
}
