package servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileImageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletFileUpload upload;
	private final long MAXSize = 4194304*2L;//4*2MB
	private String filedir=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileImageUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out1 = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		//String username=request.getParameter("username");
		//System.out.println(username);
		//�õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ��ʣ���֤�ϴ��ļ��İ�ȫ
		Map<String, String> map = new HashMap<String, String>();  
		String savePath = this.getServletContext().getRealPath("/")+"\\images";
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
		System.out.println("ִ����1");
		//System.out.println(list);
		for(FileItem item : list){
			System.out.println(item);
			System.out.println("ִ����2");
		//���fileitem�з�װ������ͨ�����������
		if(item.isFormField()){
		String name = item.getFieldName();
		//�����ͨ����������ݵ�������������
		String value = item.getString("UTF-8");
		map.put(name, value);
		//value = new String(value.getBytes("iso8859-1"),"UTF-8");
		System.out.println(name + "=" + value);
		}else{//���fileitem�з�װ�����ϴ��ļ�
		//�õ��ϴ����ļ����ƣ�
		String filename = item.getName();
		System.out.println(filename);
		map.put("filepath", filename);
		if(filename==null || filename.trim().equals("")){
		continue;
		}
		//ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺 c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
		//�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
		filename = filename.substring(filename.lastIndexOf("\\")+1);
		//��ȡitem�е��ϴ��ļ���������
		InputStream in = item.getInputStream();
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
		}
		
		}catch (Exception e) {
		message= "�ļ��ϴ�ʧ�ܣ�";
		e.printStackTrace();
		 
		}
/*		request.setAttribute("message",message);
		request.getRequestDispatcher("WebPage/message.jsp").forward(request, response);*/
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

}

