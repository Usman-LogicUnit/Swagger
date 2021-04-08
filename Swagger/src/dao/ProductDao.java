package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.google.common.collect.Lists;

import model.BarCode;
import model.Product;
import model.ProductOffering;
import model.ProductOfferingPrice;
import model.OptionValue;
import model.OptionValueRef;
import model.Option;
import model.OptionRef;
import model.ProductSpecification;
import model.ProductRef;
import model.Quantity;
import model.TimePeriod;
import model.UnitsOfMeasure;

public class ProductDao {

	public ProductDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	ClientConfig clientConfig = new ClientConfig();
	Client client = ClientBuilder.newClient(clientConfig);

	// completed OK
	public Response getProduct(String productSpecification_Id) {

		// instance initialization
		Product product = new Product();

//		First fetch product details then fetch its all varients
		System.out.println("Requesting...\n\n");

		System.out.println("Fetching Product Detail Of Product Id: " + productSpecification_Id);

		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecification").path(productSpecification_Id);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<ProductSpecification> productSpecification = response
				.readEntity(new GenericType<List<ProductSpecification>>() {
				});
		if (productSpecification == null) {
			String message = "Unable to fetch product";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
		} else if (productSpecification.size() == 0) {
			String message = "No products in Product Catalog";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.ACCEPTED).entity(message).build();
		} else {
			ProductSpecification tempProductSpecification = productSpecification.get(0);
			product.setName(tempProductSpecification.getName());
			product.setDescription(tempProductSpecification.getDescription());
			product.setPOID(tempProductSpecification.getPOID());
			product.setId(tempProductSpecification.getId());
			product.setImageURL(tempProductSpecification.getProductNumber());
			product.setBundle(tempProductSpecification.isBundle());
			product.setUnitsOfMeasure(tempProductSpecification.getUnitsOfMeasure());
			product.setProductSpecCharacteristics(tempProductSpecification.getProductSpecCharacteristics());
			product.setBarCodes(tempProductSpecification.getAvailableBarcodes());
			product.setStatus(tempProductSpecification.getLifeCycleStatus());
			product.setBundledProductSpecifications(tempProductSpecification.getBundledProductSpecifications());

			String productOfferingId = tempProductSpecification.getProductOfferings().get(0).getPOID();

			WebTarget productOfferingTarget = client.target(
					"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
					.path("/productOffering").path(productOfferingId);
			Invocation.Builder productOfferingBuilder = productOfferingTarget.request(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
			Response productOfferingResponse = productOfferingBuilder.get();
			List<ProductOffering> productOfferings = productOfferingResponse
					.readEntity(new GenericType<List<ProductOffering>>() {
					});

			if (productOfferings == null) {
				String message = "Unable to fetch product";
				System.out.println("\n\t\tRequest End");
				System.out.println("*************************************************");
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
			}
			ProductOffering tempProductOffering = productOfferings.get(0);
			product.setCategory_Id(tempProductOffering.getCategory_Id());
			product.setStartDate(tempProductOffering.getValidFor().getStartDate());
			product.setEndDate(tempProductOffering.getValidFor().getEndDate());
			ProductOfferingPrice tempProductOfferingPrice = tempProductOffering.getProductOfferingPrices().get(0);
			product.setPrice(tempProductOfferingPrice.getDutyFreeAmountValue());
			product.setTaxRate(tempProductOfferingPrice.getTaxRate());

		}
		System.out.println("Successfully Fetched");
		System.out.println("\n\t\tRequest End");
		System.out.println("*************************************************");

		return Response.status(Response.Status.OK).entity(product).build();
	}

	public Response createProductWithOrWithoutVarients(Product dataObject) {

		/// Creating Instances of Classes
		ProductSpecification productSpecification = new ProductSpecification();
		ProductOffering productOffering = new ProductOffering();
		ProductRef productSpecificationRef = new ProductRef();

		if (dataObject == null) {
			System.out.println("in here");
		}
		if (dataObject.getPrice() <= 0) {
			String message = "price not provided";
			System.out.println("Error while creating product");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.OK).entity(message).build();
		}
		if (dataObject.getName() == null || dataObject.getDescription() == null || dataObject.getStartDate() == null
				|| dataObject.getEndDate() == null) {
			String message = "please provide necessary details\n provide name,description,offeringStartDate,OfferingEndDate";
			System.out.println("Error while creating product");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.OK).entity(message).build();
		}

		/// Setting values form request Object Body for productSpecification and product
		/// Offering
		ProductOfferingPrice productOfferingPrice = new ProductOfferingPrice();
		if (dataObject.getName() != null)
			productSpecification.setName(dataObject.getName());

		if (dataObject.getDescription() != null)
			productSpecification.setDescription(dataObject.getDescription());

		if (dataObject.getBarCodes() != null)
			productSpecification.setAvailableBarcodes(dataObject.getBarCodes());

		productOffering.setValidFor(new TimePeriod());

		if (dataObject.getStartDate() != null)
			productOffering.getValidFor().setStartDate(dataObject.getStartDate());

		if (dataObject.getEndDate() != null)
			productOffering.getValidFor().setEndDate(dataObject.getEndDate());

		productOffering.setProductSpecifications(new ArrayList<ProductRef>());

		if (productSpecification.getName() != null)
			productSpecificationRef.setName(productSpecification.getName());
		// productSpecificationRef.setConversionFactor(dataObject.getConversionFactor());
		productOffering.getProductSpecifications().add(productSpecificationRef);
		productOffering.setProductOfferingPrices(new ArrayList<ProductOfferingPrice>());

		if (dataObject.getCategory_Id() != null)
			productOffering.setCategory_Id(dataObject.getCategory_Id());

		if (productSpecification.isBundle() == true) {
			productSpecification.setBundledProductSpecifications(dataObject.getBundledProductSpecifications());
			System.out.println("Product is bundle");
		} else {
			System.out.println("Not a bundle Product");
		}

		if (dataObject.getProductSpecCharacteristics() != null) {
			System.out.println("Product have multiple varients");
			productSpecification.setProductSpecCharacteristics(dataObject.getProductSpecCharacteristics());
		} else {
			System.out.println("No Characteristics");
		}

		// Creating default unit Of measure if any of Unit of Measure is not provided
		if (dataObject.getUnitsOfMeasure() == null || dataObject.getUnitsOfMeasure().size() == 0) {
			System.out.println("no unit of Measure provided");
			List<UnitsOfMeasure> defaultUnitOfMeasureList = new ArrayList<>();
			UnitsOfMeasure unitOfMeasure = new UnitsOfMeasure();
			unitOfMeasure.setId("1");
			unitOfMeasure.setConversionFactor(1);
			unitOfMeasure.setName("Nos");
			unitOfMeasure.setDefault(true);
			defaultUnitOfMeasureList.add(unitOfMeasure);
			productSpecification.setUnitsOfMeasure(defaultUnitOfMeasureList);
		} else {
			productSpecification.setUnitsOfMeasure(dataObject.getUnitsOfMeasure());
			System.out.println("Unit of Measure Provided");
		}

		// Create the product from details

		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecification");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.post(Entity.entity(productSpecification, MediaType.APPLICATION_JSON));
		ProductSpecification responseProduct = new ProductSpecification();
		responseProduct = response.readEntity(ProductSpecification.class);
		if (responseProduct == null) {
			String message = "Error while creating product";
			System.out.println("Error while creating product");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.OK).entity(message).build();// .header("Access-Control-Allow-Origin",
																				// "*")
			// .header("Access-Control-Allow-Methods", "GET,POST").build();
		} else {
			productOffering.getProductSpecifications().get(0).setId(responseProduct.getId());
			productOffering.getProductSpecifications().get(0).setProduct_Id(responseProduct.getId());
			// Get the details of Created Product
			WebTarget webTargetProductSpecification = client.target(
					"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
					.path("/productSpecification").path(responseProduct.getId());
			Invocation.Builder invocationBuilderProductSpecification = webTargetProductSpecification
					.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer usman");
			Response responseProductSpecification = invocationBuilderProductSpecification.get();
			List<ProductSpecification> productSpecifications = responseProductSpecification
					.readEntity(new GenericType<List<ProductSpecification>>() {
					});
			if (productSpecifications.size() > 0) {
				productSpecification = productSpecifications.get(0);

				// Get default unit Of Measure
				UnitsOfMeasure defaultUnitOfMeasure = new UnitsOfMeasure();

				for (UnitsOfMeasure unitOfMeasure : productSpecifications.get(0).getUnitsOfMeasure()) {
					if (unitOfMeasure.isDefault() == true) {
						System.out.println("one is default product" + unitOfMeasure.getPOID());
						defaultUnitOfMeasure = unitOfMeasure;
					}
				}
				Quantity quantity = new Quantity();

				// Setting default unit of Measure to Product Offering Prices

				if (dataObject.getPrice() > 0)
					productOfferingPrice.setTaxIncludedAmountValue(dataObject.getPrice());

				if (dataObject.getTaxRate() > 0)
					productOfferingPrice.setTaxRate(dataObject.getTaxRate());

				quantity.setNumber(1);
				quantity.setUnitOfMeasure_Id(defaultUnitOfMeasure.getPOID());
				quantity.setUnitOfMeasureName(defaultUnitOfMeasure.getName());
				productOfferingPrice.setUnitOfMeasure(quantity);
				productOffering.getProductOfferingPrices().add(productOfferingPrice);

				// Check for Product if it has some default Characteristics and value

				if (productSpecification.getProductSpecCharacteristics().size() > 0) {
					List<Option> productSpecCharacteristicList = productSpecification.getProductSpecCharacteristics();
					List<List<String>> superlist = new ArrayList<>();
					for (Option productSpecCharacteristic : productSpecCharacteristicList) {

						List<String> values = new ArrayList<>();

						for (OptionValue productCharValue : productSpecCharacteristic.getProductSpecCharValues()) {
							values.add(productSpecCharacteristic.getName() + ":" + productCharValue.getValue() + "="
									+ productSpecCharacteristic.getPOID() + "-" + productCharValue.getPOID());
						}
						superlist.add(values);
					}
					System.out.println("no of characteristics" + productSpecCharacteristicList.size());

					System.out.println(Lists.cartesianProduct(superlist));

					List<List<String>> test = new ArrayList<>();
					test = Lists.cartesianProduct(superlist);
					System.out.print("Number of Possible Varients" + test.size());

					List<OptionRef> productSpecCharacteristicRef = null;
					List<OptionValueRef> productSpecCharValueRef = null;

					int looprunning = 0;
					for (int combinations = 0; combinations < test.size(); combinations++) {

						System.out.println(test.get(combinations));
						productSpecCharacteristicRef = new ArrayList<>();
						List<String> values = new ArrayList<>();
						values = test.get(combinations);
						for (int val = 0; val < productSpecCharacteristicList.size(); val++) {
							OptionRef productSpecCharacteristicRefObject = new OptionRef();
							OptionValueRef productSpecCharValueRefObject = new OptionValueRef();
							productSpecCharValueRef = new ArrayList<>();

							System.out.println("new values" + values.get(val));
							String string = values.get(val);
							String[] splitIntoCharNameAndValue_POIDs = string.split(":");
							String characteristicName = splitIntoCharNameAndValue_POIDs[0];
							String vae = splitIntoCharNameAndValue_POIDs[1];
							String[] characteristicsValue = vae.split("=");
							String value = characteristicsValue[0];
							String POIDs = characteristicsValue[1];
							String[] characteristicsAndValuePOIDs = POIDs.split("-");
							String CharacteristicPOID = characteristicsAndValuePOIDs[0];
							String ValuePOID = characteristicsAndValuePOIDs[1];
							productSpecCharacteristicRefObject.setName(characteristicName);
							productSpecCharacteristicRefObject.setId(CharacteristicPOID);
							productSpecCharValueRefObject.setValue(value);
							productSpecCharValueRefObject.setId(ValuePOID);
							productSpecCharValueRef.add(productSpecCharValueRefObject);
							productSpecCharacteristicRefObject.setProductSpecCharValues(productSpecCharValueRef);
							productSpecCharacteristicRef.add(productSpecCharacteristicRefObject);
							System.out.println("characteristics and its values:" + characteristicName + "---" + value);
							System.out.println("new " + productSpecCharValueRef.toString());
							System.out.println(
									"reference object characteristics" + productSpecCharacteristicRef.toString());
							System.out.println("loop running number of times" + looprunning++);
						}
						System.out.println("--------------------------------------------");
						System.out.println(productSpecCharacteristicRef.toString());
						productOffering.setProductSpecCharacteristicRefs(productSpecCharacteristicRef);
						WebTarget productOfferingTarget = client.target(
								"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
								.path("/productOffering");
						Invocation.Builder productOfferingBuilder = productOfferingTarget
								.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer usman");
						productOfferingBuilder.post(Entity.entity(productOffering, MediaType.APPLICATION_JSON));
					}

				} else {

					System.out.println("Empty Characteristics");
					System.out.println("product Offering" + productOffering.toString());
					WebTarget productOfferingTarget = client.target(
							"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
							.path("/productOffering");
					Invocation.Builder productOfferingBuilder = productOfferingTarget
							.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer usman");
					Response productOfferingResponse = productOfferingBuilder
							.post(Entity.entity(productOffering, MediaType.APPLICATION_JSON));
					ProductOffering productOfferingObject = new ProductOffering();
					productOfferingObject = productOfferingResponse.readEntity(ProductOffering.class);
					System.out.println("Offering ID" + productOfferingObject.getId());
				}
				System.out.println("Successfully created product and its varients");
				System.out.println("\n\t\tRequest End");
				System.out.println("*************************************************");
				return Response.status(Response.Status.OK).entity(productSpecification).build();
				// .header("Access-Control-Allow-Origin",
				// "*").header("Access-Control-Allow-Methods", "GET,POST")
				// .build();
			} else
				return null;
		}
	}

	// completed OK
	public Response getAllProducts(int startingIndex, int size, String status) {
		List<Product> products = new ArrayList<>();
		Product product;
		System.out.println("Requesting...\n\n");
		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecifications");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<ProductSpecification> productSpecifications = null;
		productSpecifications = response.readEntity(new GenericType<List<ProductSpecification>>() {
		});
		System.out.println("product Specifications: " + productSpecifications.size());
		WebTarget webTargetForAllProductOfferings = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productOfferings");
		Invocation.Builder invocationBuilderForAllOfferings = webTargetForAllProductOfferings
				.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response allOfferingsResponse = invocationBuilderForAllOfferings.get();
		List<ProductOffering> productOfferings = null;
		productOfferings = allOfferingsResponse.readEntity(new GenericType<List<ProductOffering>>() {
		});

		if (productSpecifications == null || productOfferings == null) {
			String message = "Unable to fetch all products";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
		} else if (productSpecifications.size() == 0) {
			String message = "No products in Product Catalog";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.ACCEPTED).entity(message).build();
		} else {
			if (startingIndex > productSpecifications.size()) {
				System.out.println("Please provide valid Starting endpoint");
				System.out.println("\n\t\tRequest End");
				System.out.println("*************************************************");
				return Response.status(Response.Status.BAD_REQUEST).entity("index size out of bound").build();
			}
			List<Product> paginatedList;

			for (ProductSpecification productSpecification : productSpecifications) {
				System.out.println("name:" + productSpecification.getName());
				product = new Product();
				product.setName(productSpecification.getName());
				product.setDescription(productSpecification.getDescription());
				product.setPOID(productSpecification.getPOID());
				product.setId(productSpecification.getId());
				product.setImageURL(productSpecification.getProductNumber());
				product.setBundle(productSpecification.isBundle());
				product.setUnitsOfMeasure(productSpecification.getUnitsOfMeasure());
				product.setProductSpecCharacteristics(productSpecification.getProductSpecCharacteristics());
				product.setBarCodes(productSpecification.getAvailableBarcodes());
				product.setBundledProductSpecifications(productSpecification.getBundledProductSpecifications());
				product.setStatus(productSpecification.getLifeCycleStatus());
				String productOfferingId = productSpecification.getProductOfferings().get(0).getPOID();

				for (ProductOffering productOffering : productOfferings) {
					if (productOfferingId.equals(productOffering.getPOID())) {

						product.setCategory_Id(productOffering.getCategory_Id());
						product.setStartDate(productOffering.getValidFor().getStartDate());
						product.setEndDate(productOffering.getValidFor().getEndDate());
						ProductOfferingPrice tempProductOfferingPrice = new ProductOfferingPrice();
						tempProductOfferingPrice = productOffering.getProductOfferingPrices().get(0);
						product.setPrice(tempProductOfferingPrice.getDutyFreeAmountValue());
						product.setTaxRate(tempProductOfferingPrice.getTaxRate());
						System.out.println("Price: " + tempProductOfferingPrice.getDutyFreeAmountValue());
						products.add(product);
						break;
					}
				}
			}
			if (startingIndex > 0 && size > 0) {
				startingIndex--;
				if (startingIndex + size > productSpecifications.size()) {
					int itemToRemove = (startingIndex + size) - productSpecifications.size();
					size = size - itemToRemove;
				}
				paginatedList = products.subList(startingIndex, size + startingIndex);
				System.out.println("size of list: " + paginatedList.size());
				System.out.println("Successfully Fetched Products");
				System.out.println("\n\t\tRequest End");
				System.out.println("*************************************************");
				return Response.status(200).entity(new GenericEntity<List<Product>>(paginatedList) {
				}).build();
			}
			System.out.println("Number of Products: " + products.size());
			System.out.println("Successfully Fetched all products");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(200).entity(new GenericEntity<List<Product>>(products) {
			}).build();
		}
	}

	// Completed OK
	public Response getUnitOfMeasuresOfProduct(String productId) {
		System.out.println("Requesting...\n\n");
		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecification").path(productId);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<ProductSpecification> productSpecifications = response
				.readEntity(new GenericType<List<ProductSpecification>>() {
				});
		if (productSpecifications == null) {
			String message = "Unable to fetch all products";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
		} else if (productSpecifications.size() == 0) {
			String message = "No products in Product Catalog";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.ACCEPTED).entity(message).build();
		} else {
			List<UnitsOfMeasure> unitOfMeasures = new ArrayList<>();
			unitOfMeasures = productSpecifications.get(0).getUnitsOfMeasure();
			System.out.println("Successfully Fetched all units of measures");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.OK).entity(new GenericEntity<List<UnitsOfMeasure>>(unitOfMeasures) {
			}).build();
		}
	}

	// completed OK
	public Response getDefaultUnitOfMeasure(String productId) {
		System.out.println("Requesting...\n\n");
		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecification").path(productId);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<ProductSpecification> productSpecifications = response
				.readEntity(new GenericType<List<ProductSpecification>>() {
				});
		if (productSpecifications == null) {
			String message = "Unable to fetch all products";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
		} else if (productSpecifications.size() == 0) {
			String message = "No products in Product Catalog";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.ACCEPTED).entity(message).build();
		}

		else {
			UnitsOfMeasure defaultUnitOfMeasure = new UnitsOfMeasure();
			for (UnitsOfMeasure unitOfMeasure : productSpecifications.get(0).getUnitsOfMeasure()) {
				if (unitOfMeasure.isDefault() == true) {
					System.out.println("default unit of measure is: " + unitOfMeasure.getName());
					defaultUnitOfMeasure = unitOfMeasure;
					break;
				}
			}
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.OK).entity(defaultUnitOfMeasure).build();
		}
	}

	// completed OK
	public Response searchByName(String name) {
		System.out.println("Requesting...\n\n");
		System.out.println("Searching... product by name " + "\"" + name + "\"" + " in products");
		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecifications");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<ProductSpecification> productSpecifications = response
				.readEntity(new GenericType<List<ProductSpecification>>() {
				});
		if (productSpecifications == null) {
			String message = "Unable to fetch all products";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
		} else if (productSpecifications.size() == 0) {
			String message = "No products in Product Catalog";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.ACCEPTED).entity(message).build();
		} else {
			List<Product> products = new ArrayList<>();
			Product product = new Product();
			for (ProductSpecification productSpecification : productSpecifications) {
				if (name.equals(productSpecification.getName())) {
					product.setName(productSpecification.getName());
					product.setDescription(productSpecification.getDescription());
					product.setPOID(productSpecification.getPOID());
					product.setId(productSpecification.getId());
					product.setBundle(productSpecification.isBundle());
					product.setImageURL(productSpecification.getProductNumber());
					product.setUnitsOfMeasure(productSpecification.getUnitsOfMeasure());
					product.setProductSpecCharacteristics(productSpecification.getProductSpecCharacteristics());
					product.setBarCodes(productSpecification.getAvailableBarcodes());
					product.setStatus(productSpecification.getLifeCycleStatus());
					product.setBundledProductSpecifications(productSpecification.getBundledProductSpecifications());

					String productOfferingId = productSpecification.getProductOfferings().get(0).getPOID();

					WebTarget productOfferingTarget = client.target(
							"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
							.path("/productOffering").path(productOfferingId);
					Invocation.Builder productOfferingBuilder = productOfferingTarget
							.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer usman");
					Response productOfferingResponse = productOfferingBuilder.get();
					List<ProductOffering> productOfferings = productOfferingResponse
							.readEntity(new GenericType<List<ProductOffering>>() {
							});

					if (productOfferings == null) {
						String message = "Unable to fetch product";
						System.out.println("\n\t\tRequest End");
						System.out.println("*************************************************");
						return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
					}
					ProductOffering tempProductOffering = productOfferings.get(0);
					product.setCategory_Id(tempProductOffering.getCategory_Id());
					product.setStartDate(tempProductOffering.getValidFor().getStartDate());
					product.setEndDate(tempProductOffering.getValidFor().getEndDate());
					ProductOfferingPrice tempProductOfferingPrice = tempProductOffering.getProductOfferingPrices()
							.get(0);
					product.setPrice(tempProductOfferingPrice.getDutyFreeAmountValue());
					product.setTaxRate(tempProductOfferingPrice.getTaxRate());
					products.add(product);
				}
			}
			if (products.size() == 0) {
				String message = "No product exists with name: " + name;
				System.out.println("No product exists for name");
				System.out.println("\n\t\tRequest End");
				System.out.println("*************************************************");
				return Response.status(Response.Status.NOT_FOUND).entity(message).build();
			}
			System.out.println("Successfully search product form name");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.OK).entity(new GenericEntity<List<Product>>(products) {
			}).build();
		}
	}

	// completed OK
	public Response getAllBarCodes(String productId) {
		System.out.println("Requesting...\n\n");
		System.out.println("Searching... " + "\"" + productId + " \"" + "barcodes in product");

		/*
		 * Get product details then check if the product has barcodes then it return the
		 * list of barcodes else it return empty list
		 */

		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecification").path(productId).path("/barCodes");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<BarCode> barCodes = response.readEntity(new GenericType<List<BarCode>>() {
		});
		if (barCodes == null) {
			String message = "Unable to fetch all barcodes";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
		} else if (barCodes.size() == 0) {
			String message = "No barcode for Product exists";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.ACCEPTED).entity(message).build();
		} else
			System.out.println("Successfully retrieve all barcodes of product " + productId);

		System.out.println("\n\t\tRequest End");
		System.out.println("*************************************************");
		return Response.status(Response.Status.OK).entity(new GenericEntity<List<BarCode>>(barCodes) {
		}).build();
	}

	// completed OK
	public Response searchByBarCode(String barcodeCode) {
		System.out.println("Requesting...\n\n");
		System.out.println("Searching... product by barcode: " + "\"" + barcodeCode + "\"");
		Product product = new Product();

		/*
		 * API to search product by bar code
		 * 
		 * Search product by name First fetch all the product then compare products with
		 * name if name matches then add product to list
		 */
		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecifications");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<ProductSpecification> productSpecifications = response
				.readEntity(new GenericType<List<ProductSpecification>>() {
				});
		if (productSpecifications == null) {
			String message = "Unable to fetch all products";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
		} else if (productSpecifications.size() == 0) {
			String message = "No products in Product Catalog";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.ACCEPTED).entity(message).build();
		} else {
			for (ProductSpecification productSpecification : productSpecifications) {
				for (BarCode barcode : productSpecification.getAvailableBarcodes()) {
					if (barcodeCode.equals(barcode.getCode())) {
						System.out.println("barcode matched product found");
						System.out.println("Successfully search product form barcode");
						System.out.println("\n\t\tRequest End");
						System.out.println("*************************************************");
						product.setName(productSpecification.getName());
						product.setDescription(productSpecification.getDescription());
						product.setPOID(productSpecification.getPOID());
						product.setId(productSpecification.getId());
						product.setImageURL(productSpecification.getProductNumber());
						product.setBundle(productSpecification.isBundle());
						product.setUnitsOfMeasure(productSpecification.getUnitsOfMeasure());
						product.setProductSpecCharacteristics(productSpecification.getProductSpecCharacteristics());
						product.setBarCodes(productSpecification.getAvailableBarcodes());
						product.setStatus(productSpecification.getLifeCycleStatus());
						product.setBundledProductSpecifications(productSpecification.getBundledProductSpecifications());

						String productOfferingId = productSpecification.getProductOfferings().get(0).getPOID();

						WebTarget productOfferingTarget = client.target(
								"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
								.path("/productOffering").path(productOfferingId);
						Invocation.Builder productOfferingBuilder = productOfferingTarget
								.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer usman");
						Response productOfferingResponse = productOfferingBuilder.get();
						List<ProductOffering> productOfferings = productOfferingResponse
								.readEntity(new GenericType<List<ProductOffering>>() {
								});

						if (productOfferings == null) {
							String message = "Unable to fetch product";
							System.out.println("\n\t\tRequest End");
							System.out.println("*************************************************");
							return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
						}
						ProductOffering tempProductOffering = productOfferings.get(0);
						product.setCategory_Id(tempProductOffering.getCategory_Id());
						product.setStartDate(tempProductOffering.getValidFor().getStartDate());
						product.setEndDate(tempProductOffering.getValidFor().getEndDate());
						ProductOfferingPrice tempProductOfferingPrice = tempProductOffering.getProductOfferingPrices()
								.get(0);
						product.setPrice(tempProductOfferingPrice.getDutyFreeAmountValue());
						product.setTaxRate(tempProductOfferingPrice.getTaxRate());

						return Response.status(Response.Status.OK).entity(product).build();
					}
				}
			}
			String message = "Barcode doesn't exist";
			return Response.status(Response.Status.NOT_FOUND).entity(message).build();
		}
	}

	// completed OK
	public Response updateProduct(Product product, String productId) {
		System.out.println("product:" + product + "\n\n\n");
		System.out.println("Requesting...\n\n");

		/*
		 * API to update product details Update product details like name description
		 * price etc
		 */

		if (product.getName() != null || product.getDescription() != null || product.getImageURL() != null
				|| product.getUnitsOfMeasure() != null || product.getBarCodes() != null
				|| product.getStatus() != null) {
			updateProductSpecification(product, productId);
		}
		if (product.getPrice() > 0 || product.getTaxRate() > 0) {
			updateProductOffering(product, productId);
		}
		String message = "Successfully updated product";
		return Response.status(200).entity(message).build();
	}

	// completed OK
	public void updateProductSpecification(Product product, String productId) {
		ProductSpecification productSpecification = new ProductSpecification();
		System.out.println("Updating Product Specification");

		if (product.getName() != null)
			productSpecification.setName(product.getName());

		if (product.getDescription() != null)
			productSpecification.setDescription(product.getDescription());

		if (product.getImageURL() != null) {
			String imageURL = product.getImageURL();
			String[] splitOnColon = imageURL.split(":");
			String imageURLwithoutColon = splitOnColon[1];
			System.out.println("yeaaaahhhhh!!! jogarh" + imageURLwithoutColon);
			productSpecification.setProductNumber(imageURLwithoutColon);
		}

		if (product.getUnitsOfMeasure() != null) {
			productSpecification.setUnitsOfMeasure(product.getUnitsOfMeasure());
		}

		if (product.getBarCodes() != null) {
			productSpecification.setAvailableBarcodes(product.getBarCodes());
		}
		if (product.getStatus() != null)
			product.setStatus(productSpecification.getLifeCycleStatus());
		System.out.println("Product Specification: " + productSpecification.toString() + "\n\n\n");
		System.out.println("Updating product Specification: ...\n\n");
		WebTarget webTargetUpdateProduct = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService/productSpecification")
				.path(productId);
		Invocation.Builder invocationBuilder = webTargetUpdateProduct.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		invocationBuilder.put(Entity.entity(productSpecification, MediaType.APPLICATION_JSON));

	}

	// completed OK
	public void updateProductOffering(Product product, String productId) {
		System.out.println("Updating productOffering: ...\n\n");
		WebTarget webTargetforProductOffering = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecification").path(productId).path("productOfferings");
		Invocation.Builder invocationBuilderForOffering = webTargetforProductOffering
				.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response responseForOffering = invocationBuilderForOffering.get();
		List<ProductOffering> productOfferings = responseForOffering
				.readEntity(new GenericType<List<ProductOffering>>() {
				});

		if (productOfferings != null) {
			ProductOffering productOffering = new ProductOffering();
			List<ProductOfferingPrice> productOfferingPrices = new ArrayList<>();
			ProductOfferingPrice productOfferingPrice = new ProductOfferingPrice();

			String productOfferingPOID = productOfferings.get(0).getPOID();
			String productOfferingPricePOID = productOfferings.get(0).getProductOfferingPrices().get(0).getPOID();

			productOfferingPrice.setPOID(productOfferingPricePOID);
			productOfferingPrice.setDutyFreeAmountValue(product.getPrice());
			productOfferingPrice.setTaxRate(product.getTaxRate());
			productOfferingPrices.add(productOfferingPrice);
			productOffering.setProductOfferingPrices(productOfferingPrices);

			System.out.println("Testing " + productOffering.toString());
			WebTarget webTargetUpdatePrice = client.target(
					"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
					.path("productOffering").path(productOfferingPOID);
			Invocation.Builder invocationBuilderUpdatePrice = webTargetUpdatePrice.request(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
			invocationBuilderUpdatePrice.put(Entity.entity(productOffering, MediaType.APPLICATION_JSON));
		}
	}

	// completed OK
	public Response deleteProduct(String productId) {
		ProductSpecification productSpecification = new ProductSpecification();
		productSpecification.setLifeCycleStatus("Archived");
		System.out.println("Product Specification: " + productSpecification.toString() + "\n\n\n");
		System.out.println("Updating product Specification: ...\n\n");
		WebTarget webTargetUpdateProduct = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService/productSpecification")
				.path(productId);
		Invocation.Builder invocationBuilder = webTargetUpdateProduct.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		invocationBuilder.put(Entity.entity(productSpecification, MediaType.APPLICATION_JSON));
		System.out.println("Successfully deleted Product");
		System.out.println("\n\t\tRequest End");
		System.out.println("*************************************************");
		return Response.status(Response.Status.OK).entity("Successfully deleted Product").build();
	}

}
