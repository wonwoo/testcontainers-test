package ml.wonwoo.testcontainerstest.mongo;


import ml.wonwoo.testcontainerstest.MongoDbContainer;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@DataMongoTest
@ContextConfiguration(initializers = Initializer.class)
public @interface JDataMongoIntegrationTest {


}

class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final MongoDbContainer mongoContainer = new MongoDbContainer();


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        mongoContainer.start();

        TestPropertyValues.of(

                "spring.data.mongodb.uri=mongodb://" + mongoContainer.getContainerIpAddress() + ":" + mongoContainer.getPort() + "/test"

        ).applyTo(applicationContext);
    }
}
