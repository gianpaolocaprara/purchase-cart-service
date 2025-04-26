CREATE TABLE products
(
    id   INT PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE,
    vat DOUBLE
);

CREATE TABLE orders
(
    id INT PRIMARY KEY,
    price DOUBLE,
    vat DOUBLE
);

CREATE TABLE order_items
(
    order_id   INT,
    product_id INT,
    price DOUBLE,
    vat DOUBLE,
    quantity   INT,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);
