package entity;

public class User {
       private int userId;         //�û�id
       private String userName;    //�û���
       private String userPwd;     //�û�����
       private String name;        //�û�����
       private String showName;    //������ʾ�õ��û���Ϣ
       private String memo;        //��ע
       private int enabled;        //�Ƿ������¼��0-������1����Ĭ��Ϊ1
       public User() {
   		super();
   	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}   
}
