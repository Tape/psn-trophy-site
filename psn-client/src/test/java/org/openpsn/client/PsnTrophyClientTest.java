package org.openpsn.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openpsn.client.response.TitleTrophiesResponse;
import org.openpsn.client.response.TrophyTitlesResponse;
import org.openpsn.client.rest.Method;
import org.openpsn.client.rest.RequestEntity;
import org.openpsn.client.rest.RestClient;
import org.openpsn.test.junit.extension.SystemPropertiesExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class, SystemPropertiesExtension.class})
class PsnTrophyClientTest {
    @Captor
    private ArgumentCaptor<RequestEntity<?>> requestCaptor;

    @Mock
    private RestClient restClient;

    private PsnTrophyClient client;

    @BeforeEach
    public void setUp() {
        this.client = new PsnTrophyClient(restClient, "test");
    }

    @Test
    public void getX_should_overrideUrlBase() {
        System.setProperty("openpsn.client.trophyUrlBase", "https://google.com");

        client.getTrophyTitles();

        verify(restClient).requestObjectAsync(requestCaptor.capture(), eq(TrophyTitlesResponse.class));

        final var request = requestCaptor.getValue();
        assertThat(request.getMethod()).isEqualTo(Method.GET);
        assertThat(request.getUri()).asString()
            .isEqualTo("https://google.com/users/me/trophyTitles");
    }

    @Test
    public void getTrophyTitles_should_buildUrl() {
        client.getTrophyTitles();

        verify(restClient).requestObjectAsync(requestCaptor.capture(), eq(TrophyTitlesResponse.class));

        final var request = requestCaptor.getValue();
        assertThat(request.getMethod()).isEqualTo(Method.GET);
        assertThat(request.getUri()).asString()
            .isEqualTo("https://m.np.playstation.com/api/trophy/v1/users/me/trophyTitles");
    }

    @Test
    public void getTitleTrophies_should_buildUrl() {
        client.getTitleTrophies("test");

        verify(restClient).requestObjectAsync(requestCaptor.capture(), eq(TitleTrophiesResponse.class));

        final var request = requestCaptor.getValue();
        assertThat(request.getMethod()).isEqualTo(Method.GET);
        assertThat(request.getUri()).asString()
            .isEqualTo("https://m.np.playstation.com/api/trophy/v1/npCommunicationIds/test/trophyGroups/all/trophies");
    }
}
