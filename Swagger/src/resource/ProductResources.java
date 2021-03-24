package resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.ProductDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import model.BarCode;
import model.DataObject;
import model.ProductWithOffering;
import model.ProductSpecification;
import model.UnitsOfMeasure;

@Path("/Products")
@Api(value = "Products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResources {

	ProductDao productDao = new ProductDao();

	@POST
	@ApiOperation(value = "Create Product", notes = "Create product along with its varient if there are any varients", response = ProductSpecification.class)
	public ProductSpecification NewProduct(DataObject dataObject) {
		return productDao.createProductWithOrWithoutVarients(dataObject);
	}

	@GET
	@Path("/{productId}")
	@ApiOperation(value = "Product and its varients details", notes = "Return product detais along with its all varient's detail", response = ProductWithOffering.class)
	public ProductWithOffering GetProductById(@ApiParam(value = "product id", required = true) @PathParam("productId") String productId) {
		return productDao.getProduct(productId);
	}

	@GET
	@Path("/{productId}/unitOfMeasures")
	@ApiOperation(value = "Product all units of Measures", notes = "Return product all units of Measures", response = UnitsOfMeasure.class, responseContainer = "List")
	public List<UnitsOfMeasure> GetAllUnitOfMeasures(@PathParam("productId") String productId) {
		return productDao.getUnitOfMeasuresOfProduct(productId);
	}

	@GET
	@Path("/{productId}/unitOfMeasure/default")
	@ApiOperation(value = "Default unit of measure of product", notes = "if product has any default unit of measure", response = UnitsOfMeasure.class)
	public UnitsOfMeasure GetDefaultUnitOfMeasure(@PathParam("productId") String productId) {
		return productDao.getDefaultUnitOfMeasure(productId);
	}

	@GET
	@Path("/name/{name}")
	@ApiOperation(value = "Search by product name", notes = "Search products by its name", response = ProductSpecification.class, responseContainer = "List")
	public List<ProductSpecification> SearchByName(@PathParam("name") String name) {
		return productDao.searchByName(name);
	}

	@GET
	@Path("/{productId}/barCodes")
	@ApiOperation(value = "all available barcodes of product", notes = "list all barcodes of products", response = BarCode.class, responseContainer = "List")
	public List<BarCode> GetAllBarCodes(@PathParam("productId") String productId) {
		return productDao.getAllBarCodes(productId);
	}


	@GET
	@Path("/barcode/{barcode}")
	@ApiOperation(value="Search by barcode",
	notes="search product by barcode",
	response=BarCode.class)
	public ProductSpecification SearchBarCode(@PathParam("barcode") String searchBarCode) {
		return productDao.SearchBarCode(searchBarCode);
	}
}
