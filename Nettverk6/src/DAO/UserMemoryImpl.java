package DAO;

import exceptions.ForbiddenException;
import java.util.HashMap;
import java.util.Map;
//import javax.ejb.Singleton;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

/**
 *
 * @author eidheim
 */
class UserDetails {

  private String password;
  private String role;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}

public class UserMemoryImpl implements User {

  //Make singleton with lazy initialisation
  //instance is initialised only if or when injected
  private static User instance = null;
  public static synchronized User getInstance() {
    if (instance == null) {
      instance = new UserMemoryImpl();
    }
    return instance;
  }

  private final Map<String, UserDetails> users = new HashMap<String, UserDetails>();

  private UserMemoryImpl() {
    UserDetails userDetails;

    userDetails = new UserDetails();
    userDetails.setPassword("123");
    userDetails.setRole("write");
    users.put("writeUser", userDetails);

    userDetails = new UserDetails();
    userDetails.setPassword("321");
    userDetails.setRole("read");
    users.put("readUser", userDetails);
  }

  @Override
  public String getRole(String auth) throws ForbiddenException {
    if (auth == null) {
      throw new ForbiddenException();
    }
    String[] authParts = auth.split(" ");

    if (authParts.length == 2) {
      String decoded = new String(parseBase64Binary(authParts[1]));
      String[] decodedParts = decoded.split(":");
      if (decodedParts.length == 2) {
        String username = decodedParts[0];
        String password = decodedParts[1];
        if (!users.containsKey(username)) {
          throw new ForbiddenException();
        }
        UserDetails userDetails = users.get(username);
        if (userDetails.getPassword().equals(password)) {
          return userDetails.getRole();
        }
      }
    }
    throw new ForbiddenException();
  }
}
