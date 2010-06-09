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
    <script type="text/javascript" language="javascript" src="js/core.js"></script>
    <style type="text/css">
        @import "css/jquery.contextMenu.css";
        @import "css/jquery-ui-1.8.1.custom.css";
        @import "css/core.css";

    </style>
</head>
<body>
<!--блок с таблицей-->
<div id="tableConatainer">
    <table id="torrentTable">
        <thead>
        <tr>
            <th>hash</th>
            <th class="titleTd">Имя</th>
            <th>Статус</th>
            <th>&nbsp;</th>
            <th class="titleSize" >Скачано</th>
            <th class="titleSize" >Размер</th>
            <th>Ратио</th>
            <th>Пиры</th>
            <th>Сиды</th>
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
<!--блок диалога с настройками-->
<!--дебаг-->
<button onclick="doAction('settings', null);">Remove me</button>
<!--/дебаг-->
</body>
</html>