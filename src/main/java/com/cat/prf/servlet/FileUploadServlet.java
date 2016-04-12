package com.cat.prf.servlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.security.Principal;
import java.util.logging.Logger;

@WebServlet(name = "fileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(FileUploadServlet.class.getSimpleName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int n = 0;
        String username = req.getUserPrincipal().getName();
        File uploadFolder = new File(req.getServletContext().getRealPath("") + File.separator
                + "Files" + File.separator + username);

        if(!uploadFolder.isDirectory()) {
            uploadFolder.mkdirs();
        }

        for (Part file : req.getParts()) {
            String fileName = new String(file.getSubmittedFileName().getBytes("iso-8859-1"), "UTF-8");
            try (DataInputStream dis = new DataInputStream(file.getInputStream());
                 DataOutputStream dos = new DataOutputStream(
                         new FileOutputStream(uploadFolder + File.separator + fileName)
                 )) {
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
