package model.util;

public enum Specialty {

    ONLINE_SHOPPING("Online shopping"),
    BILLING_AND_PAYMENTS("Billing and payments"),
    ORDERS_AND_SHIPPING("Orders and shipping"),
    RETURNS_AND_REFUNDS("Returns and refunds"),
    USER_ACCOUNTS("User accounts"),
    PRODUCTS_AND_CATALOG("Products and catalog"),
    PROMOTIONS_AND_DISCOUNTS("Promotions and discounts"),
    PAYMENT_INTEGRATION("Payment integration"),
    STOCK_AND_INVENTORY("Stock and inventory"),
    ECOMMERCE_PLATFORM("Ecommerce platform");

    private final String name;

    Specialty(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
