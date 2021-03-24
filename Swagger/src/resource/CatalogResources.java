package resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.CatalogDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Catalog;

@Path("/Catalogs")
@Api(value="Catalogs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CatalogResources {
	
	CatalogDao catalogDao=new CatalogDao();
	
	@POST
	@ApiOperation(value="Create a catalog",notes="Add a new Catalog in Product Catalog",
	response=Catalog.class)
	public Catalog AddCatalog(Catalog catalogObject) {
		return catalogDao.newCatalog(catalogObject);
	}
	
	@GET
	@Path("/{catalogId}")
	@ApiOperation(value="Catalog detail", notes="Catalog along with all categories included in cataog",
	response=Catalog.class,
	responseContainer ="List" )
	public List<Catalog> GetCatalog(@PathParam("catalogId") String catalogId ) {
		return catalogDao.getCatalogById(catalogId);
	}
}