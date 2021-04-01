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
import model.DataObject;
import model.ProductDetailWithPrice;
import model.ProductWithOffering;
import model.ProductOffering;
import model.ProductOfferingPrice;
import model.ProductSpecCharValue;
import model.ProductSpecCharValueRef;
import model.ProductSpecCharacteristic;
import model.ProductSpecCharacteristicRef;
import model.ProductSpecification;
import model.ProductSpecificationRef;
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

	public Response getProduct(String productSpecification_Id) {

		// instance initialization
		ProductWithOffering productdetails = new ProductWithOffering();

//		First fetch product details then fetch its all varients
		System.out.println("Requesting...\n\n");

		System.out.println("Fetching Product Detail Of Product Id: " + productSpecification_Id);

		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecification").path(productSpecification_Id);
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
			productdetails.setProductSpecification(productSpecifications.get(0));
			int numberOfOfferings = productdetails.getProductSpecification().getProductOfferings().size();
			System.out.println("number of varients product has: " + numberOfOfferings + "\n");
		}

		String productOfferingId = null;

		List<ProductOffering> allOfferings = new ArrayList<>();
		ProductOffering productOfferingObject = null;
		System.out.println("---------------------------------------");
		for (int i = 0; i < productdetails.getProductSpecification().getProductOfferings().size(); i++) {
			productOfferingId = productdetails.getProductSpecification().getProductOfferings().get(i).getPOID();

			// Fetch offering of the fetched product
			WebTarget productOfferingTarget = client.target(
					"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
					.path("/productOffering").path(productOfferingId);
			Invocation.Builder productOfferingBuilder = productOfferingTarget.request(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
			Response productOfferingResponse = productOfferingBuilder.get();
			List<ProductOffering> productOfferings = productOfferingResponse
					.readEntity(new GenericType<List<ProductOffering>>() {
					});
			if (productOfferings.size() == 0) {
				System.out.println("Unable to fetch varient of Id: " + productOfferingId);
			} else {
				System.out.println(
						"Successfully fetched varient number " + (i + 1) + " of varient Id: " + productOfferingId);
				productOfferingObject = new ProductOffering();
				productOfferingObject = productOfferings.get(0);
				allOfferings.add(productOfferingObject);
			}
		}
		System.out.println("---------------------------------------");

		productdetails.setProductOffering(allOfferings);
		System.out.println("Successfully Fetched Product and Its details");
		System.out.println("\n\t\tRequest End");
		System.out.println("*************************************************");

		return Response.status(Response.Status.OK).entity(productdetails).build();
	}

	public Response createProductWithOrWithoutVarients(DataObject dataObject) {

		/// Creating Instances of Classes
		ProductSpecification productSpecification = new ProductSpecification();
		ProductOffering productOffering = new ProductOffering();
		ProductSpecificationRef productSpecificationRef = new ProductSpecificationRef();

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
		if (dataObject.getName() == null || dataObject.getDescription() == null
				|| dataObject.getOfferingStartDate() == null || dataObject.getOfferingEndDate() == null) {
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

		if (dataObject.getNumber() != null)
			productSpecification.setProductNumber(dataObject.getNumber());

		if (dataObject.getBarCodes() != null)
			productSpecification.setAvailableBarcodes(dataObject.getBarCodes());

		if (dataObject.getOfferingName() != null)
			productOffering.setName(dataObject.getOfferingName());

		if (dataObject.getOfferingDescription() != null)
			productOffering.setDescription(dataObject.getOfferingDescription());

		productOffering.setValidFor(new TimePeriod());

		if (dataObject.getOfferingStartDate() != null)
			productOffering.getValidFor().setStartDate(dataObject.getOfferingStartDate());

		if (dataObject.getOfferingEndDate() != null)
			productOffering.getValidFor().setEndDate(dataObject.getOfferingEndDate());

		productOffering.setProductSpecifications(new ArrayList<ProductSpecificationRef>());

		if (productSpecification.getName() != null)
			productSpecificationRef.setName(productSpecification.getName());

		if (productSpecification.getDescription() != null)
			productSpecificationRef.setDescription(productSpecification.getDescription());

		if (productSpecification.getProductNumber() != null)
			productSpecificationRef.setProductSerialNumber(productSpecification.getProductNumber());

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
				if (dataObject.getPriceDescription() != null)
					productOfferingPrice.setDescription(dataObject.getPriceDescription());

				if (dataObject.getPriceName() != null)
					productOfferingPrice.setName(dataObject.getPriceName());

				if (dataObject.getPriceType() != null)
					productOfferingPrice.setPriceType(dataObject.getPriceType());

				if (dataObject.getDutyFreeAmountValue() > 0)
					productOfferingPrice.setDutyFreeAmountValue(dataObject.getDutyFreeAmountValue());

				if (dataObject.getPrice() > 0)
					productOfferingPrice.setTaxIncludedAmountValue(dataObject.getPrice());

				if (dataObject.getTaxRate() > 0)
					productOfferingPrice.setTaxRate(dataObject.getTaxRate());

				if (dataObject.getPercentage() > 0)
					productOfferingPrice.setPercentage(dataObject.getPercentage());

				quantity.setNumber(1);
				quantity.setUnitOfMeasure_Id(defaultUnitOfMeasure.getPOID());
				quantity.setUnitOfMeasureName(defaultUnitOfMeasure.getName());
				productOfferingPrice.setUnitOfMeasure(quantity);
				productOffering.getProductOfferingPrices().add(productOfferingPrice);

				// Check for Product if it has some default Characteristics and value

				if (productSpecification.getProductSpecCharacteristics().size() > 0) {
					List<ProductSpecCharacteristic> productSpecCharacteristicList = productSpecification
							.getProductSpecCharacteristics();
					List<List<String>> superlist = new ArrayList<>();
					for (ProductSpecCharacteristic productSpecCharacteristic : productSpecCharacteristicList) {

						List<String> values = new ArrayList<>();

						for (ProductSpecCharValue productCharValue : productSpecCharacteristic
								.getProductSpecCharValues()) {
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

					List<ProductSpecCharacteristicRef> productSpecCharacteristicRef = null;
					List<ProductSpecCharValueRef> productSpecCharValueRef = null;

					int looprunning = 0;
					for (int combinations = 0; combinations < test.size(); combinations++) {

						System.out.println(test.get(combinations));
						productSpecCharacteristicRef = new ArrayList<>();
						List<String> values = new ArrayList<>();
						values = test.get(combinations);
						for (int val = 0; val < productSpecCharacteristicList.size(); val++) {
							ProductSpecCharacteristicRef productSpecCharacteristicRefObject = new ProductSpecCharacteristicRef();
							ProductSpecCharValueRef productSpecCharValueRefObject = new ProductSpecCharValueRef();
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

	public Response getAllProducts() {

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
			System.out.println("Successfully Fetched all products");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(200).entity(new GenericEntity<List<ProductSpecification>>(productSpecifications) {
			}).build();
		}
	}

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

	public Response searchByName(String name) {
		if (name == null) {
			String message = "Name cannot be empty";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.BAD_REQUEST).entity(message)
					.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET").build();
		}
		System.out.println("Requesting...\n\n");
		System.out.println("Searching... " + "\"" + name + "\"" + " in products");
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
			List<ProductSpecification> productSpecificationsByName = new ArrayList<>();
			for (ProductSpecification productSpecification : productSpecifications) {
				if (name.equals(productSpecification.getName())) {
					System.out.println("Product founded in record " + "\"" + productSpecification.getName() + "\"");
					productSpecificationsByName.add(productSpecification);
				}
			}
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			if (productSpecificationsByName.size() > 0) {
				return Response.status(Response.Status.OK)
						.entity(new GenericEntity<List<ProductSpecification>>(productSpecificationsByName) {
						}).build();
			} else {
				String message = "Product not found in record: " + "\" " + name + " \"" + "\n Search again";
				return Response.status(Response.Status.NOT_FOUND).entity(message)

						.build();
			}
		}
	}

	public Response getAllBarCodes(String productId) {
		System.out.println("Requesting...\n\n");
		System.out.println("Searching... " + "\"" + productId + " \"" + "barcodes in products");
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

	public Response SearchBarCode(String barcodeCode) {
		System.out.println("Requesting...\n\n");
		System.out.println("Searching... barcode " + "\"" + barcodeCode + "\"" + " in products");
		ProductSpecification product = new ProductSpecification();
		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecifications");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<ProductSpecification> products = response.readEntity(new GenericType<List<ProductSpecification>>() {
		});
		if (products == null) {
			String message = "Unable to fetch all products";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
		} else if (products.size() == 0) {
			String message = "No products in Product Catalog";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.ACCEPTED).entity(message).build();
		} else {
			for (ProductSpecification productSpecification : products) {
				for (BarCode barcode : productSpecification.getAvailableBarcodes()) {
					if (barcodeCode.equals(barcode.getCode())) {
						System.out.println("barcode matched product found");
						System.out.println("Successfully search product form barcode");
						System.out.println("\n\t\tRequest End");
						System.out.println("*************************************************");
						product = productSpecification;
						return Response.status(Response.Status.OK).entity(product).build();
					}
				}
			}
			String message = "Barcode doesn't exist";
			return Response.status(Response.Status.NOT_FOUND).entity(message).build();
		}
	}

	public Response allproducts() {
		System.out.println("Requesting...\n\n");
		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecifications");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		System.out.println(response.toString());
		List<ProductSpecification> productSpecifications = null;
		productSpecifications = response.readEntity(new GenericType<List<ProductSpecification>>() {
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
			List<ProductDetailWithPrice> products = new ArrayList<>();
			ProductDetailWithPrice product = null;
			for (ProductSpecification productSpecification : productSpecifications) {
				product = new ProductDetailWithPrice();
				product.setProductId(productSpecification.getPOID());
				product.setName(productSpecification.getName());
				product.setDescription(productSpecification.getDescription());
				product.setUnitsOfmeasure(productSpecification.getUnitsOfMeasure());
				products.add(product);
			}
			System.out.println("Successfully Fetched all products");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(200).entity(new GenericEntity<List<ProductDetailWithPrice>>(products) {
			}).build();
		}
	}

	public Response allproductsWithPrice() {
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
			List<ProductDetailWithPrice> product = new ArrayList<>();
			ProductDetailWithPrice products = null;
			String offeringId;
			List<ProductOfferingPrice> productOfferingPrice = new ArrayList<>();
			for (ProductSpecification productSpecification : productSpecifications) {
				products = new ProductDetailWithPrice();
				products.setName(productSpecification.getName());
				products.setDescription(productSpecification.getDescription());
				products.setProductId(productSpecification.getPOID());
				products.setUnitsOfmeasure(productSpecification.getUnitsOfMeasure());

				offeringId = productSpecification.getProductOfferings().get(0).getPOID();
				System.out.println(
						"offering id for price:\t" + productSpecification.getProductOfferings().get(0).getPOID());
				WebTarget webTargetforPrice = client.target(
						"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService/productOfferingPrice?productOffering_Id="
								+ offeringId);
				Invocation.Builder invocationBuilderforPrice = webTargetforPrice.request(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
				Response responseForPrice = invocationBuilderforPrice.get();
				productOfferingPrice = responseForPrice.readEntity(new GenericType<List<ProductOfferingPrice>>() {
				});
				System.out.println("price\t" + productOfferingPrice.get(0).getTaxIncludedAmountValue());
				products.setPrice(productOfferingPrice.get(0).getTaxIncludedAmountValue());
				product.add(products);
			}
			System.out.println("Successfully Fetched all products");
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");

			return Response.status(200).entity(new GenericEntity<List<ProductDetailWithPrice>>(product) {
			}).build();
		}

	}

	public Response updateProduct(DataObject dataObject, String productId) {
		ProductSpecification productSpecification = new ProductSpecification();
		System.out.println("Requesting...\n\n");
		if (dataObject.getName() != null || dataObject.getDescription() != null) {
			System.out.println("Updating name and description ...\n");
			if (dataObject.getName() != null)
				productSpecification.setName(dataObject.getName());

			if (dataObject.getDescription() != null)
				productSpecification.setDescription(dataObject.getDescription());

			WebTarget webTargetUpdateProduct = client.target(
					"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService/productSpecification")
					.path(productId);
			Invocation.Builder invocationBuilder = webTargetUpdateProduct.request(MediaType.APPLICATION_JSON)
					.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
			invocationBuilder.put(Entity.entity(productSpecification, MediaType.APPLICATION_JSON));

		}
		if (dataObject.getPrice() > 0) {
			System.out.println("Updating Price ...\n");
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
				productOfferingPrice.setTaxIncludedAmountValue(dataObject.getPrice());
				productOfferingPrices.add(productOfferingPrice);
				productOffering.setProductOfferingPrices(productOfferingPrices);

				WebTarget webTargetUpdatePrice = client.target(
						"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
						.path("productOffering").path(productOfferingPOID);
				Invocation.Builder invocationBuilderUpdatePrice = webTargetUpdatePrice
						.request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer usman");
				invocationBuilderUpdatePrice.put(Entity.entity(productOffering, MediaType.APPLICATION_JSON));
			}
		}
		String message = "Successfully updated product";
		return Response.status(200).entity(message).build();
	}

	public Response searchById(String productSpecification_Id) {
		System.out.println("Requesting...\n\n");

		System.out.println("Fetching Product Detail Of Product Id: " + productSpecification_Id);

		WebTarget webTarget = client.target(
				"http://localhost:8083/Apps/PMS/HULM/7b64206f-1435-438a-8b1c-42aee9d0cec3/ProductCatalogService")
				.path("/productSpecification").path(productSpecification_Id);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer usman");
		Response response = invocationBuilder.get();
		List<ProductSpecification> productSpecifications = response
				.readEntity(new GenericType<List<ProductSpecification>>() {
				});
		if (productSpecifications == null) {
			String message = "Unable to fetch product";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
		} else if (productSpecifications.size() == 0) {
			String message = "No product found";
			System.out.println("\n\t\tRequest End");
			System.out.println("*************************************************");
			return Response.status(Response.Status.NOT_FOUND).entity(message).build();
		} else {
			ProductSpecification product = new ProductSpecification();
			product = productSpecifications.get(0);
			return Response.status(Response.Status.OK).entity(product).build();
		}
	}
}
