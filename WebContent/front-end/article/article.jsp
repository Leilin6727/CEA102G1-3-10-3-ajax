<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.util.*" %>
<%@ page import="com.art.model.*" %>
<jsp:useBean id="artSvc" scope="page" class="com.art.model.ArtService" />
<jsp:useBean id="memSvc" class="com.mem.model.MemDAO" />

<!DOCTYPE html>
<%		
// 		session.getAttribute("memVO");

		String memNo = "1"; //假設會員1登入
		session.setAttribute("memNo", memNo);
		session.getAttribute("memNo");

%>
<html>

<head>
    <meta charset="UTF-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css"
        href="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.css" />
    <script src="<%=request.getContextPath()%>/resource/datetimepicker/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/resource/datetimepicker/jquery.datetimepicker.full.js"></script>
    <!-- bootstrap -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous">
    </script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <!-- toastr v2.1.4 -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>

    <script>
        toastr.options = {
            // 參數設定
            "closeButton": false, // 顯示關閉按鈕
            "debug": false, // 除錯
            "newestOnTop": false, // 最新一筆顯示在最上面
            "progressBar": true, // 顯示隱藏時間進度條
            "positionClass": "toast-top-right", // 位置的類別
            "preventDuplicates": false, // 隱藏重覆訊息
            "onclick": null, // 當點選提示訊息時，則執行此函式
            "showDuration": "300", // 顯示時間(單位: 毫秒)
            "hideDuration": "1000", // 隱藏時間(單位: 毫秒)
            "timeOut": "5000", // 當超過此設定時間時，則隱藏提示訊息(單位: 毫秒)
            "extendedTimeOut": "1000", // 當使用者觸碰到提示訊息時，離開後超過此設定時間則隱藏提示訊息(單位: 毫秒)
            "showEasing": "swing", // 顯示動畫時間曲線
            "hideEasing": "linear", // 隱藏動畫時間曲線
            "showMethod": "fadeIn", // 顯示動畫效果
            "hideMethod": "fadeOut" // 隱藏動畫效果
        }
    </script>
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
        }

        img {
            max-width: 100%;
        }

        /*   	div.container{   */
        /*   		border: 1px solid blue;   */
        /*   	}   */
        /*   	div.col-3{   */
        /*   		border: 1px solid red;   */
        /*   	}   */
        /*   	div.col-9{   */
        /*   	    border: 1px solid black;   */
        /*   	}   */
        /*      #artListCenter{   */
        /*        	border: 1px solid green;   */
        /*       }   */
        #artListLeft {
            vertical-align: top;
            position: sticky;
            top: 0;
            border: 1px solid blud;
            overflow-y: auto;
            height: 100vh;

        }

        @media (max-width: 767px) {
            #artListLeft {
                height: auto;
            }

            #artListLeft #newArt {
                width: 54px;
                max-width: 100%;
            }
        }

        #artListLeft a {
            color: #666666;
        }

        #artListLeft a:hover {
            cursor: pointer;
        }

        .selectedMovType {
            background-color: #AA9166;
        }

        #newArt {
            margin: 5px;
            width: 54px;
        }

        @media (max-width: 576px) {
            #newArt {
                width: 90%;
            }
        }
    </style>

    <script>
        $(document).ready(function () {
            debugger;
            //列出側邊欄電影類型
            showArtMoveType();

            debugger;
            //列出全部文章列表
            ListArtQuery();

            //單篇文章燈箱
            debugger;
            $('#artListCenter').on('click', '.artContent', function (event) {
                console.log("artContent clicked:" + $(event.currentTarget).html());
                console.log("artNo:" + $(event.currentTarget).attr('data-value'));
                // 		debugger;
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/art/art.do',
                    data: {
                        'action': 'art_Show_By_AJAX',
                        'artNo': $(event.currentTarget).attr('data-value')
                    },
                    dataType: 'json',
                    success: function (artVO) {
                        debugger;
                        console.log('#basicModal' + document.getElementById('#basicModal'));
                        //開啟燈箱
                        $('#basicModal').modal('show');

                        //把文章標題內容放進燈箱
                        $(artVO).each(function (i, item) {
                            //取include ArticleContent.jsp後的id
                            console.log(item.artTitle);
                            clearOneArticle();
                            $('#myModalLabel').attr('data-value', item.artNo);
                            $('#myModalLabel').append(item.artTitle);
                            $('#oneArtContent').append('<p>' + item.artContent +
                                '</p>');
                            $('#artFav_header_like').attr('data-value', item.artNo);
                            $('#artRptButton').attr('data-value', item.artNo);
                            console.log("item.memNo:" + item.memNo);

                            //判斷是否為會員本人發表的文章
                            if (item.memNo == '${memNo}') {
                                $('#memUpdateArt').show();
                                $('#artUpdateMemNo').val(item.memNo);
                                $('#artUpdateArtNo').val(item.artNo);
                            } else {
                                $('#memUpdateArt').hide();
                            }

                            //判斷是否已收藏
                            isArtFav();

                            //呼叫列全部回文
                            debugger;
                            listAllArtRepByArtNo();
                        });
                        console.log("success");
                        console.log("${memNo}");

                    },
                    error: function () {
                        console.log("AJAX-ready發生錯誤囉!")
                    }
                });
            });

            //觸發複合查詢
            $('#findArtByTitleButton ,#findArtByCompositeQueryButton, #artTitleByCompositeQuery, #artTimeForByCompositeQuery, #artAuthorForByCompositeQuery')
                .on('click keypress', function (e) {
                    //     	debugger;
                    if (e.which === 13 || e.currentTarget.id === 'findArtByCompositeQueryButton' || e
                        .currentTarget.id === 'findArtByTitleButton') {
                        //         	debugger;
                        findArtByCompositeQuery(e);
                    }
                });

            //依電影類型查詢
            $('#artMovTypeList').on('click', 'li', function (e) {
                debugger;
                $('.selectedMovType').removeClass('selectedMovType');
                document.getElementById("artTitleByCompositeQuery").placeholder = '依' + $(this).data(
                    'value') + '查詢';
                debugger;
                clearArtCompositeQuery();
                $(this).addClass('selectedMovType');
                findArtByCompositeQuery(e);
            });

            //時間月曆
            $.datetimepicker.setLocale('zh');
            $('#artTimeForByCompositeQuery').datetimepicker({
                theme: '', //theme: 'dark',
                timepicker: false, //timepicker:true,
                format: 'Y-m-d', //format:'Y-m-d H:i:s',
                value: '', // value:   new Date(),
            });

            //增加button class
            $('button').addClass('btn btn-outline-secondary');

            //修改成功訊息
            if ('${updateSuccess}' == 'updateSuccess') {
                //      	debugger;
                toastr['success']('修改成功！！', '成功'); 
                <%
                session.removeAttribute("updateSuccess"); 
                %>
            }

            //新增成功訊息
            if ('${addSuccess}' == 'addSuccess') {
                //      	debugger;
                toastr['success']('發文成功！！', '成功'); 
                <%
                session.removeAttribute("addSuccess"); 
                %>
            }

            //呼叫收藏
            changeArtFav();

            //呼叫檢舉
            addArtRpt();

            //呼叫新增留言
            addArtRep();

            //呼叫檢舉留言
            addRepRpt();
        });

        //側邊欄電影類型
        function showArtMoveType() {
            $.ajax({
                type: 'POST',
                url: '<%=request.getContextPath()%>/art/art.do',
                data: {
                    'action': 'find_MoveType_By_AJAX'
                },
                dataType: 'json',
                success: function (artVO) {
                    clearArtMoveList();
                    // 			debugger;
                    $(artVO).each(function (i, item) {
                        $('#artMovTypeList').append(
                            '<li id="' + item.movTypeIndex + '" class="nav-item" data-value="' +
                            item.artMovType + '"><a class="nav-link" href="#">' + item
                            .artMovType + '</a></li>'
                        );
                    });
                },
                error: function () {
                    console.log("AJAX-showArtMoveType發生錯誤囉!")
                }
            });
        };

        //複合查詢
        function findArtByCompositeQuery(e) {
            // 	debugger;
            $.ajax({
                type: 'post',
                url: '<%=request.getContextPath()%>/art/art.do',
                data: addCompositeQueryData(e),
                dataType: 'json',
                success: function (artVO) {
                    clearArtList();
                    $(artVO).each(function (i, item) {
                        //             	debugger;
                        $('#artListCenter').append(
                            '<div id="artAuthor" style="display: inline-block"><div style="display: inline-block">作者：</div> <div style="display: inline-block">' +
                            item.memName + '</div></div>' +
                            '<div id="artAuthor" style="display: inline-block"><div style="display: inline-block">電影類型：</div> <div style="display: inline-block">' +
                            item.artMovType + '</div></div>' +
                            '<div id="artTitle"><div style="font-size: 1.2rem;"><b>' + item
                            .artTitle + '</b></div></div>' +
                            '<div id="artTime"><div style="display: inline-block">修改時間：</div> <div style="display: inline-block">' +
                            moment(item.artTime).locale('zh_TW').format('llll') +
                            '</div></div>' +
                            '<div><div class="artContent" data-value="' + item.artNo + '">' +
                            item.artContent + '</div></div><hr>');
                        $('#artListCenter .artContent').css({
                            'height': '10vh',
                            'white-space': 'nowrap',
                            'overflow': 'hidden',
                            'text-overflow': 'ellipsis',
                            'cursor': 'pointer'
                        });
                    });
                },
                error: function () {
                    console.log('AJAX-findArtByCompositeQuery發生錯誤囉!')
                }
            });
        };

        //複合查詢data
        function addCompositeQueryData(e) {
            var addArtDataAttr = {
                'action': 'find_By_CompositeQuery_Use_AJAX'
            };
            // 	debugger;
            addArtDataAttr['artMovType'] = $('.selectedMovType').attr('data-value');
            console.log('artMovType add ' + addArtDataAttr);
            // 	debugger;
            if ($('#artTitleByCompositeQuery').val() != null) {
                addArtDataAttr['artTitle'] = $('#artTitleByCompositeQuery').val();
                console.log("artTitle add ");
            }
            // 	debugger;
            if ($('#artTimeForByCompositeQuery').val() != null) {
                addArtDataAttr['artTime'] = $('#artTimeForByCompositeQuery').val();
                console.log("artTime add " + addArtDataAttr);
            }
            // 	debugger;
            if ($('#artAuthorForByCompositeQuery').val() != null) {
                addArtDataAttr['artAuthor'] = $('#artAuthorForByCompositeQuery').val();
                console.log("artAuthor add " + addArtDataAttr);
            }

            console.log("addCompositeQueryData:" + addArtDataAttr);
            return addArtDataAttr;
        };

        //清空文章列表
        function clearArtList() {
            $('#artListCenter').empty();
        };

        //清空電影類型列表
        function clearArtMoveList() {
            $('#artMovTypeList').empty();
        };

        //清空進階查詢
        function clearArtCompositeQuery() {
            $('#artAuthorForByCompositeQuery').val("");
            $('#artTimeForByCompositeQuery').val("");
            $('#artTitleByCompositeQuery').val("");
        };
    </script>
    <title>--SEENEMA ARTICLE--</title>
</head>

<body>
    <div class="container">
    
        <div class="row">
            <div class="col-12 col-md-3">
                <!-- 左區塊開始 -->
                <div id="artListLeft">
                    <!-- 錯誤列表 -->
                    <c:if test="${not empty errorMsgs}">
                        <font style="color:red"></font>
                        <ul>
                            <c:forEach var="message" items="${errorMsgs}">
                                <li style="color:red">${message}</li>
                            </c:forEach>
                        </ul>
                    </c:if>
                    <nav class="navbar navbar-expand-lg navbar-light" style="padding: 5px;">
                        <button class="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false"
                            aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarNavDropdown">
                            <ul class="nav flex-column">
                                <div id="artListLeftTop">
                                    <li>
                                        <!-- 新增文章 -->
                                        <form method="post" action="<%=request.getContextPath()%>/art/art.do">
                                            <input type="hidden" name="action" value="newArt">
                                            <label>
                                                <div id="newArtDiv">
                                                    <input type="image" id="newArt"
                                                        src="<%=request.getContextPath()%>/resource/images/newArtIcon.png"
                                                        alt="發文" title="發文">
                                                </div>
                                            </label>
                                        </form>
                                    </li>
                                    <li>
                                        <div class="input-group mb-3">
                                            <input type="text" id="artTitleByCompositeQuery" class="form-control"
                                                placeholder="搜尋標題" aria-label="Recipient's username"
                                                aria-describedby="findArtByTitleButton">
                                            <div class="input-group-append">
                                                <button class="btn btn-outline-secondary" type="button"
                                                    id="findArtByTitleButton">查詢</button>
                                                <!--                                               <input type="hidden" name="action" value="find_ByTitle"> -->
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <hr>
                                    </li>
                                    <li class="nav-item active ">
                                        <a class="nav-link"
                                            href="<%=request.getContextPath()%>/front-end/article/article.jsp">討論區首頁<span
                                                class="sr-only">(current)</span></a>
                                    </li>
                                    <li>
                                        <hr>
                                    </li>                                    
                                </div>
                                <div id="artListLeftDown">
                                    <li class="nav-item" style="font-size: 0.8em;">
                                        	電影類型
                                    </li>
                                    <div id="artMovTypeList">
                                        <li class="nav-item">
                                            <a class="nav-link" href="#">尚無分類</a>
                                        </li>
                                    </div>
                                    <li>
                                        <hr>
                                    </li>
                                    <li>
                                        <div id="accordion">
                                            <div class="card">
                                                <div class="card-header">
                                                    <a class="card-link" data-toggle="collapse" href="#collapseOne">
                                                        	進階查詢
                                                    </a>
                                                </div>
                                                <div id="collapseOne" class="collapse" data-parent="#accordion">
                                                    <div class="card-body">
                                                        <table>
                                                            <tr>
                                                                <td><input type="text" id="artAuthorForByCompositeQuery"
                                                                        name="artAuthor" placeholder="搜尋作者"></td>
                                                            </tr>
                                                            <tr>
                                                                <td><input type="text" id="artTimeForByCompositeQuery"
                                                                        name="artTime" placeholder="搜尋發表日期"></td>
                                                            </tr>
                                                            <tr>
                                                                <td><input id="findArtByCompositeQueryButton"
                                                                        class="btn btn-outline-secondary" type="button"
                                                                        value="查詢"></td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </div>
                            </ul>
                        </div>
                    </nav>
                </div>
                <!-- 左區塊結束 -->
            </div>
            <div class="col-12 col-md-9">
                <!-- 中間區塊開始 -->
                    <!-- ====================include ListArtQuery.jsp==================== -->
                    <jsp:include page="/front-end/article/ListArtQuery.jsp"></jsp:include>
                    <!-- ====================include ListArtQuery.jsp==================== -->
                <!-- 中間區塊結束 -->
            </div>
        </div>
    </div>

</html>