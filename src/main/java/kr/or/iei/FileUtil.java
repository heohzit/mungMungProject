package kr.or.iei;

import java.io.File;

import org.springframework.stereotype.Component;

@Component
public class FileUtil {
	public String getFilepath(String savepath,String filename) {
		String onlyfilename = filename.substring(0,filename.lastIndexOf("."));
		String extention = filename.substring(filename.lastIndexOf("."));
		String filepath = null;
		int count = 0;
		while(true) {
			if(count ==0) {
				filepath = onlyfilename+extention;
			}else {
				filepath = onlyfilename+"_"+count+extention;
			}
			File checkFile = new File(savepath+filepath);
			if(!checkFile.exists()) {
				break;
			}
			count++;		
		}
		return filepath;
	}
}
