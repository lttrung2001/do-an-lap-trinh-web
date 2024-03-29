<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header">
    <nav class="container">
        <a  id="logo" href="home.htm">
            <img src="<c:url value='/resources/my_png/Logo-byOanh.png'/>" alt="CAB Shop">				
        </a>
        
        <ul id="main-menu">
            <li><a class="style-menu " href="home.htm" >HOME</a></li>
            <li class="Option"><a class="style-menu" href="top.htm">TOP</a>
                <ul class="top-menu sub-menu ">
                	<c:forEach var="pt" items="${productTypeTop }">
                		<li><a href="top/${pt.proTypeId }.htm" onclick="notification_option()">${pt.proTypeName }</a></li>
                	</c:forEach>
                </ul>
            </li>
            
            <li class="Option"><a class="style-menu" href="bot.htm" >BOT</a>
                <ul class="bot-menu sub-menu">
	                <c:forEach var="pt" items="${productTypeBot }">
                		<li><a href="bot/${pt.proTypeId }.htm" onclick="notification_option()">${pt.proTypeName }</a></li>
                	</c:forEach>
                </ul>
            </li>
            <li class="Option"><a class="style-menu" href="new.htm">NEW</a></li>
            <!-- <li><a href="#contact" class="style-menu">CONTACT</a></li> -->
        </ul>
        <div id="header-icon">
            <!-- <span id="icon-account"> <ion-icon name="person-circle"></ion-icon></span> -->
            <c:if test="${sessionScope.account == null }">
            <div class="icon-button-header">
                <button id="icon-person-btn" ><ion-icon name="person-outline"></ion-icon></button>
                <ul id="login_signup" class="hideAccount">
                    <li><a href="login.htm" id="Login">Log in</a></li>
					<li><a href="register.htm" id="Signup">Sign up</a></li>
                </ul>
            </div>
            </c:if>
            <c:if test="${sessionScope.account != null }">
            <div class="iconLogged">
                <button onclick="onMenuLogged()"><ion-icon name="person"></ion-icon></button>
                <ul class="menuLogged hide">
                    <li class="changeInfor"><a href="account/info.htm">Thay đổi thông tin</a></li>
                    <li class="history"><a href="account/history.htm">Lịch sử</a></li>
                    <li class="logOut"><a href="logout.htm">Đăng xuất</a></li>
                </ul>
            </div>
            </c:if>
            <div class="icon-button-header">
                <button id="icon-search-btn" ><ion-icon name="search-outline" ></ion-icon></button>
                <div class="search-input hide" >
                    <form class="search-form" action="${part }.htm">
                        <input autocomplete="off" class="search-input-box" type="text" placeholder="Search.." name="search">
                        <button type="submit"><i class="fa fa-search"></i></button>
                    </form>
                </div>
            </div>
            <div class="icon-button-header iconCart">
                <button id="icon-bag-btn"><a href="checkout.htm"><ion-icon name="cart-outline"></ion-icon></a></button>
            </div>
        </div>
    </nav>
<!--     <script type="text/javascript" src="/resources/my_js/main_js.js" ></script> -->
</div>
<!-- Dynamic background -->
    <div class="dynamic_background">
        <div class="b-left"></div>
        <div class="b-right"></div>
    </div>
    
<!-- End of Dynamic background -->