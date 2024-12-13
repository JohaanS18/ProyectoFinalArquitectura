/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.service.getmetadata.impl;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import com.mycompany.server.service.getmetadata.interfaces.GetMetadata;

/**
 *
 * @author fabia
 */
public class GetMetadataJPG implements GetMetadata {

    @Override
    public String getMetadata(File archivo) {
        try {
            HashMap<String, Object> metadatos = new HashMap<>();

            // Leer la imagen
            ImageInfo imageInfo = Imaging.getImageInfo(archivo);

            // Obtener metadatos específicos
            // Extraer dimensiones
            metadatos.put("Ancho", imageInfo.getWidth());
            metadatos.put("Alto", imageInfo.getHeight());
            metadatos.put("BitsPerPixel", imageInfo.getBitsPerPixel());
            metadatos.put("FormatDetails", imageInfo.getFormatDetails());
            metadatos.put("FormatName", imageInfo.getFormatName());
            metadatos.put("MimeType", imageInfo.getMimeType());
            metadatos.put("NumberOfImages", imageInfo.getNumberOfImages());
            metadatos.put("PhysicalHeightDpi", imageInfo.getPhysicalHeightDpi());
            metadatos.put("PhysicalHeightInch", imageInfo.getPhysicalHeightInch());
            metadatos.put("PhysicalWidthDpi", imageInfo.getPhysicalWidthDpi());
            metadatos.put("PhysicalWidthInch", imageInfo.getPhysicalWidthInch());
            metadatos.put("rawData", imageInfo);
            Gson g = new Gson();
            return g.toJson(metadatos);
        } catch (IOException ex) {
            Logger.getLogger(GetMetadataJPG.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
