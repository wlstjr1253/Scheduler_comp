package kr.record.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.record.dao.GalleryDao;
import kr.record.domain.GalleryDto;
import kr.util.FileUtil;

public class GalleryModifyAjaxAction implements Action {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		MultipartRequest multi = FileUtil.createFile(request);
		
		int g_idx = Integer.parseInt(multi.getParameter("g_idx"));
		String g_photo1 = multi.getFilesystemName("filename");
		
		GalleryDao dao = GalleryDao.getInstance();
		GalleryDto galleryItem = dao.getGalleryItem(g_idx);
		
		galleryItem.setG_idx(g_idx);
		galleryItem.setG_title(multi.getParameter("g_title"));
		galleryItem.setG_content(multi.getParameter("editordata"));
		if (g_photo1 != null) {
			galleryItem.setG_photo1(g_photo1);
			galleryItem.setG_photo2(FileUtil.createThumbnail(
					g_photo1, "m"+g_photo1, 145, 0));
		}

		int count = dao.updateGalleryItem(galleryItem);
		Map<String, Object> mapAjax = 
				new HashMap<String, Object>();

		if (count <= 0) {
			mapAjax.put("message", "수정 중 에러가 발생했습니다.");
		} else {
			mapAjax.put("message", "수정이 완료 되었습니다.");
		}
		
		// JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = 
				mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("jsonData", jsonData);		
		
		// JSP 경로 반환
		return "/WEB-INF/views/common/ajaxView.jsp";
	}

}
