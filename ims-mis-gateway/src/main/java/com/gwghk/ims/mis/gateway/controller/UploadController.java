package com.gwghk.ims.mis.gateway.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gwghk.ims.common.common.Constants;
import com.gwghk.ims.common.common.MisRespResult;
import com.gwghk.ims.common.common.MisResultCode;
import com.gwghk.ims.common.exception.IMSBussinessException;
import com.gwghk.unify.framework.file.UploadFileInfo;
import com.gwghk.unify.framework.file.fastdfs.FastDFSManager;

/**
 * 文件上传
 * @apiDefine SystemCommonApi 公共接口
 * 
 * @author jackson.tang
 */
@Controller
@RequestMapping("/mis")
public class UploadController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	/**
	 * @api {post} /mis/image/upload 图片上传
	 * @apiSampleRequest /mis/image/upload
	 * @apiGroup SystemCommonApi
	 * 
	 * @apiParam {CommonsMultipartFile} file 上传文件
	 * 
	 * @apiSuccess {String} resultCode 返回的状态码 0成功，其他表示失败，请查看全局状态码
	 * @apiSuccess {String} resultMsg 返回的信息
	 * @apiSuccess {Object} data 返回数据
	 * @apiSuccess {Object} extendData 扩展数据(总记录数)
	 *
	 * @apiSuccessExample {json} 返回样例
	 *	{
	 *		"resultCode": "0",
	 *		"resultMsg": "请求成功",
	 *		"data": {...},
	 *		"extendData": 1000
	 *	}
	 */
    @RequestMapping(value = "/image/upload", method = RequestMethod.POST)
    @ResponseBody
    public MisRespResult<UploadFileInfo> imageUpload(HttpServletRequest request, HttpServletResponse responce,@RequestParam(value="file") MultipartFile file) {
        try {
        	request.setCharacterEncoding("UTF-8");
        	responce.setCharacterEncoding("UTF-8");
        	responce.setContentType("text/html;charset=UTF-8");
            double megabytes = file.getSize() / (1024 * 1024);
            if (megabytes <= 5) {
            	UploadFileInfo uploadResult=uploadToFastDFS(file);
                return MisRespResult.success(uploadResult);
            } else {
                return MisRespResult.error(MisResultCode.Error40009.getMessage());
            }
        } 
        catch (IMSBussinessException e1) {
        	logger.error("<---uploadAttachment->file upload fail !", e1);
            return MisRespResult.error(e1.getMessage());
        }catch (Exception e2) {
            logger.error("<---uploadAttachment->file upload fail !", e2);
            return MisRespResult.error(MisResultCode.FAIL.getMessage());
        }
    }
    
    /**
     * 上传文件到FastDFS系统
     * @param file
     * @return
     * @throws IMSBussinessException
     * @throws IOException
     */
    private UploadFileInfo uploadToFastDFS(MultipartFile file) throws IMSBussinessException, IOException{
    	
        // 1、判断文件是否为null
        if (file == null) 
            throw new IMSBussinessException("未找到上传的文件！请检查;");
        
        // 2、文件大小及判断文件类型是否正确
        String orgFileName = file.getOriginalFilename();
        Long fileSize = file.getSize();
        if (fileSize > Constants.MAX_UPLOAD_IMAGE_SIZE) 
        	throw new IMSBussinessException("最大上传文件大小为5M，请检查;");
           
        
        String fileType = orgFileName.substring(orgFileName.lastIndexOf(".") + 1);
        if (!(fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("jpeg") || fileType.equalsIgnoreCase("bmp")
                || fileType.equalsIgnoreCase("gif") || fileType.equalsIgnoreCase("png"))) 
        		throw new IMSBussinessException("图片类型不正确，支持(jpg/jpeg/bmp/gif/png)，请检查;");
               
        // 3、上传文件
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "." + fileType;
        
        return FastDFSManager.upload(file.getInputStream(), fileName);
    }
    
}
