public class Config {
    @Bean
    public Customer customer() {
        return new Customer("xtc", "xtc@gamil.com");
    }

    @Bean
    public Address address() {
        return new Address("Baker Street", "221B");
    }
}
