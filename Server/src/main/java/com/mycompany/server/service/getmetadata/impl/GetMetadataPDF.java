/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.service.getmetadata.impl;

import com.google.gson.Gson;
import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import com.mycompany.server.service.getmetadata.interfaces.GetMetadata;

/**
 *
 * @author fabia
 */
public class GetMetadataPDF implements GetMetadata {

    @Override
    public String getMetadata(File archivo) {
        try {
            PdfReader reader = new PdfReader(new FileInputStream(archivo));
            //PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
            HashMap<String, String> info = reader.getInfo();
            HashMap<String, Object> mapObject = new HashMap<>();
            mapObject.putAll(info);
            Gson g = new Gson();
            return g.toJson(mapObject);
        } catch (IOException ex) {

        }
        return null;
    }

}
