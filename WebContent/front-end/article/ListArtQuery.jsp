<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="java.util.*" %>
<%@ page import="com.art.model.*" %>

<%-- <jsp:useBean id="ListArt_ByCompositeQuery" scope="request" type="java.util.List<ArtVO>"/> --%>
<jsp:useBean id="artSvc" scope="page" class="com.art.model.ArtService"/>
<jsp:useBean id="memSvc" class="com.mem.model.MemDAO"/>

<!DOCTYPE html>




<html>
<head>
<meta charset="UTF-8">
<script src="<%=request.getContextPath()%>/resource/js/moment-with-locales.min.js" ></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.2/css/all.css" integrity="sha384-vSIIfh2YWi9wW0r9iZe7RJPrKwp6bG+s9QZMoITbCckVJqGCCRhc+ccxNcdpHuYu" crossorigin="anonymous">


<style>
	#artListCenter{
/* 		border-radius:0.5rem; */
 		line-height: 2rem;
        max-width: 100%;
        margin: 2rem;
	}
	.artContent{
		height: 10vh;
		white-space:nowrap;
		overflow:hidden;
		text-overflow:ellipsis;
		cursor: pointer; 
	}
	.artContent img{
		width: 10%;
		height: 10%;
		display: block;
    	margin-left: auto;
	}
	#artAuthor{
		width: 40vh;
	}
    #art_modal-header_like{
    	margin-top: 6px;
    	margin-left: 25px;    	
    }
    #artRpt_icon{
     	margin-top: 6px; 
     	margin-left: 20px;      
    }
    .modal-dialog {
	    position: relative;
	    width: auto;
	    margin: auto;
	    pointer-events: none;
	}
	@media (min-width: 576px){
		.modal-dialog {
		    max-width: 575px;
		    margin: auto;
		}	
	}
    .modal-dialog-scrollable .modal-content {
	     max-height: 100vh;
	     overflow: hidden;
	     margin: 0px;
	}
    @media (min-width: 576px){
		.modal-dialog-scrollable .modal-content {
	     	max-height: 100vh;
		}    
    }
    #artRepDiv{
    	width: 100%;
    }
    #artRepDiv textarea{
    	width: 90%;
    	border-style: hidden;
    	display: inline-block;
    	vertical-align: top;
    }
    #artRepDiv #artRepButton{
    	display: inline-block;
    	vertical-align: bottom;
    	font-size: 2rem;
    	
    }
</style>

<script type="text/javascript">

//??????????????????	
function ListArtQuery(){
// 	debugger;
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/art/art.do',
		data: {'action':'art_Show_By_AJAX'},
		dataType: 'json',
		success: function (artVO){
			//??????????????????
			$(artVO).each(function(i, item){
				$('#artListCenter').append(
						'<div id="artAuthor" style="display: inline-block"><div style="display: inline-block">?????????</div> <div style="display: inline-block">'+item.memName+'</div></div>'
						+'<div id="artAuthor" style="display: inline-block"><div style="display: inline-block">???????????????</div> <div style="display: inline-block">'+item.artMovType+'</div></div>'
						+'<div id="artTitle"><div style="font-size: 1.2rem;"><b>'+item.artTitle+'</b></div></div>'
						+'<div id="artTime"><div style="display: inline-block">???????????????</div> <div style="display: inline-block">'+moment(item.artTime).locale('zh_TW').format('llll')+'</div></div>'
						+'<div><div class="artContent" data-value="'+item.artNo+'">'+item.artContent+'</div></div><hr>')			
						;
				});			
		},
		error: function(){console.log("AJAX-ListArtQuery???????????????!")}
	});
		
	//?????????????????????????????????????????????????????????
	if('${openModal}' === 'openModal'){
		//????????????
		$('#basicModal').modal('show');
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/art/art.do',
			data: {'action':'art_Show_By_AJAX', 'artNo':'${artNo}'},
			dataType: 'json',
			success: function (artVO){
				$(artVO).each(function(i, item){
					clearOneArticle();
 					$('#myModalLabel').append(item.artTitle);
 					$('#artFav_header_like').attr('data-value', item.artNo)
 					$('#myModalLabel').attr('data-value', item.artNo)
 					$('#oneArtContent').append('<p>'+item.artContent+'</p>');
					console.log("item.memNo:"+item.memNo);
						
 					//??????????????????????????????????????????
 					if(item.memNo == '${memNo}'){
 						$('#memUpdateArt').show();
 						$('#artUpdateMemNo').val(item.memNo);
 						$('#artUpdateArtNo').val(item.artNo);
 					}else{
 						$('#memUpdateArt').hide();
 					}
 					 					
					//?????????????????????
	 				isArtFav();
					
	 			  	//?????????????????????
	 			    listAllArtRepByArtNo();
	 			  	
 					//?????????????????????artNo
 					<%
 					session.removeAttribute("openModal");
 					session.removeAttribute("artNo");
 					%>
				});
			},
			error: function(){console.log("AJAX-openModal???????????????!")}
		});
	}

	console.log("??????????????????====="+'${memNo}');
};

//??????????????????????????????
function isArtFav(){
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/art/artFav.do',
		data: {'action':'isArtFav', 'artNo':$('#artFav_header_like').attr('data-value')},
		dataType: 'json',
		success: function (artFavVO){
			$(artFavVO).each(function(i, item){
// 				debugger;
				if(item.isArtFav == true){
					$('#artFav_header_like').css('color', 'red');				    
				}else if(item.isArtFav == false){
					$('#artFav_header_like').css('color', '#94B8D5');
				}
			});
		},
		error: function(){console.log("AJAX-isArtFav???????????????!")}
	});
};

//???????????????????????????
function changeArtFav(){
	$('#artFav_header_like').click(function(){
		console.log("$('#artFav_header_like').css('color'):"+$('#artFav_header_like').css('color'));
		if($('#artFav_header_like').css('color') == 'rgb(255, 0, 0)'){
// 			debugger;
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/art/artFav.do',
				data: {'action':'deleteArtFav', 'artNo':$('#artFav_header_like').attr('data-value')},
				dataType: 'json',
				success: function (){
							$('#artFav_header_like').css('color', '#94B8D5');
							toastr['warning']('????????????', '??????');
				},
				error: function(){console.log("AJAX-changeArtFav???????????????!")}
			});
		}else{
			$.ajax({
				type: 'POST',
				url: '<%=request.getContextPath()%>/art/artFav.do',
				data: {'action':'addArtFav', 'artNo':$('#artFav_header_like').attr('data-value')},
				dataType: 'json',
				success: function (){
							$('#artFav_header_like').css('color', 'red');
							toastr['success']('????????????', '??????');
				},
				error: function(){console.log("AJAX-changeArtFav???????????????!")}
			});
		}
	});	
};

//????????????
function addArtRpt(){
	$('#artRptButton').click(function(){
// 		debugger;
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/art/artRpt.do',
			data: {'action':'addArtRpt', 'artNo':$('#artRptButton').attr('data-value'), 'artRptReson':$('#artRptReson').val()},
			dataType: 'json',
			success: function(){
				clearArtRptText();
				toastr['warning']('????????????', '??????');
			},
			error: function(){console.log("AJAX-addArtRpe???????????????!")}
		});
	});
};

//????????????
function addArtRep(){
	$('#artRepButton').click(function(){
// 		debugger;
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/art/artRep.do',
			data: {'action':'addArtRep', 'artNo':$('#myModalLabel').attr('data-value'), 'artRepContent':$('#artRepContent').val()},
			dataType: 'json',
			success: function(){
				clearArtRepContent();
				listAllArtRepByArtNo();
				toastr['success']('????????????', '??????');

			},
			error: function(){console.log("AJAX-addArtRpe???????????????!")}
		});
	});
};

//???????????????
function listAllArtRepByArtNo(){
	$.ajax({
		type: 'POST',
		url: '<%=request.getContextPath()%>/art/artRep.do',
		data: {'action':'listAllArtRepByArtNo', 'artNo':$('#myModalLabel').attr('data-value')},
		dataType: 'json',
		success: function(artRepVO){
			//??????????????????
			clearArtRepList();
			if(artRepVO.length == 0){
				$('#artRep').append('<div style="font-size: 3rem; color: #808080">????????????</div>');
			}else{
				$(artRepVO).each(function(i, item){
					$('#artRep').append('<div style="line-height: 300%"><div id="memName" style="display:inline-block; width: 20%;">'+item.memName+'</div><div id="artRepTime" style="display:inline-block; color: #6C6C6C; width: 60%">'+moment(item.artRepTime).locale('zh_TW').format('llll')+'</div><i id="artRepRpt_icon" class="fas fa-exclamation-circle dropdown-toggle dropdown" data-toggle="dropdown" title="????????????" style="font-size: 1.5rem; color: #94B8D5;"></i><div class="dropdown-menu"><div class="form-group" data-value='+item.artRepNo+'>????????????<input type="text" class="form-control artRepRptReson" placeholder="????????????" style="width: 100%;"></div><button class="btn btn-outline-secondary artRepRptButton">??????</button></div><div class="artRepContentList" style="text-indent: 2em;">'+item.artRepContent+'</div><hr>');				
				});					
			}
			

		},
		error: function(){console.log("AJAX-listAllArtRepByArtNo???????????????!")}
	});
};

//????????????
function addRepRpt(){
	$('#artRep').on('click', '.artRepRptButton', function(event){
// 		debugger;
		$.ajax({
			type: 'POST',
			url: '<%=request.getContextPath()%>/art/artRepRpt.do',
			data: {'action':'addRepRpt', 'artRepNo':$(event.currentTarget).prev('.form-group').attr('data-value') , 'artRepRptReson':$(event.currentTarget).prev('.form-group').find('.artRepRptReson').val()},
			dataType: 'json',
			success: function(){
				clearRepRptReson();
				toastr['warning']('????????????', '??????');
			},
			error: function(){console.log("AJAX-addRepRpt???????????????!")}
		});
	});
};

//????????????
function clearOneArticle(){
	$('#oneArtContent').empty();
	$('#myModalLabel').empty();
}

//??????????????????
function clearArtRepList(){
	$('#artRep').empty();
};

//??????????????????
function clearArtRptText(){
	$('#artRptReson').val("");
};

//??????????????????
function clearArtRepContent(){
	$('#artRepContent').val("");
};

//??????????????????
function clearRepRptReson(){
	$('.artRepRptReson').val("");
};
</script>

</head>
<body>
<!-- ?????????????????? -->
<!--??????AJAX?????????????????? -->
<div id="artListCenter">

</div>
<!-- ?????????????????? -->

<!-- ???????????? -->
<div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModal" aria-hidden="true">
	<div class="modal-dialog modal-dialog-scrollable" role="document">
		<div class="modal-content" style="height: 100vh">
                <div id="art_modal-header" class="modal-header">
                    <div class="modal-title col-md-11">
                        <h3 id="myModalLabel">??????????????????</h3>

                        <!--???????????? -->
                        <div id="art_modal-header_like" class="float-right">
                            <c:if test="${memNo != null}">
                                    <i id="artFav_header_like" class="fas fa-heart" title="??????" style="font-size: 1.8em; color: #94B8D5;"></i>
                            </c:if>
                        </div>
                        
                        <!-- ??????button -->
						<div class="btn-group float-right" style="box-sizing: border-box;">
							<i id="artRpt_icon" class="fas fa-frown dropdown-toggle dropdown" data-toggle="dropdown" title="??????" style="font-size: 1.8em; color: #94B8D5;"></i>
						    <div class="dropdown-menu">
						         <div class="form-group">
						              <label for="artRptReson">????????????</label>
						              <input type="text" class="form-control" id="artRptReson" placeholder="????????????" style="width: 100%;">
						         </div>
						         <button id="artRptButton" class="btn btn-outline-secondary">??????</button>
						    </div>
						 </div>
						                          
                        <!--?????????????????? -->
                        <div id="art_modal-header_update" class="float-right">
                            <form method="POST" action="<%= request.getContextPath()%>/art/art.do">
                                <input type="hidden" id="artUpdateMemNo" name="memNo" value="">
                                <input type="hidden" id="artUpdateArtNo" name="artNo" value="">
                                <input type="hidden" name="action" value="select_Upadte_One_Art">
                                <input type="submit" id="memUpdateArt" class="btn btn-outline-secondary" value="????????????">
                            </form>
                        </div>
                        
                    </div>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>			
			<div class="modal-body">
<!-- ====================include ArticleContent.jsp==================== -->
				<div id="art_modal_body" data-value="">
					<%@ include file="/front-end/article/ArticleContent.file" %> 
				</div>
<!-- ====================include ArticleContent.jsp==================== -->
			</div>

			<div id="art_modal-footer" class="modal-footer" style="padding: 0px">
				<div id="artRepDiv">
					<textarea id="artRepContent"></textarea>
	            	<i id="artRepButton" class="fas fa-reply-all" title="??????"></i>
            	</div>
            </div>
		</div>
	</div>	
</div>
<!-- ???????????? -->
</body>
</html>