package ml.wonwoo.testcontainerstest.mongo

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import ml.wonwoo.testcontainerstest.KDockerComposeContainer
import org.assertj.core.api.Assertions.assertThat
import org.bson.Document
import org.junit.jupiter.api.Test
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File


@Testcontainers
class DockerComposeTests {

    companion object {

        private const val REDIS_PORT = 6379
        private const val MONGO_PORT = 27017

    }

    @Container
    var dockerCompose: KDockerComposeContainer = KDockerComposeContainer(File("src/test/resources/docker-compose.yaml"))
        .apply {

            withExposedService("redis_1", REDIS_PORT)
            withExposedService("mongo_1", MONGO_PORT)

        }

    @Test
    fun `docker compose test`() {


        val port = dockerCompose.getServicePort("mongo_1", MONGO_PORT)
        val host = dockerCompose.getServiceHost("mongo_1", MONGO_PORT)

        val mongoClient = MongoClients.create("mongodb://$host:$port")
        val database = mongoClient.getDatabase("test")

        val collection = database.getCollection("users")

        val document = Document("name", "wonwoo")
            .append("email", "test@test.com")

        collection.insertOne(document)

        collection.find().apply {

            assertThat(this).hasSize(1)

            forEach {

                assertThat(it["name"]).isEqualTo("wonwoo")
                assertThat(it["email"]).isEqualTo("test@test.com")

            }
        }
    }

}
