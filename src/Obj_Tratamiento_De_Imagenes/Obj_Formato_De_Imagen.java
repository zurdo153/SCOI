package Obj_Tratamiento_De_Imagenes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Obj_Formato_De_Imagen {

	public String FileToBase64(String ruta){
		
		Path path = Paths.get(ruta);
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ByteArrayAbase64(data);
	}
	
	public static String ByteArrayAbase64(byte[] image) {
		 
        BASE64Encoder encoder = new BASE64Encoder();
        String imageString = encoder.encode(image);
        return imageString;
    }
	
	public static byte[] base64AByteArray(String imageString) {
        byte[] imageByte = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageByte;
    }
	
	public byte[] FileToByte(String ruta){
		
		Path path = Paths.get(ruta);
		byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
