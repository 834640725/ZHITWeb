package service;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicListUI.ListSelectionHandler;

import DAL.ContentDao;
import entity.Catlog;
import entity.Content;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ContentService {
	/**
	 * ��������id��ȡ������Ϣ,����������
	 * @param 
	 * @return
	 */
	public JSONObject getContentById(int contentId) {
		// TODO Auto-generated method stub
		JSONObject object=new JSONObject();
		Content content=(new ContentDao()).selectContentById(contentId);
		if(content!=null){
			object.put("ContentId", content.getContentId());
			object.put("Caption", content.getCaption());
			object.put("CreateDate", content.getCreateDate());
			object.put("Times", content.getTimes());
			object.put("Source", content.getSource());
			object.put("Text", content.getText());
			object.put("Pic", content.getPic());
			object.put("RefPic", content.getRefPic());
			object.put("Hot", content.getHot());
			int id = content.getAuthorId().getUserId();
			String ShowName = (new UserService()).selectUserById(id).getString("ShowName");
			object.put("ShowName", ShowName);
		}
		return object;
	}
	
	public JSONArray getIndexById(int indexId) {
		// TODO Auto-generated method stub
		JSONArray array=new JSONArray();
		List<Content>commentlist = (new ContentDao()).selectIndexById(indexId);
		//Content content=(new ContentDao()).selectIndexById(indexId);
		for(Content content:commentlist){
			JSONObject object=new JSONObject();
			object.put("contentId", content.getContentId());
			object.put("caption", content.getCaption());
			object.put("createDate", content.getCreateDate());
			object.put("times", String.valueOf(content.getTimes()));
			object.put("source", content.getSource());
			object.put("text", content.getText());
			object.put("pic", content.getPic());
			object.put("refPic", content.getRefPic());
			object.put("hot", content.getHot());
			object.put("userId", content.getAuthorId().getUserId());
			int id = content.getAuthorId().getUserId();
			String ShowName = (new UserService()).selectUserById(id).getString("ShowName");
			object.put("showName", ShowName);
			
			object.put("catlogId", content.getCatlog().getCatlogId());
			int Id = content.getCatlog().getCatlogId();
			String CntURL = (new CatlogService()).selectCatlogById(Id).getString("CntURL");
			object.put("CntURL", CntURL);
			array.add(object);
		}
		return array;
	}
	
	//�����ʱ��
	public JSONArray getTimeById(int indexId,int page,int rows) {
		// TODO Auto-generated method stub
		JSONArray array=new JSONArray();
		List<Content>contentlist1 = (new ContentDao()).selecttimeById(indexId);
		List<Content>contentlist = epaginationOrder(contentlist1, page, rows);
		//Content content=(new ContentDao()).selectIndexById(indexId);
		for(Content content:contentlist){
			JSONObject object=new JSONObject();
			object.put("contentId", content.getContentId());
			object.put("caption", content.getCaption());
			object.put("createDate", content.getCreateDate());
			object.put("times", String.valueOf(content.getTimes()));
			object.put("source", content.getSource());
			object.put("text", content.getText());
			object.put("pic", content.getPic());
			object.put("refPic", content.getRefPic());
			object.put("hot", content.getHot());
			object.put("userId", content.getAuthorId().getUserId());
			int id = content.getAuthorId().getUserId();
			String ShowName = (new UserService()).selectUserById(id).getString("ShowName");
			object.put("showName", ShowName);
			array.add(object);
		}
		return array;
	}
	
	//����ԱȨ��
	public JSONArray getById(int indexId,int userId,int page,int rows) {
		// TODO Auto-generated method stub
		JSONArray array=new JSONArray();
		List<Content>contentlist1 = (new ContentDao()).selectById(indexId,userId);
		List<Content>contentlist = epaginationOrder(contentlist1, page, rows);
		//Content content=(new ContentDao()).selectIndexById(indexId);
		for(Content content:contentlist){
			JSONObject object=new JSONObject();
			object.put("contentId", content.getContentId());
			object.put("caption", content.getCaption());
			object.put("createDate", content.getCreateDate());
			object.put("times", String.valueOf(content.getTimes()));
			object.put("source", content.getSource());
			object.put("text", content.getText());
			object.put("pic", content.getPic());
			object.put("refPic", content.getRefPic());
			object.put("hot", content.getHot());
			object.put("userId", content.getAuthorId().getUserId());
			int id = content.getAuthorId().getUserId();
			String ShowName = (new UserService()).selectUserById(id).getString("ShowName");
			object.put("showName", ShowName);
			array.add(object);
		}
		return array;
	}
	
	//easyui��ҳ
	private List<Content> epaginationOrder(List<Content> list,int page,int rows){
		List<Content>small=new ArrayList<Content>();
		int beginIndex=rows*(page-1);
		int endIndex;
		if(rows*page>list.size()){
			endIndex=list.size();
		}
		else{
			endIndex=rows*page;
		}
		for(int i=beginIndex;i<endIndex;i++){  
			small.add(list.get(i));  
        }  
		return small;
	}
	
	//��ȡһ����Ŀ����������
	public int getContentTotal(int indexId){
		List<Content>content=(new ContentDao()).selectIndexById(indexId);
		int i=content.size();
		return i;
	}
	
	/**
	 * ��������
	 * @param 
	 * @return
	 */
	public boolean addContent(Content content) {
		// TODO Auto-generated method stub
		int i=(new ContentDao()).insertContent(content);
		if(i>0){
			return true;
		}
		return false;
	}
	
	/**
	 *  ɾ������
	 * @param 
	 * @return
	 */
	public boolean deletecontent(int contentId) {
		// TODO Auto-generated method stub
		int i=(new ContentDao()).deleteContent(contentId);
		if(i>0){
			return true;
		}
		return false;
	}
	
	//�༭
	public boolean updateContent(Content content) {
		int i = (new ContentDao()).updateContent(content);
		if(i>0) {return true;}
		else {return false;}
	}
	
	//�Ķ�����
	public boolean updateTimes(Content content) {
		int i = (new ContentDao()).updateTimes(content);
		if(i>0) {return true;}
		else {return false;}
	}
	
	//ͼƬ
	public JSONArray selectPic() {
		// TODO Auto-generated method stub
		JSONArray array=new JSONArray();
		List<Content>contentlist = (new ContentDao()).selectPic();
		for(Content content:contentlist){
			JSONObject object=new JSONObject();
			object.put("contentId", content.getContentId());
			object.put("caption", content.getCaption());
			object.put("createDate", content.getCreateDate());
			object.put("pic", content.getPic());
			object.put("refPic", content.getRefPic());
			
			int id = content.getCatlog().getCatlogId();
			String CntURL = (new CatlogService()).selectCatlogById(id).getString("CntURL");
			object.put("CntURL", CntURL);
			
			array.add(object);
		}
		return array;
	}
	
	//hot
	public JSONArray selectHot() {
		// TODO Auto-generated method stub
		JSONArray array=new JSONArray();
		List<Content>contentlist = (new ContentDao()).selectHot();
		for(Content content:contentlist){
			JSONObject object=new JSONObject();
			object.put("contentId", content.getContentId());
			object.put("caption", content.getCaption());
			object.put("createDate", content.getCreateDate());
			object.put("times", content.getTimes());
			object.put("source", content.getSource());
			object.put("Text", content.getText());
			object.put("pic", content.getPic());
			object.put("refPic", content.getRefPic());
			
			object.put("catlogId", content.getCatlog().getCatlogId());
			int id = content.getCatlog().getCatlogId();
			String CntURL = (new CatlogService()).selectCatlogById(id).getString("CntURL");
			object.put("CntURL", CntURL);
			array.add(object);
		}
		return array;
	}
	
	
	//ҳ������
	public JSONObject getContentPaiXuById(int indexId, int page, int rows) {
		JSONArray array=new JSONArray();
		List<Content>contentlist1=(new ContentDao()).selectIndexById(indexId);
		List<Content>contentlist=paginationContent(contentlist1,page,rows);
		for(Content content:contentlist){
			JSONObject object=new JSONObject();
			object.put("contentId", content.getContentId());
			object.put("caption", content.getCaption());
			object.put("createDate", content.getCreateDate());
			object.put("times", String.valueOf(content.getTimes()));
			object.put("source", content.getSource());
			object.put("text", content.getText());
			object.put("pic", content.getPic());
			object.put("refPic", content.getRefPic());
			object.put("hot", content.getHot());
			object.put("userId", content.getAuthorId().getUserId());
			int id = content.getAuthorId().getUserId();
			String ShowName = (new UserService()).selectUserById(id).getString("ShowName");
			object.put("showName", ShowName);
			
			int Id = content.getCatlog().getCatlogId();  //CntURL������
			String CntURL = (new CatlogService()).selectCatlogById(Id).getString("CntURL");
			object.put("CntURL", CntURL);
			array.add(object);
			
		}
		JSONObject obj=new JSONObject();
		obj.put("total", contentlist1.size());
		obj.put("rows", array);
		return obj;
	}
	
	
	//��ȡ��ÿҳ�ĵ����ݡ�
	private List<Content> paginationContent(List<Content> list,int page,int rows){
		List<Content>small=new ArrayList<Content>();
		int beginIndex=rows*page;     
		System.out.println(beginIndex);
		int endIndex;
		if(rows*(page+1)>list.size()){   
			endIndex=list.size();        
		}
		else{
			endIndex=rows*(page+1);
		}
		for(int i=beginIndex;i<endIndex;i++){  
			small.add(list.get(i));  
	    }  
		return small;
	}
}


