package kr.record.action;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.record.dao.GalleryDao;
import kr.record.domain.GalleryDto;
import kr.util.AuthUtil;
import kr.util.FileUtil;

public class GalleryDataAction implements Action {

	@Override
	public String execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		//==========로그인 체크 시작=========//
		if(!AuthUtil.isLogin(request)) {
			return "redirect:/main/login.do";
		}
		//=========로그인 체크 끝===========//
		
		MultipartRequest multi = FileUtil.createFile(request);
		
		String photo1 = multi.getFilesystemName("filename");
		
		String photo2 = null;
		String photo3 = null;
		
		if (photo1 != null) {
			// 썸네일 만들기
			photo2 = FileUtil.createThumbnail(photo1, "m"+photo1, 145, 0);
		}
		
		GalleryDto gallery = new GalleryDto();
		gallery.setG_title(multi.getParameter("g_title"));
		gallery.setId(AuthUtil.getUser_id(request));
		gallery.setG_content(multi.getParameter("editordata"));
		gallery.setG_photo1(photo1);
		gallery.setG_photo2(photo2);
		gallery.setG_photo3(photo3);		
		
		GalleryDao dao = GalleryDao.getInstance();
		dao.insertGallery(gallery);

		// JSP 경로 반환
		return "redirect:/record/gallery.do";
	}

}
