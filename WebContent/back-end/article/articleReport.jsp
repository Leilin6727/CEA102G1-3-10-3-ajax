<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>Back-End Management</title>
	<%@ include file="/back-end/files/sb_head.file"%>
</head>
<body class="sb-nav-fixed">
		<%@ include file="/back-end/files/sb_navbar.file"%> <!-- 引入navbar (上方) -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">            
            	<%-- <c:set value="${pageContext.request.requestURI}" var="urlRecog"></c:set> --%> <!-- 在listAllXXX.jsp，加上這行，給sb_sidebar.file的參數-Home -->
                <%-- <c:set value="movieAdd" var="urlRecog"></c:set> --%> <!-- 在addXXX.jsp，加上這行，「value」請參照「sb_sidebar.file」給予相對應的值，給sb_sidebar.file的參數-Add -->
                <%-- <c:set value="movieSub" var="urlRecog"></c:set> --%> <!-- 在除了以上兩個jsp以外的子頁面，加上這行，「value」請參照「sb_sidebar.file」給予相對應的值，給sb_sidebar.file的參數-Sub -->         
				<%@ include file="/back-end/files/sb_sidebar.file"%> <!-- 引入sidebar (左方) -->
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid">
                    
                    <!-- PUT HERE Start-->
                    
                    <!-- table Start -->
                    <h3 id="tableTitle" class="h3-style" style="display: inline-block;"></h3>
				
		            <table id="listAllReport" class="table table-hover">
						<thead>
							<tr style="border-bottom: 3px solid #bb9d52;">
								<th>檢舉編號</th>
								<th>作者</th>
								<th>文章標題</th>
								<th>檢舉時間</th>
								<th>檢舉人</th>
								<th>檢舉理由</th>
								<th>狀態</th>
								<th>確認/取消</th>
							</tr>				
						</thead>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						<tbody>						
						</tbody>
					</table>
                    <!-- table End -->
                    
                                        <!-- table Start -->
                    <h3 class="h3-style" style="display: inline-block;">留言檢舉列表</h3>
				
		            <table id="listAllReport" class="table table-hover">
						<thead>
							<tr style="border-bottom: 3px solid #bb9d52;">
								<th>標題1</th>
								<th>標題2</th>
								<th>標題3</th>
								<th>標題4</th>
								<th>標題5</th>
							</tr>				
						</thead>
							<tr>
								<td>內容1</td>
								<td>內容2</td>
								<td>內容3</td>
								<td>內容4</td>
								<td>內容5</td>
							</tr>
						<tbody>						
						</tbody>
					</table>
                    <!-- table End -->
                                        
                    <!-- PUT HERE End-->
                    
                    </div>
                </main>
                <%@ include file="/back-end/files/sb_footer.file"%>
            </div>
        </div>
		<%@ include file="/back-end/files/sb_importJs.file"%> <!-- 引入template要用的js -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		
		<script>
		$(document).ready(function(){
			//列出全部文章檢舉
			listAllArticleReport();
			
			//列出全部留言檢舉
		});
		
		//全部文章檢舉
		function listAllArticleReport(){
			debugger;
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/art/artRpt.do',
				data: {'action':'listAllArticleReport'},
				dataType: 'json',
				success: function(artRptVO){
					debugger;
					$('#tableTitle').append("文章檢舉列表");
					$(artRptVO).each(function(i, item){
						$('#listAllReport').append('<tr><td>'+item.artRptNo+'</td><td>'+item.memName+'</td><td style="width: 50px;"><div id="inner">'+item.artTitle+'</div></td><td>'+item.artRptTime+'</td><td>'+item.reportMemName+'</td><td>'+item.artRptContent+'</td><td>'+item.artRptStatus+'</td><td><button id="checkedButton" data-value="'+artNo+'" style="background-color: #bb9d52;">'+item.artRptStatusButton+'</button></td></tr>');
					});
				},
                error: function () {
                    console.log("AJAX-listAllArticleReport發生錯誤囉!")
                }
			});
		};
		
		//更改確認文章檢舉
		function changeArticleReport(){
			$('#checkedButton').on('click', function(){
				console.log('changeArticleReport ok!');
				$.ajax({
					type: 'POST',
					url: '<%=request.getContextPath()%>/art/artRpt.do',
					data: {'action':'changeArticleReport', 'artNo':$('#checkedButton').attr('data-value')},
					dataType: 'json',
					success: function(){
						
					},
	                error: function () {
	                    console.log("AJAX-changeArticleReport發生錯誤囉!")
	                }					
				});
			});
		};
		</script>
    </body>
</html>