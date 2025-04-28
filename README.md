# Purchase Cart Service

This is a test service for managing a purchase cart.

## Requirements

- Docker

## Build Docker Image

To build the Docker image, run:

```bash
docker build -t purchase-cart-service .
```

If you don't have execution permissions for the scripts in the scripts folder, run:

```bash
chmod +x ./scripts/build.sh
```

```bash
chmod +x ./scripts/test.sh
```

```bash
chmod +x ./scripts/run.sh
```

## Build the project

You can build the project using the following command:

```bash
docker run -v $(pwd):/mnt -p 9090:9090 -w /mnt purchase-cart-service ./scripts/build.sh
```

## Run Tests

To run the tests:

```bash
docker run -v $(pwd):/mnt -p 9090:9090 -w /mnt purchase-cart-service ./scripts/test.sh
```

## Run the Application

To start the application:

```bash
docker run -v $(pwd):/mnt -p 9090:9090 -w /mnt purchase-cart-service ./scripts/run.sh
```

The application will be available at http://localhost:9090/

## Available Endpoints
- POST /api/v1/orders

## Documentation
You can access the Swagger documentation at:
http://localhost:9090/swagger-ui/index.html

## H2 Console
You can access the H2 database console at:
http://localhost:9090/h2-console/

Use the following settings:
- **JDBC URL**: jdbc:h2:mem:purchase-cart-service
- **Username**: admin
- **Password**: (leave blank)

## CURL example

```
curl -X POST http://localhost:9090/api/v1/orders \
-H "Content-Type: application/json" \
-d '{
  "order": {
    "items": [
      {
        "product_id": 1,
        "quantity": 1
      },
      {
        "product_id": 2,
        "quantity": 5
      },
      {
        "product_id": 3,
        "quantity": 1
      }
    ]
  }
}'
```