package com.kce.book.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import com.kce.book.bean.BookBean;

@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        BookBean book = (BookBean) session.getAttribute("book");

        out.println("<html><body>");

        if (book == null) {
            out.println("<h3>No Book Found</h3>");
        } else {
            out.println("<h2>Book Details</h2>");
            out.println("Book Name : " + book.getBookName() + "<br>");
            out.println("ISBN : " + book.getIsbn() + "<br>");
            out.println("Book Type : " + book.getBookType() + "<br>");
            out.println("Cost : " + book.getCost() + "<br>");
            out.println("Author : " +
                    book.getAuthor().getAuthorName() + "<br>");
            out.println("Contact : " +
                    book.getAuthor().getContactNo() + "<br>");
        }

        out.println("<a href='Menu.html'>Back to Menu</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}