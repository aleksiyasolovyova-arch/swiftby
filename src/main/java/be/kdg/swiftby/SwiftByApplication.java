package be.kdg.swiftby;

import be.kdg.swiftby.config.DotenvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SwiftByApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SwiftByApplication.class)
                .initializers(new DotenvInitializer())
                .run(args);
    }

}
