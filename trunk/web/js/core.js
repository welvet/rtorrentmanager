//инициализация статических объектов
var torrents = [];
var selectedTorrent;
var oTable;

//инициализация таблицы рторрента
//todo потом эта страница будет загружаться аяксом
function initializeTable() {
    oTable = $('#torrentTable').dataTable({
        bPaginate:false,
        bInfo:false,
        bJQueryUI: true,
        aoColumns: [
            //устанавливаем поля
            {bVisible: false},
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        ],
        bLengthChange: false,
        fnRowCallback: rowCallback, //задаем каллбек для контекстного меню
        "bProcessing": true,
        "sAjaxSource": "mockSource.html"
    });
}

function initializeButtons() {
    //создаем кнопки
    //создаем кнопку сабмит для всех диалогов
    $(".button").each(function() {
        $(this).button();
    })
    //создаем кнопку отмены для всех диалогов
    $(".closeButton").each(function() {
        $(this).click(function() {
            $(this).parents(".dialog").dialog("close");
        })
    })
}

//каллбек для обработки полей
function rowCallback(nRow, aData, iDisplayIndex) {
    //добавляем элемент в массив
    torrents.push(nRow);
    $(nRow).contextMenu({
        menu: 'contextMenu'
    },
            function(action, el, pos) {
                //тут вероятно будет кейс на функции. функция будет вида doStart(hash);
                if (selectedTorrent != undefined) {
                    doAction(action, selectedTorrent);
                }
                else {
                    alert("Выберите торрент");
                }
            });
    //устанавливаем аттрибут со значением хеша
    $(nRow).attr("hash", aData[0]);
    //устанавливаем выделение
    $(nRow).mousedown(function() {
        for (var i = 0; i <= torrents.length; i++) {
            var sel = torrents[i];
            $(sel).removeClass("rowSelected");
        }
        $(this).addClass("rowSelected");
        selectedTorrent = $(this).attr("hash");
    });
    var img = $(nRow).children().get(1);
    //устанавливаем изображение для статуса торрента
    $(img).html("<img src=\"images/" + aData[2] + ".jpg\"/>");
    //восстанавливаем выделеный торрент после обновления todo в будущем работаем только с selectedTorrent
    if ((aData[0] == selectedTorrent) && (selectedTorrent != undefined))
        $(nRow).addClass("rowSelected");
    return nRow;
}

//открыть диалог с настройками
function openSettingsDialog() {
    $("#settingsDialogBody").tabs();
    $("#settingsDialog").dialog({ modal: false, resizable: false,
        draggable: true, width: 800, height: 500 });
}

//открыть диалог с настройками торрента
function openTorrentSettingsDialog() {
    $("#torrentDialog").dialog({ modal: false, resizable: false,
        draggable: true, width: 500, height: 400 });
}

//открыть диалог с логами ошибок todo вероятно он будет модальным и требовать поддтверждения от пользователя

function reloadTable() {
    //todo обновляем таблицу с торрентом каждые 10000 мсекунд, вероятно стоит сделать это значение настраиваемым
    oTable.everyTime(10000, "table", function() {
        //обнуляем массив с торрентами и обновляем его с сервера
        torrents = [];
        oTable.fnReloadAjax(oTable.fnSettings());
    });
}

//эта функция будет вызываться как из контекстного меню, так и через "кнопки управления"
function doAction(action, hash) {
    //        $.getJSON("/"+action+"/"+hash+"/", {}, function(json) { //запрашиваем сервер http://localhost/action/hash/
    $.getJSON("mockJSON.html", {}, function(json) {
        if (json.needUserNotice == "true") { //сервер отдает все параметры в ""
            //открываем диалог
            var dialog = openTorrentSettingsDialog();
            //выставляем параметры
        }
    }); //
}

//public static void main(null)
$(document).ready(function() {
    initializeTable();
    initializeButtons();
    reloadTable();
});