﻿<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String basePathPic = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/images/";
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>协会概况</title>
<link type="text/css" rel="stylesheet" href="css/base.css" />
<link type="text/css" rel="stylesheet" href="css/css.css" />
<link href="css/con_fenyex.css" type="text/css" rel="stylesheet" />
<link href="css/pagination.css" rel='stylesheet' type='text/css' />
<script type="text/javascript" src="js/jquery1.42.min.js" ></script>
<script type="text/javascript" src="js/jquery.SuperSlide.2.1.1.js" ></script>
 <script type="text/javascript" src="js/jquery.scroll.js"></script>	
</head>

<body>

<!--header -->
<div id="header">
  <div class="w1190"><img src="image/head.gif" /></div>
</div>

<!--menu -->
<div id="menu" class="clearfix ofw mb15">
  <ul>
    <li><a href="index.jsp">网站首页 </a></li>
    <li><a href="xhgk.jsp">协会概况</a></li>
    <li><a href="hydt.jsp?Cat=8" >行业动态</a></li>
    <li><a href="hyzz.jsp">会员组织</a></li>
    <li><a href="zxfw.jsp?Cat=17" class="current">咨询服务</a></li>
  </ul>
</div>
 
 <div class="w1190 clearfix ofw">

  <!--left -->
  <div class="col">
    <div class="sub_nav mb15">
      <div class="nav_heading">
        <h2>行业动态</h2>
      </div>
      <ul class="toptab tabl">

      </ul>
    </div>     
  </div>

<!--right -->
  <div class="main">
    <div class="box-header clearfix ovr ">
      <div class="biaoti"><!-- <h2>协会动态</h2> --></div>
       <div class="location"><!-- <p class="tree">您的位置：<a href="index.jsp">首页</a>&gt;<a href="hydt.jsp">行业动态</a>&gt;协会动态</p> --></div>
    </div>
    
       <div class="news_list neirong">
	   </div>
                    
                    <!--分页 -->
                    <!-- -->
     <div class="tc mt15">
           <div id="Pagination" class="pagination">这里显示分页</div>
     </div>
 </div>
  
</div>

<!-- footer-->
<div class="ofw clearfix  mt15 box" id="footer">
  <div class="w1190 tc">CopyRight&nbsp;?&nbsp;智能制造网  主办单位：宁波市智能制造协会<br />
地址：宁波国际高新区研发园B区5号楼407室  电话：0574-87569052  传真：0574-87560954<br />  
   备案序号：浙ICP备16008081号 技术支持：<a href="http://www.cmpc.cc/" target="_blank">易模网络</a></div>
</div>

<script type="text/javascript" src="js/jquery.pagination.js"></script>
<script type="text/javascript">

var url = window.location.search;
if(url.indexOf("?")!=-1){
	var str = url.substr(5);
}
var hmf = str-17;    //和数据库中对应;
$(function(){
	$.ajax({
		type:"post",
	     url:"../getCatlogServ?parentId=4",
	     async:false,
	     dataType: "json",
	     success: function (data) {
	    	 for(var i=0;i<data.length;i++){
	    		 $(".tabl").append('<li><a><span></span>'+data[i].text+'</a></li>');
	    	}
	    	 $(".tabl li:eq("+hmf+") a").addClass('cur');    
	    	 $(".tabl li a").click(function(){
	    			$(".toptab a").removeClass('cur');
	    		    $(this).addClass('cur');
	    		    var index = $(".tabl li a").index(this);
	    		    var id = data[index].id;
	    		 $(function(){
	    			 $.ajax({
	    				 type:"post",
	    				 url:"../getCatlogByCatId?Cat="+id,
	    				 async:false,
	    			     dataType:"json",
	    			     success:function(data){
	    			    	  $(".biaoti").empty();
	    		        	  $(".location").empty();
	    		              $(".neirong").empty();
	    		              $(".pagination").empty();
	    			    	  $(".biaoti").append('<h2>'+data.text+'</h2>');
	        	    	      $(".location").append('<p class="tree">您的位置：<a href="index.jsp">首页</a>&gt;<a href="hydt.jsp">行业动态</a>&gt;'+data.text+'</p>');
	        	    	      
	        	    	      var pageSize =6;     //每页显示多少条记录
	        	    	      var pageIndex =10;    //总共几页
	        	    	      var total;           //总共多少记录
	        	    	       $(function() {
	        	    	          Init(0);
	        	    	          //alert(total);
	        	    	          //alert("1");      
	        	    	              $("#Pagination").pagination(total, {            
	        	    	                   callback: PageCallback,            
	        	    	                   prev_text: '上一页',             
	        	    	                   next_text: '下一页',              
	        	    	                   items_per_page: pageSize,              
	        	    	                   num_display_entries: 5,        
	        	    	                   num_edge_entries: 1,
	        	    	                   link_to:"javascript:void(0)"         
	        	    	               });             
	        	    	              function PageCallback(index, jq) {       
	        	    	                   Init(index);      
	        	    	              }
	        	    	          });
	        	    	       
	        	    	       function Init(pageIndex){
	        	    	           $.ajax({
	        	    	      	    type: "post",
	        	    	            url:"../getContentPaixuServ?Cat="+id+"&rows="+pageSize+"&page="+pageIndex,
	        	    	            async: false,
	        	    	            /* contentType:"application/x-www-form-urlencoded", */
	        	    	            dataType: "json",
	        	    	            success: function (data) {
	        	    	          	    $(".neirong").empty();
	        	    	          	    total = data.total;
	        	    	          	    var array = data.rows;
	        	    	          	    //alert(total);
	        	    	      	        for(var i=0;i<array.length;i++){
	        	    	      	        	var info=array[i];
	        	    	      	        	
	        	    	      	        	if(info.refPic != null){
	        	    	      	        	$(".neirong").append('<dl><h3><a href="'+info.CntURL+'?ContentId='+info.contentId+'" title="'+info.caption+'" >'+info.caption+'</a></h3><dt><a href="'+info.CntURL+'?ContentId='+info.contentId+'" title="'+info.caption+'"><img src="<%=basePathPic%>'+info.refPic+'" alt="'+info.caption+' width="150" height="95""></a></dt>  <dd class="shortdd">'+info.text+'</dd><span>发布时间：'+info.createDate+'</span></dl>') 
	        	    	      	        	}else{
	        	    	      	        	$(".neirong").append('<dl ><h3><a href="'+info.CntURL+'?ContentId='+info.contentId+'" title="'+info.caption+'">'+info.caption+'</a></h3><dd class="shortdd">'+info.text+'</dd><span>发布时间：'+info.createDate+'</span></dl>');
	        	    	      	        	};
	        	    	      	     }   	
	        	    	            },
	        	    	            error: function () {
	        	    	                alert("请求超时，请重试！");
	        	    	            }
	        	    	      	});  
	        	    	      };
	        	    	      
	        	    	      
	    			     }
	    			 })
	    		 })
	    		    
	    		    
	    		    
	    		    
	    	 })	  
	     },
	     error: function () {
	         alert("请求超时，请重试！");
	     }
	})
})

	    	     


</script>


<script type="text/javascript">



    $(function(){                     //您的位置
    	$.ajax({
    		type:"post",
    		url:"../getCatlogByCatId?Cat="+str,
    	    async:false,
    	    dataType:"json",
    	    success:function(data){
    	    	$(".biaoti").append('<h2>'+data.text+'</h2>');
    	    	$(".location").append('<p class="tree">您的位置：<a href="index.jsp">首页</a>&gt;<a href="hydt.jsp">行业动态</a>&gt;'+data.text+'</p>');
    	    },
    	error:function(){
    		alert("请求超时，请重试！");
    	}
    	})
    })


var pageSize =6;     //每页显示多少条记录
var pageIndex =10;    //总共几页
var total;           //总共多少记录
 $(function() {
    Init(0);
    //alert(total);
    //alert("1");      
        $("#Pagination").pagination(total, {            
             callback: PageCallback,            
             prev_text: '上一页',             
             next_text: '下一页',              
             items_per_page: pageSize,              
             num_display_entries: 5,        
             num_edge_entries: 1,
             link_to:"javascript:void(0)"         
         });             
        function PageCallback(index, jq) {       
             Init(index);      
        }
    });
    
 function Init(pageIndex){
     $.ajax({
	    type: "post",
      url:"../getContentPaixuServ?Cat="+str+"&rows="+pageSize+"&page="+pageIndex,
      async: false,
      /* contentType:"application/x-www-form-urlencoded", */
      dataType: "json",
      success: function (data) {
    	    $(".neirong").empty();
    	    total = data.total;
    	    var array = data.rows;
    	    //alert(total);
	        for(var i=0;i<array.length;i++){
	        	var info=array[i];
	        	
	        	if(info.refPic != null){
	        	$(".neirong").append('<dl><h3><a href="'+info.CntURL+'?ContentId='+info.contentId+'" title="'+info.caption+'" >'+info.caption+'</a></h3><dt><a href="sjjm.jsp?ContentId='+info.contentId+'" title="'+info.caption+'" ><img src="<%=basePathPic%>'+info.refPic+'" alt="'+info.caption+' width="150" height="95""></a></dt>  <dd class="shortdd">'+info.text+'</dd><span>发布时间：'+info.createDate+'</span></dl>') 
	        	}else{
	        	$(".neirong").append('<dl ><h3><a href="'+info.CntURL+'?ContentId='+info.contentId+'" title="'+info.caption+'" >'+info.caption+'</a></h3><dd class="shortdd">'+info.text+'</dd><span>发布时间：'+info.createDate+'</span></dl>');
	        	};
	     }   	
      },
      error: function () {
          alert("请求超时，请重试！");
      }
	});  
};

</script>

</body>
</html>