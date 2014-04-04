package DAO;

import exceptions.ForbiddenException;

/**
 *
 * @author eidheim
 */
public interface User {

  public String getRole(String auth) throws ForbiddenException;
}
