package org.openpsn.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.model.MediaType;
import org.openpsn.IntegrationTest;
import org.openpsn.client.rest.RestClient;
import org.openpsn.test.junit.extension.SystemPropertiesExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.openpsn.test.util.ResourceUtils.getResourceBytes;

@ExtendWith(SystemPropertiesExtension.class)
public class PsnTrophyClientIntegrationTest extends IntegrationTest {
    private PsnTrophyClient client;

    @BeforeEach
    public void setUp() {
        final var restClient = new RestClient();
        client = new PsnTrophyClient(restClient, "test");
        System.setProperty("openpsn.client.trophyUrlBase", mockServerHost);

    }

    @AfterEach
    public void tearDown() {
        mockServerClient.reset();
    }

    @Test
    public void getTrophyTitles_should_returnPayloadIfSuccessful() throws IOException {
        mockServerClient
            .when(
                request()
                    .withPath("/users/me/trophyTitles")
                    .withHeader("Authorization", "Bearer test")
            )
            .respond(
                response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(getResourceBytes("responses/trophies/trophyTitles.json"))
            );

        final var response = client.getTrophyTitles().join();
        assertThat(response.trophyTitles()).hasSize(2);
    }

    @Test
    public void getTitleTrophies_should_returnPayloadIfSuccessful() throws IOException {
        mockServerClient
            .when(
                request()
                    .withPath("/npCommunicationIds/test/trophyGroups/all/trophies")
                    .withHeader("Authorization", "Bearer test")
            )
            .respond(
                response()
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(getResourceBytes("responses/trophies/titleTrophies.json"))
            );

        final var response = client.getTitleTrophies("test").join();
        assertThat(response.trophies()).hasSize(2);
    }
}
