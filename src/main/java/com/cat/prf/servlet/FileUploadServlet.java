package com.cat.prf.servlet;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.logging.Logger;

@WebServlet(name = "fileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FileUploadServlet.class.getSimpleName());
    private static final long serialVersionUID = 23186242707046333L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int n = 0;
        for (Part file : req.getParts()) {
            String fileName = new String(file.getSubmittedFileName().getBytes("iso-8859-1"), "UTF-8");
            try (DataInputStream dis = new DataInputStream(file.getInputStream());
                 DataOutputStream dos = new DataOutputStream(new FileOutputStream("E:\\upload\\" + fileName))) {
                byte[] buffer = new byte[1024];
                int r;
                while ((r = dis.read(buffer)) != -1) {
                    dos.write(buffer, 0, r);
                }
                n++;
            }
        }
        resp.getWriter().print(n + " files uploaded.");
    }
}
