package ml.wonwoo.testcontainerstest.mongo;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@DataMongoTest
@ContextConfiguration(initializers = JMongoDbContainerInitializer.class)
public @interface JDataMongoIntegrationTest {


}
