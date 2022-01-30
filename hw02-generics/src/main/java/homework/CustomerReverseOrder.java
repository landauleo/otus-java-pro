package homework;


import java.util.*;

public class CustomerReverseOrder {

    private final Deque<Customer> customerDeque = new ArrayDeque<>();


    public void add(Customer customer) {
        customerDeque.push(customer);
    }

    public Customer take() {
        return customerDeque.pop();
    }
}
