package org.openpsn.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openpsn.client.response.TrophyTitleResponse;
import org.openpsn.client.rest.Method;
import org.openpsn.client.rest.RequestEntity;
import org.openpsn.client.rest.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PsnTrophyClientTest {
    @Captor
    private ArgumentCaptor<RequestEntity<?>> requestCaptor;

    @Mock
    private RestClient restClient;

    private PsnTrophyClient client;

    @BeforeEach
    public void setUp() {
        this.client = new PsnTrophyClient(restClient, null);
    }

    @AfterEach
    public void tearDown() {
        System.clearProperty("openpsn.client.trophyUrlBase");
    }

    @Test
    public void getTrophyTitles_should_useDefaultUrlBase() {
        client.getTrophyTitles();

        verify(restClient).requestObjectAsync(requestCaptor.capture(), eq(TrophyTitleResponse.class));

        final var request = requestCaptor.getValue();
        assertEquals(Method.GET, request.getMethod());
        assertEquals("https://m.np.playstation.com/api/trophy/v1/users/me/trophyTitles",
            request.getUri().toString());
    }

    @Test
    public void getTrophyTitles_should_overrideUrlBase() {
        System.setProperty("openpsn.client.trophyUrlBase", "https://google.com");

        client.getTrophyTitles();

        verify(restClient).requestObjectAsync(requestCaptor.capture(), eq(TrophyTitleResponse.class));

        final var request = requestCaptor.getValue();
        assertEquals(Method.GET, request.getMethod());
        assertEquals("https://google.com/users/me/trophyTitles",
            request.getUri().toString());
    }
}
