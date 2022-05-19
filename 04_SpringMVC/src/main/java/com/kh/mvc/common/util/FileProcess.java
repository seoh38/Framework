package com.kh.mvc.common.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileProcess{
	public static String save(MultipartFile upfile, String location) {
		String renamedFileName = null;
		String originalFileName = upfile.getOriginalFilename();
		
		log.info("Upfile Name : {} ", upfile.getOriginalFilename());
		log.info("location : {} ",location);
		
		// location이 실제로 존재하지 않으면 폴더를 생성하는 로직 작성! 
		File folder = new File(location);
		
		if (!folder.exists()) {
			// 폴더가 존재하지 않으면 폴더를 만들어줌!
			folder.mkdirs();
		}
		
		// 새로운 파일명으로 저장!(패턴을 지정 가능)
		renamedFileName = 
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS")) + 
				originalFileName.substring(originalFileName.lastIndexOf("."));
		
		try {
			// 업로드한 파일 데이터를 지정한 파일에 저장한다.
			// 파일객체 자체를 만들어서 지정한 위치에 파일을 만들어준다.( 물리적인 위치)
			// transferTo() 메소드를 통해서 지정한 위치에 파일을 옮긴다.
			upfile.transferTo(new File(location + "/" + renamedFileName));
			
		} catch (IllegalStateException | IOException e) {
			log.error("파일 전송 에러");
			e.printStackTrace();
		}
		
		return renamedFileName;
	}

	public static void delete(String loacation) {
		log.info("location : {} ",loacation);
		
		File file = new File(loacation);
		
		if (file.exists()) {
			file.delete();
		}
	}
	

}
