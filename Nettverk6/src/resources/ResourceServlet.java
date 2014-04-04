package resources;

import exceptions.BadRequestException;
import exceptions.ForbiddenException;
import exceptions.NotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author eidheim
 */
@WebServlet(urlPatterns = {"/resources/*"})
public class ResourceServlet extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    if (request.getPathInfo().matches("^/customers.*")) {
      //inject the Customers singleton:
      DAO.Customers daoCustomers = Config.getDaoCustomers();
      //inject the User singleton:
      DAO.User daoUser = Config.getDaoUser();

      String auth = request.getHeader("Authorization");

      String method = request.getMethod();
      if (method.equals("GET")) {
        JsonArrayBuilder jsonBuilder = Json.createArrayBuilder();
        for (DTO.Customer customer : daoCustomers.get()) {
          jsonBuilder.add(Json.createObjectBuilder()
                  .add("id", customer.getId())
                  .add("name", customer.getName())
                  .add("city", customer.getCity())
          );
        }

        out.println(jsonBuilder.build().toString());

      } else if (method.equals("POST")) {
        try {
          if (!daoUser.getRole(auth).equals("write")) {
            throw new ForbiddenException();
          }
          JsonReader reader = Json.createReader(request.getReader());
          JsonObject jsonObject = reader.readObject();

          DTO.Customer newCustomer = new DTO.Customer();
          newCustomer.setId(0);
          newCustomer.setName(jsonObject.getString("name"));
          newCustomer.setCity(jsonObject.getString("city"));

          try {
            long id = daoCustomers.add(newCustomer);
            out.print(id);
          } catch (BadRequestException exception) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
          }
        } catch (ForbiddenException e) {
          response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
      } else if (method.equals("DELETE")) {
        try {
          if (!daoUser.getRole(auth).equals("write")) {
            throw new ForbiddenException();
          }
          Pattern customersIdPattern = Pattern.compile("^/customers/(\\d+)$");
          Matcher matcher = customersIdPattern.matcher(request.getPathInfo());
          if (matcher.find()) {
            long id = Long.parseLong(matcher.group(1));
            try {
              daoCustomers.delete(id);
            } catch (NotFoundException exception) {
              response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
          }
        } catch (ForbiddenException e) {
          response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
      }
    }
    if (request.getPathInfo().matches("^/login$")) {
      //inject the User singleton:
      DAO.User daoUser = Config.getDaoUser();

      String auth = request.getHeader("Authorization");

      String method = request.getMethod();
      if (method.equals("GET")) {
        try {
          out.print(daoUser.getRole(auth));
        } catch (ForbiddenException e) {
          response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
      }
    }
  }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
