package io.muserver.docs.samples;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import io.muserver.rest.RestHandlerBuilder;

import javax.ws.rs.*;
import java.util.Date;

public class RestaurantBooking {

    public static void main(String[] args) {
        UserResource userResource = new UserResource();
        MuServer server = MuServerBuilder.httpServer()
            .addHandler(
                RestHandlerBuilder.restHandler(userResource)
                    .addCustomWriter(new JacksonJaxbJsonProvider())
                    .addCustomReader(new JacksonJaxbJsonProvider())
            )
            .start();

        System.out.println("API example: " + server.uri().resolve("/customers"));
    }

    public static class Customer {
        public String customerName;
        public int tableSize;
        public Date date;
		public Time time;
		public int option1;
		public int option2;
    }

    @Path("/customer")
    static class CustomerResource {

        @GET
        @Produces("application/json")
        public Customer getCustomer() {
            Customer customer = new Customer();
            customer.customerName = "Pranay";
            customer.tableSize = 4;
            customer.date = new Date(15/10/2022);
			customer.time = "20:00";
			customer.option1 = 20;
			customer.option2 = 21;
            return customer;
        }

        @POST
        @Consumes("application/json")
        @Produces("text/plain")
        public String postUser(Customer customer) {
            return "customerName =" + customer.customerName
                + " and tableSize " + customer.tableSize + " and date " + customer.date
				+ " and time " + customer.time + " and option1 " + customer.option1
				+ " and option2 " + customer.option2;
        }

    }

}
