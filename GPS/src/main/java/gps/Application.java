package gps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Locale;

@SpringBootApplication
@EnableFeignClients("TourGuide-Gps")
public class Application {

    public Application() {

        Locale.setDefault(Locale.US);
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
