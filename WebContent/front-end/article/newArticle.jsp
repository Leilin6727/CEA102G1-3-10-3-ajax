<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%@ page import="com.art.model.*" %>
<%
	ArtVO artVO = (ArtVO)request.getAttribute("artVO");
%> 

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
<%--     <script src="<%=request.getContextPath()%>/resource/CKEditor/ckeditor.js"></script> --%>
    <script src="https://cdn.ckeditor.com/ckeditor5/26.0.0/classic/ckeditor.js"></script>
    <!-- bootstrap -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	
</head>

<body>

    <div class="container">
            <div class="row">
                <!-- 錯誤列表 -->
                <c:if test="${not empty errorMsgs}">
                    <font style="color:red">請修正以下錯誤:</font>
                    <ul>
                        <c:forEach var="message" items="${errorMsgs}">
                            <li style="color:red">${message}</li>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>
		<form method="POST" action="<%= request.getContextPath()%>/art/art.do">
            <div class="row">
                <div class="col-9">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="inputGroup-sizing-lg">文章標題</span>
                        </div>
                        <input type="text" id="artTitle" name="artTitle" class="form-control" value=""
                            aria-label="Sizing example input" aria-describedby="inputGroup-sizing-lg" required>
                    </div>
                </div>
                <div class="col-3">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="inputGroupSelect01">電影類型</label>
                        </div>
                        <select class="custom-select" id="artMovTypeSelect" name="artMovTypeSelect" required>
                                <option value="">請選擇</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
	            <div class="col-12">
	                <input type="text" id="editor" name="editor"><br>
	                <input type="hidden" name="action" value="addArt">
	            </div>
            </div>
            <div class="row">
	            <div class="col-12">
	                <button id="btn">送出</button>
	            </div>
            </div>
        </form>
    </div>

    <script>
    var myEditor;
        class MyUploadAdapter {
            // According to the document we need a constructor first
            constructor(loader) {
                this.loader = loader;
            }
            // start upload methods
            upload() {
                return new Promise((resolve, reject) => {
                    // 使用 FileReader() 物件讀取檔案
                    const reader = this.reader = new window.FileReader();
                    // 觸發 load 事件後，resolve 丟回物件完成 Promise
                    reader.addEventListener('load', () => {
                        resolve({ default: reader.result });
                    });

                    // 觸發錯誤事件，以 reject 丟回 Promise 失敗原因
                    reader.addEventListener('error', err => {
                        reject(err);
                    });
                    // 觸發 abort 事件時，以 reject() ，使 Promise 失敗
                    reader.addEventListener('abort', () => {
                        reject();
                    });

                    // 告訴 FileReader 物件用 url 格式讀取，用於設定 img.src 性質
                    this.loader.file.then(file => {
                        reader.readAsDataURL(file);
                    });
                });


            }

            // start abort()
            abort() {
                //  觸發 FileReader abort 事件
                this.reader.abort();
            }
        }

        function MyAdapterPlugin(editor) {
            editor.plugins.get('FileRepository').createUploadAdapter = (loader) => { return new MyUploadAdapter(loader) };
        };

        ClassicEditor
	        .create(document.querySelector('#editor'), {
	            extraPlugins: [MyAdapterPlugin]
	        })
	        .then(editor => {
	            console.log('editor', editor);
	            myEditor = editor;
	        })
	        .catch(error => {
	            console.error(error);
	        });
    </script>

	<script>	
	//讀取原本內容
		window.addEventListener("load", function(){
			//取得原本內容
			console.log(document.getElementById("editor").value);
			if('<%=artVO.getArtContent()%>' !== 'null'){
				myEditor.setData('<%=artVO.getArtContent()%>');
			}
			if('<%=artVO.getArtTitle()%>' !== 'null'){
				$('#artTitle').val('<%=artVO.getArtTitle()%>');
			}
			
		    //增加button class
		    $('button').addClass('btn btn-outline-secondary');
		    
		    //增加select
		    debugger;
		   	$.ajax({
		   		type: 'POST',
		   		url: '<%=request.getContextPath()%>/art/art.do',
		   		data: {'action':'find_MoveType_By_AJAX'},
	    		dataType: 'json',
	    		success: function(data){
		    		debugger;
// 		    		clearArtMovTypeSelect();
		    		$(data).each(function(i, item){
		    			$('#artMovTypeSelect').append("<option value='"+item.artMovType+"' data-value='"+item.movTypeIndex+"'>"+item.artMovType+"</option>");
		    		});
	    		    //取得原本類型
	    		    console.log("artVO.getMovType():<%=artVO.getMovType()%>");
	    		    debugger;
	    		    if(<%=artVO.getMovType()%> != null){
		    		    $('#artMovTypeSelect').val("<%=artVO.getMovType()%>");	
	    		    }
		    	},
		    	error: function(){alert("AJAX-newArt-select發生錯誤囉!")}
		    });

	});
	
	function clearArtMovTypeSelect(){
		$('#artMovTypeSelect').empty();
		$('#artMovTypeSelect').append("<option value=''>請選擇</option>");
	};
	</script>

    <script>
        let btn = document.getElementById("btn");
        btn.addEventListener("click", function () {
            document.getElementById("editor").value = myEditor.getData();
            console.log("getData_editor:"+document.getElementById("editor").value);
            
        });
    </script>

    

</body>

</html>
