package kr.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

//파일 업로드 기능을 수행하기 위해서 cos.jar 파일이 필수적으로
//요구됨
public class FileUtil {
	//업로드 경로
	public static final String UPLOAD_PATH="D:\\javaWork\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Scheduler\\upload";
	// public static final String UPLOAD_PATH="D:\\javaWork\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\Scheduler\\upload";
	// public static final String UPLOAD_PATH="D:\\javaWork\\workspace\\NCS\\Scheduler\\imgUpload";
	// public static final String UPLOAD_PATH="D:\\study\\web_app\\NCS\\Scheduler\\WebContent\\upload";
	//인코딩 타입
	public static final String ENCODING_TYPE="utf-8";
	//최대 업로드 사이즈
	public static final int MAX_SIZE=10*1024*1024;
	
	//파일 업로드 경로에 생성
	public static MultipartRequest createFile(
			             HttpServletRequest request)
                             throws IOException{
		return new MultipartRequest(request,
				                    UPLOAD_PATH,
				                    MAX_SIZE,
				                    ENCODING_TYPE,
				        new DefaultFileRenamePolicy());
	}
	//파일 삭제
	public static void removeFile(String filename) {
		if(filename != null) {
			File file = new File(UPLOAD_PATH,filename);
			if(file.exists()) file.delete();
		}
	}
	
	// 제공된 파일의  썸네일 생성
	public static String createThumbnail(String uploadedFile,int thumbnailWidth, int thumbnailHeight){
		return createThumbnail(uploadedFile, null, thumbnailWidth, thumbnailHeight);
	}
	
	public static String createThumbnail(String uploadedFile,String thumbnailFile,int thumbnailWidth, int thumbnailHeight){
		if(thumbnailFile==null){
			int index = uploadedFile.lastIndexOf(".");
			if(index !=-1){//썸네일의 확장자는 jpg로 변경
				thumbnailFile = "s" + uploadedFile.substring(0,index) + ".jpg";
			}
		}else{
			int index = thumbnailFile.lastIndexOf(".");
			if(index !=-1){//썸네일의 확장자는 jpg로 변경
				thumbnailFile = thumbnailFile.substring(0,index) + ".jpg";
			}
		}
		
		FileInputStream fs = null; 
		try { 
			fs = new FileInputStream(UPLOAD_PATH+"/"+uploadedFile);
			BufferedImage im = ImageIO.read(fs);

			int width;
			int height;

			if(thumbnailHeight == 0){//높이를 0으로 지정했을 경우 넓이를 

				int radio = im.getWidth() / thumbnailWidth;//축소할 비율을 구함

				width = thumbnailWidth;
				height = im.getHeight() / radio;
			}else{
				width = thumbnailWidth;
				height = thumbnailHeight;
			}

			BufferedImage thumb = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D 	g2 = thumb.createGraphics();

			g2.drawImage(im.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, width, height, null);
			ImageIO.write(thumb, "jpg", new File(UPLOAD_PATH,thumbnailFile));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fs!=null)try {fs.close();} catch (IOException e) {}
		}
		return thumbnailFile;
	}
}






