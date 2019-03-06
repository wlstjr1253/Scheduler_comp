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

//���� ���ε� ����� �����ϱ� ���ؼ� cos.jar ������ �ʼ�������
//�䱸��
public class FileUtil {
	//���ε� ���
	public static final String UPLOAD_PATH="D:\\javaWork\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Scheduler\\upload";
	// public static final String UPLOAD_PATH="D:\\javaWork\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\Scheduler\\upload";
	// public static final String UPLOAD_PATH="D:\\javaWork\\workspace\\NCS\\Scheduler\\imgUpload";
	// public static final String UPLOAD_PATH="D:\\study\\web_app\\NCS\\Scheduler\\WebContent\\upload";
	//���ڵ� Ÿ��
	public static final String ENCODING_TYPE="utf-8";
	//�ִ� ���ε� ������
	public static final int MAX_SIZE=10*1024*1024;
	
	//���� ���ε� ��ο� ����
	public static MultipartRequest createFile(
			             HttpServletRequest request)
                             throws IOException{
		return new MultipartRequest(request,
				                    UPLOAD_PATH,
				                    MAX_SIZE,
				                    ENCODING_TYPE,
				        new DefaultFileRenamePolicy());
	}
	//���� ����
	public static void removeFile(String filename) {
		if(filename != null) {
			File file = new File(UPLOAD_PATH,filename);
			if(file.exists()) file.delete();
		}
	}
	
	// ������ ������  ����� ����
	public static String createThumbnail(String uploadedFile,int thumbnailWidth, int thumbnailHeight){
		return createThumbnail(uploadedFile, null, thumbnailWidth, thumbnailHeight);
	}
	
	public static String createThumbnail(String uploadedFile,String thumbnailFile,int thumbnailWidth, int thumbnailHeight){
		if(thumbnailFile==null){
			int index = uploadedFile.lastIndexOf(".");
			if(index !=-1){//������� Ȯ���ڴ� jpg�� ����
				thumbnailFile = "s" + uploadedFile.substring(0,index) + ".jpg";
			}
		}else{
			int index = thumbnailFile.lastIndexOf(".");
			if(index !=-1){//������� Ȯ���ڴ� jpg�� ����
				thumbnailFile = thumbnailFile.substring(0,index) + ".jpg";
			}
		}
		
		FileInputStream fs = null; 
		try { 
			fs = new FileInputStream(UPLOAD_PATH+"/"+uploadedFile);
			BufferedImage im = ImageIO.read(fs);

			int width;
			int height;

			if(thumbnailHeight == 0){//���̸� 0���� �������� ��� ���̸� 

				int radio = im.getWidth() / thumbnailWidth;//����� ������ ����

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






