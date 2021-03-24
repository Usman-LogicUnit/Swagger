package dao;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
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

	public Category newCategory(Category category) {

		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("category");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.post(Entity.entity(category, MediaType.APPLICATION_JSON));
		category = response.readEntity(Category.class);

		return category;
	}

	public List<Category> getCategories(String categoryId) {
		WebTarget webTarget = client.target("http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService").path("category").path(categoryId);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<Category> categories;
		categories = response.readEntity(new GenericType<List<Category>>() {
		});
		return categories;
	}

}
