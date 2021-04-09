package resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.ProductDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import model.Product;

@Path("/Products")
@Api(value = "Products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResources {

	ProductDao productDao = new ProductDao();

	@POST
	@ApiOperation(value = "Create Product", notes = "Create product along with its varient if there are any varients")
	public Response NewProduct(Product dataObject) {
		return productDao.createProductWithOrWithoutVarients(dataObject);
	}

	@GET
	@ApiOperation(value = "Get all product", notes = "All products in Product Catalog")
	public Response GetAllProducts(@QueryParam("start") int startIndex, @QueryParam("size") int size,
			@QueryParam("status") String status, @QueryParam("role") String role) {
		System.out.println("start: " + startIndex + "size: " + size + " status: " + status);
		return productDao.getAllProducts(startIndex, size, status, role);
	}

	@PUT
	@Path("/{productId}")
	@ApiOperation(value = "Update product", notes = "Update product name,description,price")
	public Response UpdateProduct(@PathParam("productId") String productId, Product dataObject) {
		System.out.println("productId" + productId);
		return productDao.updateProduct(dataObject, productId);
	}

	@GET
	@Path("/{productId}")
	@ApiOperation(value = "Product and its varients details", notes = "Return product detais along with its all varient's detail")
	public Response GetProductById(
			@ApiParam(value = "product id", required = true) @PathParam("productId") String productId) {
		return productDao.getProduct(productId);
	}

	@DELETE
	@Path("/{productId}")
	public Response DeleteProduct(@PathParam("productId") String productId) {
		return productDao.deleteProduct(productId);
	}

	@GET
	@Path("/{productId}/unitOfMeasures")
	@ApiOperation(value = "Product all units of Measures", notes = "Return product all units of Measures", responseContainer = "List")
	public Response GetAllUnitOfMeasures(@PathParam("productId") String productId,
			@QueryParam("default") boolean defaultUnitOfMeasure) {
		System.out.println("default value:" + defaultUnitOfMeasure);
		if (defaultUnitOfMeasure == true) {
			return productDao.getDefaultUnitOfMeasure(productId);
		} else if (defaultUnitOfMeasure == false) {
			return productDao.getUnitOfMeasuresOfProduct(productId);
		} else {
			return Response.status(Response.Status.METHOD_NOT_ALLOWED)
					.entity("value of default is not corrent: " + defaultUnitOfMeasure).build();
		}
	}

	@GET
	@Path("/{productId}/barCodes")
	@ApiOperation(value = "all available barcodes of product", notes = "list all barcodes of products", responseContainer = "List")
	public Response GetAllBarCodes(@PathParam("productId") String productId) {
		return productDao.getAllBarCodes(productId);
	}

	@GET
	@Path("/search")
	public Response Search(@QueryParam("searchby") String searchBy, @QueryParam("value") String value) {
		if (searchBy.equals("name")) {
			return productDao.searchByName(value);
		} else if (searchBy.equals("barcode")) {
			return productDao.searchByBarCode(value);
		} else {
			return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Search not allowed by " + searchBy)
					.build();
		}
	}
}
