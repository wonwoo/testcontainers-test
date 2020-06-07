package ml.wonwoo.testcontainerstest.mongo;

import com.mongodb.client.*;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class JDockerComposeTests {

    private final int REDIS_PORT = 6379;
    private final int MONGO_PORT = 27017;

    @Container
    private final DockerComposeContainer dockerCompose = new DockerComposeContainer(new File("src/test/resources/docker-compose.yaml"))
            .withExposedService("redis_1", REDIS_PORT)
            .withExposedService("mongo_1", MONGO_PORT);


    @Test
    void docker_compose_test() {

        int port = dockerCompose.getServicePort("mongo_1", MONGO_PORT);
        String host = dockerCompose.getServiceHost("mongo_1", MONGO_PORT);

        MongoClient mongoClient = MongoClients.create("mongodb://" + host + ":" + port);
        MongoDatabase database = mongoClient.getDatabase("test");

        MongoCollection<Document> collection = database.getCollection("users");

        Document document = new Document("name", "wonwoo")
                .append("email", "test@test.com");

        collection.insertOne(document);

        FindIterable<Document> documents = collection.find();

        assertThat(documents).hasSize(1);

        documents.forEach((Consumer<? super Document>) it -> {

            assertThat(it.get("name")).isEqualTo("wonwoo");
            assertThat(it.get("email")).isEqualTo("test@test.com");

        });

    }


}
