package br.com.mytho.role.domain.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
	
	public String save(String base64, ServletContext context) {
		try {
			String imageName = System.currentTimeMillis() + ".png";
			
			String imagem = getHashFrom(base64);
			byte[] array = toByteArray(imagem);
			
			writeFileIn(getPath(imageName, context), array);
			
			return imageName;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	
	private String getPath(String imageName, ServletContext context) {
		String path = System.getenv("OPENSHIFT_DATA_DIR");
		if(path == null)
			path = context.getRealPath("/");
		
		path = path + imageName;
		return path;
	}
	
	private void writeFileIn(String path, byte[] file) throws FileNotFoundException, IOException {
		OutputStream outputStream = new FileOutputStream(path);

		outputStream.write(file);
		outputStream.flush();
		outputStream.close();
	}

	private byte[] toByteArray(String image) throws IOException {
		return Base64.decode(image.getBytes());
	}

	private String getHashFrom(String bytes) {
		return bytes.substring(bytes.indexOf(",") + 1).replace(" ", "+");
	}

}
