<%@ page language="java" %>
<%@ page contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Rtorrent manager</title>
    <script type="text/javascript" language="javascript" src="js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" language="javascript" src="js/jquery-ui-1.8.1.custom.min.js"></script>
    <script type="text/javascript" language="javascript" src="js/jquery.timers-1.2.js"></script>
    <script type="text/javascript" language="javascript" src="js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" language="javascript" src="js/jquery.contextMenu.js"></script>
    <script type="text/javascript" language="javascript" src="js/jsddm.js"></script>
    <script type="text/javascript" language="javascript" src="js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript" src="js/core.js"></script>
    <style type="text/css">
        @import "css/jquery.contextMenu.css";
        @import "css/jquery-ui-1.8.1.custom.css";
        @import "css/jsddm.css";
        @import "css/core.css";

    </style>
</head>
<body>
<div id="wrapper">
    <%--блок меню настроек--%>
    <div id="mainMenu">
        <ul id="jsddm">
            <li><a href="#">Общее</a>
                <ul>
                    <li><a href="#" action="checkAll">Проверить все</a></li>
                    <li><a href="#" action="shutdownApp">Выключить rmanager</a></li>
                </ul>
            </li>
            <li><a href="#">Настройки</a>
                <ul>
                    <li><a href="#" dialog="rtorrent">Rtorrent</a></li>
                    <li><a href="#" dialog="scheduler">Планировщик</a></li>
                    <li><a href="#" dialog="server">Web server</a></li>
                    <li><a href="#" dialog="notice">Уведомления</a></li>
                </ul>
            </li>
            <li><a href="#">Трекеры</a>
                <ul>
                    <li><a href="#" dialog="rutracker">Rutracker</a></li>
                    <li><a href="#" dialog="lostfilm">LostFilm</a></li>
                </ul>
            </li>
            <li><a href="#" action="help">Help</a></li>
            <li><a href="#" action="about">About</a></li>
        </ul>
    </div>
    <!--блок с таблицей-->
    <div id="tableConatainer">
        <table id="torrentTable">
            <thead>
            <tr>
                <th>hash</th>
                <th class="titleTd">Имя</th>
                <th>Статус</th>
                <th>&nbsp;</th>
                <th class="titleSize">Скачано</th>
                <th class="titleSize">Размер</th>
                <th>Обновлен</th>
                <th>Ратио</th>
                <th>Пиры</th>
                <th>Всего</th>
            </tr>
            </thead>
        </table>
    </div>
    <!--блок контекстного меню-->
    <ul id="contextMenu">
        <li><a href="#start">Запустить</a></li>
        <li><a href="#stop">Остановить</a></li>
        <li><a href="#remove">Удалить</a></li>
        <li><a href="#properties">Свойства</a></li>
    </ul>
    <!--блок диалога с настройками торрента-->
    <div id="torrentDialog" class="dialog">
    </div>
    <br>

    <div id="log" class="fg-toolbar ui-widget-header ui-corner-tl ui-corner-tr ui-helper-clearfix">
        <span id="label">Логи <a href="#">(очистить)</a></span>
        <textarea id="logArea" class="ui-widget" rows="7" cols="117" disabled="disabled">
            <%@ include file="log.jsp" %>
        </textarea>
    </div>
    <div id="simpleAction" class="dialog">
    </div>
    <div id="tableButtons">
        <img id="addTorrent" src="/images/add.png"/>
        <img id="refresh" src="/images/refresh.png"/>
    </div>
</div>
</body>
</html>