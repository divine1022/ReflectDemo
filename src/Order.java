public class Order {
    private Customer customer;
    private Address address;

    @AutoWired
    public Order(Customer customer, Address address) {
        this.customer = customer;
        this.address = address;
    }

    public Order() {

    }
}
