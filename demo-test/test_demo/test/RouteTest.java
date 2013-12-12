import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.routeAndCall;

public class RouteTest {

    @Test
    public void rootRouteTest() {
        Result result = routeAndCall(fakeRequest(GET, "/"));
        assertThat(result).isNotNull();
    }

    @Test
    public void badRouteTest() {
        Result result = routeAndCall(fakeRequest(GET, "/api/v1/contestant"));
        assertThat(result).isNotNull();
    }
}
