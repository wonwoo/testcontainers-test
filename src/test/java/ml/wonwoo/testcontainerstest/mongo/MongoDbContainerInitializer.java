package ml.wonwoo.testcontainerstest.mongo;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class MongoDbContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private JMongoDbContainer mongoDbContainer = new JMongoDbContainer();

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        mongoDbContainer.start();

        TestPropertyValues.of(

                "spring.data.mongodb.uri=mongodb://" + mongoDbContainer.getContainerIpAddress() + ":" + mongoDbContainer.getPort() + "/test"

        ).applyTo(applicationContext);
    }
}
