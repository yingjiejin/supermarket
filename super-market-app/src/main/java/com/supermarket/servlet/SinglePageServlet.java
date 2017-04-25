package com.supermarket.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SinglePageServlet extends HttpServlet {
    Logger logger = Logger.getLogger(getClass());
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath() + (req.getPathInfo() == null ? "" : req.getPathInfo());
        logger.debug(String.format("拦截到请求%s, 转发到首页", url));
        req.getRequestDispatcher("/index.html").forward(req, resp);
    }

}
