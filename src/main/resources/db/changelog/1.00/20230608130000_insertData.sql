-- liquibase formatted sql
-- changeset msuzko:insert_prices

INSERT INTO prices (ticker, price)
VALUES  ('MSFT', 332.68),
        ('AA',34.55),
        ('MU',67.54),
        ('AAPL',179.21),
        ('AMZN',126.61);

--rollback DELETE FROM prices;
