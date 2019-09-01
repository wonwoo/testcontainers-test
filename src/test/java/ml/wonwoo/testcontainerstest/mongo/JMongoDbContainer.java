package ml.wonwoo.testcontainerstest.mongo;

import org.testcontainers.containers.GenericContainer;

public class JMongoDbContainer extends GenericContainer<JMongoDbContainer> {

    public JMongoDbContainer() {
        super("mongo:4.0.10");
    }

    public int getPort() {
        return getMappedPort(27017);
    }

}

