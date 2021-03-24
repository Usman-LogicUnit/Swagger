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

import model.Catalog;

public class CatalogDao {

	public CatalogDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	ClientConfig clientConfig = new ClientConfig();
	Client client = ClientBuilder.newClient(clientConfig);

	public Catalog newCatalog(Catalog catalogObject) {

		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/catalog");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.post(Entity.entity(catalogObject, MediaType.APPLICATION_JSON));
		catalogObject = new Catalog();
		catalogObject = response.readEntity(Catalog.class);
		return catalogObject;
	}

	public List<Catalog> getCatalogById(String catalogId) {

		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/catalog").path(catalogId);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<Catalog> catalog = response.readEntity(new GenericType<List<Catalog>>() {});
		return catalog;
	}
}
