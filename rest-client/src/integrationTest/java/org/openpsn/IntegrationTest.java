package org.openpsn;

import org.mockserver.client.MockServerClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.utility.DockerImageName;

public class IntegrationTest {
    protected static MockServerContainer MOCK_SERVER = new MockServerContainer(
        DockerImageName.parse("mockserver/mockserver")
            .withTag(MockServerClient.class.getPackage().getImplementationVersion())
    );

    static {
        MOCK_SERVER.start();
    }

    protected final MockServerClient mockServerClient;
    protected final String mockServerHost;

    public IntegrationTest() {
        mockServerClient = new MockServerClient(
            MOCK_SERVER.getHost(),
            MOCK_SERVER.getServerPort());

        mockServerHost = String.format(
            "http://%s:%d",
            MOCK_SERVER.getHost(),
            MOCK_SERVER.getServerPort());
    }
}
