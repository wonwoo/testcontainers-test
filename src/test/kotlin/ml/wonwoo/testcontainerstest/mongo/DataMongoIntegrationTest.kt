package ml.wonwoo.testcontainerstest.mongo

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.ContextConfiguration
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
@DataMongoTest
@ContextConfiguration(initializers = [MongoDbContainerInitializer::class])
annotation class DataMongoIntegrationTest {


}