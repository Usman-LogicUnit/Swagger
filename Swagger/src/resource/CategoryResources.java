package resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.CategoryDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Category;

@Path("/Categories")
@Api(value = "Categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResources {

	CategoryDao categoryDao = new CategoryDao();

	@POST
	@ApiOperation(value = "create a category", notes = "create a category under a catalog", response = Category.class)
	public Response AddCategory(Category category) {
		return categoryDao.newCategory(category);
	}

	@GET
	@Path("/{categoryId}")
	@ApiOperation(value = "category details", notes = "category and its all details", response = Category.class, responseContainer = "List")
	public Response getCategory(@PathParam("categoryId") String categoryId) {
		return categoryDao.getCategories(categoryId);
	}

}
