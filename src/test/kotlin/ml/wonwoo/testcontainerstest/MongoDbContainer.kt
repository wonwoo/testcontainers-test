package ml.wonwoo.testcontainerstest

import org.testcontainers.containers.GenericContainer

class MongoDbContainer : GenericContainer<MongoDbContainer>(DEFAULT_IMAGE_AND_TAG) {

    companion object {
        const val MONGODB_PORT = 27017
        const val DEFAULT_IMAGE_AND_TAG = "mongo:4.0.10"
    }

    fun getPort(): Int = getMappedPort(MONGODB_PORT)

}