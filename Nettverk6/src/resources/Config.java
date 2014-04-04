package resources;

public class Config {
  
  public static DAO.Customers getDaoCustomers() {
    //choose implementation here:
    return DAO.CustomersMemoryImpl.getInstance();
  }
  
  public static DAO.User getDaoUser() {
    //choose implementation here:
    return DAO.UserMemoryImpl.getInstance();
  }
}
