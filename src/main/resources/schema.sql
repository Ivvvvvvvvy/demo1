DROP TABLE IF EXISTS EXCHANGE_RATE;

CREATE TABLE EXCHANGE_RATE (
    currency VARCHAR(250) PRIMARY KEY NOT NULL,
    currency_in_chinese VARCHAR(250) NOT NULL,
    rate VARCHAR(250) NOT NULL
);

INSERT INTO EXCHANGE_RATE(currency, currency_in_chinese, rate) VALUES
('USD', '美金', '28.645'),
('HKD', '港幣', '3.546'),
('GBP', '英鎊', '35.45'),
('AUD', '澳幣', '20.49'),
('CAD', '加拿大幣', '22.5');