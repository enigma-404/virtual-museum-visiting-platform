package org.enigma.virtualmuseumvisitingplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy=true)
public class VirtualMuseumVisitingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualMuseumVisitingPlatformApplication.class, args);
    }

}
