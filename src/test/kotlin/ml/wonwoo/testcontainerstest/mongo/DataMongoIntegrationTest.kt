package ml.wonwoo.testcontainerstest.mongo

import ml.wonwoo.testcontainerstest.MongoDbContainer
import ml.wonwoo.testcontainerstest.mongo.DataMongoIntegrationTest.Initializer
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
@DataMongoTest
@ContextConfiguration(initializers = [Initializer::class])
annotation class DataMongoIntegrationTest {

    companion object {

        val mongoContainer: MongoDbContainer = MongoDbContainer()

    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

        override fun initialize(applicationContext: ConfigurableApplicationContext) {

            mongoContainer.start()

            TestPropertyValues.of(

                "spring.data.mongodb.uri=mongodb://${mongoContainer.containerIpAddress}:${mongoContainer.getPort()}/test"

            ).applyTo(applicationContext)

        }
    }
}