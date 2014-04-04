package DAO;

import exceptions.BadRequestException;
import exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eidheim
 */
public class CustomersMemoryImpl implements Customers {

  //Make singleton with lazy initialisation
  //instance is initialised only if or when injected
  private static Customers instance = null;
  public static synchronized Customers getInstance() {
    if (instance == null) {
      instance = new CustomersMemoryImpl();
    }
    return instance;
  }

  private final List<DTO.Customer> customers;

  private CustomersMemoryImpl() {
    customers = new ArrayList<DTO.Customer>();
    DTO.Customer customer;

    customer = new DTO.Customer();
    customer.setId(1);
    customer.setName("Victor Bryan");
    customer.setCity("Seattle");
    customers.add(customer);

    customer = new DTO.Customer();
    customer.setId(2);
    customer.setName("Lee Carroll");
    customer.setCity("Phoenix");
    customers.add(customer);

    customer = new DTO.Customer();
    customer.setId(3);
    customer.setName("Charles Sutton");
    customer.setCity("Quebec");
    customers.add(customer);

    customer = new DTO.Customer();
    customer.setId(4);
    customer.setName("Albert Einstein");
    customer.setCity("New York City");
    customers.add(customer);

    customer = new DTO.Customer();
    customer.setId(5);
    customer.setName("Sonya Williams");
    customer.setCity("Los Angeles");
    customers.add(customer);

    customer = new DTO.Customer();
    customer.setId(6);
    customer.setName("Jesse Hawkins");
    customer.setCity("Atlanta");
    customers.add(customer);

    customer = new DTO.Customer();
    customer.setId(7);
    customer.setName("Lynette Gonzalez");
    customer.setCity("Albuquerque");
    customers.add(customer);

    customer = new DTO.Customer();
    customer.setId(8);
    customer.setName("Erick Pittman");
    customer.setCity("Chicago");
    customers.add(customer);

    customer = new DTO.Customer();
    customer.setId(9);
    customer.setName("Alice Price");
    customer.setCity("Cleveland");
    customers.add(customer);

    customer = new DTO.Customer();
    customer.setId(10);
    customer.setName("Gerard Tucker");
    customer.setCity("Buffalo");
    customers.add(customer);

    customer = new DTO.Customer();
    customer.setId(11);
    customer.setName("Shanika Passmore");
    customer.setCity("Orlando");
    customers.add(customer);
  }

  @Override
  public List<DTO.Customer> get() {
    return customers;
  }

  @Override
  public long add(DTO.Customer newCustomer) throws BadRequestException {
    if (newCustomer.getName().equals("") || newCustomer.getCity().equals("")) {
      throw new BadRequestException();
    }
    long newId = 0;
    synchronized (customers) {
      for (DTO.Customer customer : customers) {
        if (customer.getId() > newId) {
          newId = customer.getId();
        }
      }
      newId++;
      newCustomer.setId(newId);
      customers.add(newCustomer);
    }
    return newId;
  }

  @Override
  public void delete(long id) throws NotFoundException {
    synchronized (customers) {
      for (int c = 0; c < customers.size(); c++) {
        if (customers.get(c).getId() == id) {
          customers.remove(c);
          return;
        }
      }
    }
    throw new NotFoundException();
  }
}
