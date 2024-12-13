/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.service.getmetadata.impl;

import com.google.gson.Gson;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import com.mycompany.server.service.getmetadata.interfaces.GetMetadata;

/**
 *
 * @author fabia
 */
public class GetMetadataText implements GetMetadata {

    @Override
    public String getMetadata(File archivo) {
        HashMap<String, Object> info = new HashMap<>();
        

        if (archivo.exists()) {
            info.put("Nombre", archivo.getName());

            info.put("Ruta", archivo.getAbsolutePath());
            info.put("Tamaño", archivo.length() + " bytes");

            // Obtener la fecha de modificación en un formato legible
            Date fechaModificacion = new Date(archivo.lastModified());
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            info.put("modificacion", formato.format(fechaModificacion));
            Gson g = new Gson();
            return g.toJson(info);
        } else {
            System.out.println("El archivo no existe.");
        }
        return null;
    }

}
