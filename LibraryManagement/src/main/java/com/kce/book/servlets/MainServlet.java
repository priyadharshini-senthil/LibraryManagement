package com.kce.book.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.kce.book.bean.BookBean;
import com.kce.book.bean.AuthorBean;
import com.kce.book.dao.AuthorDao;
import com.kce.book.service.Administrator;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("Menu.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");

        if ("AddBook".equals(operation)) {

            String result = addBook(request);

            if ("SUCCESS".equals(result)) {
                response.sendRedirect("Menu.html");
            } else {
                response.sendRedirect("Invalid.html");
            }

        } else if ("Search".equals(operation)) {

            String isbn = request.getParameter("isbn");
            BookBean book = new Administrator().viewBook(isbn);

            if (book == null) {
                response.sendRedirect("Invalid.html");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("book", book);

                RequestDispatcher rd =
                        request.getRequestDispatcher("ViewServlet");
                rd.forward(request, response);
            }
        }
    }

    // 🔧 FIXED Add Book logic
    private String addBook(HttpServletRequest request) {

        try {
            String isbn = request.getParameter("isbn");
            String bookName = request.getParameter("bookName");
            String bookTypeStr = request.getParameter("bookType");
            String authorName = request.getParameter("authorName");
            String costStr = request.getParameter("cost");

            // basic validation
            if (isbn == null || bookName == null || authorName == null
                    || costStr == null) {
                return "INVALID";
            }

            float cost = Float.parseFloat(costStr);

            // ✅ get author safely
            AuthorDao authorDAO = new AuthorDao();
            AuthorBean author = authorDAO.getAuthor(authorName);

            if (author == null) {
                System.out.println("Author not found: " + authorName);
                return "INVALID";
            }

            // ✅ book type as CHAR(1)
            char bookType = bookTypeStr.charAt(0); // G, R, etc.

            BookBean book = new BookBean();
            book.setIsbn(isbn);
            book.setBookName(bookName);
            book.setBookType(bookType);
            book.setCost(cost);
            book.setAuthor(author);

            return new Administrator().addBook(book);

        } catch (Exception e) {
            e.printStackTrace();
            return "INVALID";
        }
    }
}