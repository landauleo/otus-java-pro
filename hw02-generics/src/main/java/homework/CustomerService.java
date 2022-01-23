package homework;


import java.util.*;

public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> copiedMap = map.firstEntry();
        return new AbstractMap.SimpleImmutableEntry<>(new Customer(copiedMap.getKey()), copiedMap.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Customer nextCustomer = map.higherKey(customer);
        return nextCustomer == null ? null : new AbstractMap.SimpleImmutableEntry<>(new Customer(nextCustomer), map.get(nextCustomer));
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
