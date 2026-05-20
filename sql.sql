CREATE TABLE donations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50),
    mobile VARCHAR(15),
    donation DOUBLE,
    payment_method VARCHAR(20),
    payment_status VARCHAR(20)
);