package com.cat.prf.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.logging.Logger;

@SuppressWarnings("serial")
@WebServlet(name = "fileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int n = 0;
        for (Part file : req.getParts()) {
            try (InputStream is = file.getInputStream();
                 FileOutputStream fos = new FileOutputStream("E:\\upload\\" + file.getSubmittedFileName())) {
                byte[] buffer = new byte[1024];
                int r;
                while ((r = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, r);
                }
                n++;
            }
        }
        resp.getWriter().print(n + " files uploaded.");
    }
}
