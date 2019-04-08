package cn.jianwoo.eshop.adminweb.controller;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UploadController {

    @Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	@PostMapping(value = "/uploadfile")
	public EShopResult fileUpload(@RequestParam("file") MultipartFile uploadFile) {
		try {
			//1、取文件的扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			String name = originalFilename.substring(0,originalFilename.lastIndexOf(".") );
			//2、创建一个FastDFS的客户端
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/fastdfs-client.conf");
			//3、执行上传处理
			String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
//            System.out.println(path);
			//4、拼接返回的url和ip地址，拼装成完整的url
			String url = IMAGE_SERVER_URL + path;
			//5、返回map
			Map result = new HashMap<>()  ;
            System.out.println(url);
			result.put("url", url);
			result.put("name", name);
			return EShopResult.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			//5、返回map
			return EShopResult.error("图片上传失败");
		}
	}
}
