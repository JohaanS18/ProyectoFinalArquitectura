/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.server.dao;
import com.mycompany.server.model.FileMetadata;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
/**
 *
 * @author Johan Sebastian
 */
public interface IFileDAO {
    Map<String, Object> saveFile(String metadata, File file) throws IOException, NoSuchAlgorithmException;
}