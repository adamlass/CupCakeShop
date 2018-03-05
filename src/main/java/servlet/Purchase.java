/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.Order_dao;
import dao.user_dao;
import entities.ShoppingCart;
import entities.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activity.InvalidActivityException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author adamlass
 */
@WebServlet(name = "Purchase", urlPatterns = {"/Purchase"})
public class Purchase extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        //TODO turn trycatch back on

        try {
            user_dao dao = new user_dao();

            //new user
            User user = dao.findUser(((User) request.getSession().getAttribute("user")).getName());
            ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("ShoppingCart");

            if (user.getBalance() < shoppingCart.getPrice()) {
                throw new InvalidActivityException();
            }

            dao.addFunds(user, -shoppingCart.getPrice());
//            
            Order_dao odao = new Order_dao();
            odao.makeOrder(user, shoppingCart);

            MakeAdmin.updateUser(request);
            request.getSession().setAttribute("ShoppingCart", new ShoppingCart());

        } catch (InvalidActivityException i) {
            response.sendRedirect("configurator.jsp?purchase=nef");
            return;
        } catch (Exception e) {
            response.sendRedirect("configurator.jsp?purchase=falsepurchase");
            return;
        }
        response.sendRedirect("configurator.jsp?purchase=true");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
