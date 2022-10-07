package io.muserver.docs.samples;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import io.muserver.rest.RestHandlerBuilder;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RestaurantBookingTest {
    private final MuServer server = MuServerBuilder.httpServer()

        .addHandler(
            RestHandlerBuilder.restHandler(new JacksonJaxRSExample.UserResource())
                .addCustomWriter(new JacksonJaxbJsonProvider())
                .addCustomReader(new JacksonJaxbJsonProvider())
        )
        .start();
    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    public void getCustomer() throws Exception {
        HttpResponse<String> resp = client.send(HttpRequest.newBuilder()
            .uri(server.uri().resolve("/customers"))
            .build(), HttpResponse.BodyHandlers.ofString());
        JSONObject body = new JSONObject(resp.body());
        assertThat(body.getString("customerName"), equalTo("Pranay"));
        assertThat(body.getInt("tableSize"), equalTo(4));
        assertThat(body.getDate("date"), equalTo("15/10/2022"));
		assertThat(body.getTime("time"), equalTo("20:00"));
        assertThat(body.getInt("option1"), equalTo(20));
        assertThat(body.getInt("option2"), equalTo(21));		
		    
    }

    @Test
    public void postUser() throws Exception {
		Customer customer = new Customer();
		customer.customerName = "Pranay";
            customer.tableSize = 4;
            customer.date = "15/10/2022";
			customer.time = "20:00";
			customer.option1 = 20;
			customer.option2 = 21;
        HttpResponse<String> resp = client.send(HttpRequest.newBuilder()
            .uri(server.uri().resolve("/customers"))
            .method("POST", HttpRequest.BodyPublishers.ofString("{customer))
            .header("content-type", "application/json")
            .build(), HttpResponse.BodyHandlers.ofString());
        assertThat(resp.body(), equalTo("Success"));
    }

    @After
    public void stopIt() {
        server.stop();
    }

}