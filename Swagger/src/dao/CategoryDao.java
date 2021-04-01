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

import model.Category;

public class CategoryDao {

	public CategoryDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	ClientConfig clientConfig = new ClientConfig();
	Client client = ClientBuilder.newClient(clientConfig);

	public Response newCategory(Category category) {
		System.out.println("Requesting...\n\n");
		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("category");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.post(Entity.entity(category, MediaType.APPLICATION_JSON));
		category = response.readEntity(Category.class);
		if (category == null) {
			String message = "Unable to create category";
			System.out.println("Error occured! Unable to create category");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
		} else {
			System.out.println("Successfully created category");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.CREATED).entity(category).build();
		}
	}

	public Response getCategories(String categoryId) {
		System.out.println("Requesting...\n\n");
		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("category").path(categoryId);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<Category> categories = response.readEntity(new GenericType<List<Category>>() {
		});

		if (categories.size() == 0 || categories == null) {
			String message = "Category don't exist";
			System.out.println("Unable to fetch category details OR Category don't exist");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.NO_CONTENT).entity(message).build();
		} else {
			System.out.println("Successfully Fetched category");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.OK).entity(new GenericEntity<List<Category>>(categories) {
			}).build();
		}
	}

}
