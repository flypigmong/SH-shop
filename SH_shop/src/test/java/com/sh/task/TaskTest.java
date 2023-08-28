package com.sh.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sh.mapper.AdminMapper;
import com.sh.model.AttachImageVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class TaskTest {

	@Autowired
	private AdminMapper mapper;
	
	/* 서버 디렉토리에 저장된 이미지 파일 리스트 가져오기  */
	private String getFolderYesterDay() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		
		String str = sdf.format(cal.getTime());
		
		return str.replace("-", File.separator);
	}
	

	@Test
	public void taskTests() {
		
		/* DB 이미지 파일 리스트 */
		
		//DB에 저장된 이미지 파일 리스트 가져옴
		List<AttachImageVO> fileList = mapper.checkFileList();
	
		System.out.println("fileList  : ");
		fileList.forEach( list -> System.out.println(list));
		System.out.println("==================");
		
		// 리스트 파일 정보들을 Path 객체로 변환
		List<Path> checkFilePath = new ArrayList<Path>();
		
		// checkFilePath 요소로 추가(원본이미지)
		for (AttachImageVO vo : fileList) {
		    Path path = Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
		    checkFilePath.add(path);
		}
		
		// checkFilePath 요소로 추가되었는지 확인
		System.out.println("checkFilePath : ");
		checkFilePath.forEach(list -> System.out.println(list));
		System.out.println("==================");

	
		// checkFilePath 요소로 추가(썸네일이미지)
		for (AttachImageVO vo : fileList) {
		    Path path = Paths.get("C:\\upload", vo.getUploadPath(), "s_" + vo.getUuid() + "_" + vo.getFileName());
		    checkFilePath.add(path);
		}
		
		//checkFilePath 요소로 추가되었는지 확인
		System.out.println("checkFilePath(썸네일 이미지 정보 추가 후) : ");
		checkFilePath.forEach(list -> System.out.println(list));
		System.out.println("==================");
		
		//체크할 대상의 이미지 파일이 저장된 디렉토리를 File객체로 생성 후 targetDir 변수에 대입
		File targetDir = Paths.get("C:\\upload", getFolderYesterDay()).toFile();
		// File 배열 객체 주소를 targetFile 변수에 대입
		File[] targetFile = targetDir.listFiles();
		
		//targetFile의 배열에 디렉토리에 저장되어 있는 파일의 정보들이 요소로 있는지 확인
		System.out.println("targetFile : ");
		for(File file : targetFile) {
			System.out.println(file);
		}
		
		System.out.println("=================");
		
		
	}
	
	
	
}
