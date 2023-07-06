package com.example.crud3.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PictureService {
    void getPicture(HttpServletRequest request, HttpServletResponse response,String p) throws IOException;
    String toBase64(String filePath);

}
