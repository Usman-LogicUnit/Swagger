package dao;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.google.common.net.HttpHeaders;

import model.Catalog;

public class CatalogDao {

	public CatalogDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	ClientConfig clientConfig = new ClientConfig();
	Client client = ClientBuilder.newClient(clientConfig);

	public Response newCatalog(Catalog catalogObject) {
		System.out.println("Requesting...\n\n");
		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/catalog");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.post(Entity.entity(catalogObject, MediaType.APPLICATION_JSON));
		catalogObject = new Catalog();
		catalogObject = response.readEntity(Catalog.class);
		if(catalogObject==null)
		{
			String message = "Error Occured in Creating Catalog!";
			System.out.println("Error occured in creating catalog");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
		}
		else {
			System.out.println("Successfully created Catalog");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
		return Response.status(Response.Status.CREATED).entity(catalogObject).build();
	}
	}

	public Response getCatalogById(String catalogId) {
		System.out.println("Requesting...\n\n");
		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/catalog").path(catalogId);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<Catalog> catalog = response.readEntity(new GenericType<List<Catalog>>() {
		});

		if (catalog.size() == 0 || catalog == null) {
			String message = "Unable to fetch catalog details OR Catalog don't exist";
			System.out.println("Unable to fetch catalog details OR Catalog don't exist");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.NO_CONTENT).entity(message).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET").build();
		} else {
			System.out.println("Successfully Fetched catalog");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.OK).entity(new GenericEntity<List<Catalog>>(catalog) {
			}).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET").build();
		}
	}
}
