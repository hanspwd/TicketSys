package model.enums;

public enum Specialty {

    ONLINE_SHOPPING(1, "Online shopping"),
    BILLING_AND_PAYMENTS(2,"Billing and payments"),
    ORDERS_AND_SHIPPING(3,"Orders and shipping"),
    RETURNS_AND_REFUNDS(4,"Returns and refunds"),
    USER_ACCOUNTS(5,"User accounts"),
    PRODUCTS_AND_CATALOG(6,"Products and catalog"),
    PROMOTIONS_AND_DISCOUNTS(7,"Promotions and discounts"),
    PAYMENT_INTEGRATION(8,"Payment integration"),
    STOCK_AND_INVENTORY(9,"Stock and inventory"),
    ECOMMERCE_PLATFORM(10,"Ecommerce platform");

    private final int specialityId;
    private final String name;

    Specialty(int specialityId, String name) {
        this.name = name;
        this.specialityId = specialityId;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public String getName() {
        return name;
    }

}
