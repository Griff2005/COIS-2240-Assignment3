package src;

/**
 * The type Customer.
 */
public class Customer {
    /**
     * The Customer id.
     */
    private int customerId;
    /**
     * The Name.
     */
    private String name;

    /**
     * Instantiates a new Customer.
     *
     * @param customerId the customer id
     * @param name       the name
     */
    public Customer(int customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerId() {
    	return customerId;
    }

    /**
     * Gets customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {
    	return name;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "src.Customer ID: " + customerId + " | Name: " + name;
    }
}