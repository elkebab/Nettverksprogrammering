package DAO;

import exceptions.BadRequestException;
import exceptions.NotFoundException;
import java.util.List;

/**
 *
 * @author eidheim
 */
public interface Customers {
  
  public List<DTO.Customer> get();

  public long add(DTO.Customer newCustomer) throws BadRequestException;

  public void delete(long id) throws NotFoundException;
}
