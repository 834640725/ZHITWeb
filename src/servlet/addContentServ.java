package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.omg.Dynamic.Parameter;

import com.jspsmart.upload.SmartUpload;

import service.ContentService;
import entity.Catlog;
import entity.Content;
import entity.User;

public class addContentServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletFileUpload upload;
	private final long MAXSize = 4194304*2L;//4*2MB
	private String filedir=null;
	/**
	 * Constructor of the object.
	 */
	public addContentServ() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
            doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out1 = response.getWriter();

		Map<String, String> map = new HashMap<String, String>();  
		String savePath = this.getServletContext().getRealPath("/")+"images";
		File file = new File(savePath);
		//�ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
		if (!file.exists() && !file.isDirectory()) {
		System.out.println(savePath+"Ŀ¼�����ڣ���Ҫ����");
		//����Ŀ¼
		file.mkdir();
		}
		//��Ϣ��ʾ
		String message = "";
		
		try{
		//ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
		//1������һ��DiskFileItemFactory����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//2������һ���ļ��ϴ�������
		ServletFileUpload upload = new ServletFileUpload(factory);
		//����ϴ��ļ�������������
		upload.setHeaderEncoding("UTF-8"); 
		//3���ж��ύ�����������Ƿ����ϴ���������
		if(!ServletFileUpload.isMultipartContent(request)){
		//���մ�ͳ��ʽ��ȡ����
        return;
		}
		//4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
		List<FileItem> list = upload.parseRequest(request);
		

		for(FileItem item : list){
		 	   if(item.isFormField()){
			    String name = item.getFieldName();
				String value = item.getString("UTF-8");
				    map.put(name, value);}
		 	     else{
				    String name = item.getFieldName();
					String filename = item.getName();
					map.put(name, filename);
				}
		       }
		
		    String refPic = map.get("RefPic");
		    String Pic = map.get("Pic");
		    System.out.println(refPic);
		    System.out.println(Pic);
		    if(refPic.equals("") && Pic.equals("")){  
		    //��������
			int catlogId = Integer.parseInt(map.get("CatlogId"));
			String caption = map.get("Caption");
			int userId = Integer.parseInt(map.get("AuthorId"));
			String source = map.get("Source");
			String text = map.get("Text");
			int hot = Integer.parseInt(map.get("Hot"));
			
			Catlog catlog = new Catlog();
			catlog.setCatlogId(catlogId);
			User user = new User();
			user.setUserId(userId);
			Content content = new Content();
			content.setCatlog(catlog);
			content.setCaption(caption);
			content.setAuthorId(user);
			content.setSource(source);				
			content.setText(text);
			content.setHot(hot);
			boolean b = (new ContentService()).addContent(content);
			if(b){
				out1.print("True");
			}else{
				out1.print("False");
			}
			out1.flush();
			out1.close();
		}else{
		for(FileItem item : list){
       //���fileitem�з�װ������ͨ�����������
		if(item.isFormField()){	
		String name = item.getFieldName();
		//�����ͨ����������ݵ�������������
		String value = item.getString("UTF-8");
		map.put(name, value);
		}else{//���fileitem�з�װ�����ϴ��ļ�
		//�õ��ϴ����ļ����ƣ�	
		String filename = item.getName();
		/*map.put("filepath", filename);*/
		if(filename==null || filename.trim().equals("")){
		continue;
		}
		//ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺 c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
		//�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
		filename = filename.substring(filename.lastIndexOf("\\")+1);
		//��ȡitem�е��ϴ��ļ���������
		InputStream in = item.getInputStream();
/*		//�õ��ļ����������
		String saveFilename = makeFileName(filename);*/
		//����һ���ļ������
		FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
		//����һ��������
		byte buffer[] = new byte[1024];
		//�ж��������е������Ƿ��Ѿ�����ı�ʶ
		int len = 0;
		//ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
		while((len=in.read(buffer))>0){
		//ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
		out.write(buffer, 0, len);
		}
		
        //��������
		int catlogId = Integer.parseInt(map.get("CatlogId"));
		String caption = map.get("Caption");
		int userId = Integer.parseInt(map.get("AuthorId"));
		String source = map.get("Source");
		String text = map.get("Text");
		int hot = Integer.parseInt(map.get("Hot"));
		
		Catlog catlog = new Catlog();
		catlog.setCatlogId(catlogId);
		User user = new User();
		user.setUserId(userId);
		Content content = new Content();
		content.setCatlog(catlog);
		content.setCaption(caption);
		content.setAuthorId(user);
		content.setSource(source);
		
		content.setPic(filename);
		content.setRefPic(filename);
		
		content.setText(text);
		content.setHot(hot);
		boolean b = (new ContentService()).addContent(content);
		if(b){
			out1.print("True");
		}else{
			out1.print("False");
		}
		//�ر�������
		in.close();
		//�ر������
		out.close();
		out1.flush();
		out1.close();
		//ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
		item.delete();
		message = "�ļ��ϴ��ɹ���";

		}
		
		}}
		}catch (Exception e) {
		message= "�ļ��ϴ�ʧ�ܣ�";
		e.printStackTrace();
		 
		}
	}

/*    private String makeFileName(String filename){  
        //Ϊ��ֹ�ļ����ǵ���������ҪΪ�ϴ��ļ�����һ��Ψһ���ļ���
        return UUID.randomUUID().toString() + "_" + filename;
    }*/
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
