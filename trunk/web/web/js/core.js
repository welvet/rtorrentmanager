//инициализация статических объектов
var torrents = [];
var selectedTorrent;
var oTable;
var refresh = false;

//инициализация таблицы рторрента
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
        "sAjaxSource": "/torrent/list/"
    });

    //initialize buttons
    $("#torrentTable_filter").prepend($("#tableButtons"));
}

function initializeButtons(parent) {
    if (parent != null)
        parent = parent + " ";
    else
        parent = "";
    //создаем кнопки
    $(parent + ".button").each(function() {
        $(this).button();
    });
    //создаем кнопку отмены для всех диалогов
    $(parent + ".closeButton").each(function() {
        $(this).click(function() {
            $(this).parents(".dialog").dialog("close");
            return false;
        });
    });
    //создаем кнопку для диалога отправки настроек
    $(parent + ".submit").click(function() {
        var form = $(this).parents(".settingsForm");
        //отправляем форму
        $.ajax({url: form.attr("action"), type: "POST", data: form.serialize()});
        //закрываем диалог
        $(this).parents(".dialog").dialog("close");
        return false;
    });
}

//каллбек для обработки полей
function rowCallback(nRow, aData, iDisplayIndex) {
    //добавляем элемент в массив
    torrents.push(nRow);
    $(nRow).contextMenu({
        menu: 'contextMenu'
    },
            function(action, el, pos) {
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
    //устанавливаем css класс для колонки с именем
    var name = $(nRow).children().get(0);
    $(name).addClass("titleTd");
    //устанавливаем изображение для статуса торрента
    var img = $(nRow).children().get(1);
    $(img).html("<img src=\"images/" + aData[2] + ".png\"/>");
    //восстанавливаем выделеный торрент после обновления 
    if ((aData[0] == selectedTorrent) && (selectedTorrent != undefined))
        $(nRow).addClass("rowSelected");
    return nRow;
}

function reloadTable() {
    torrents = [];
    oTable.fnReloadAjax(oTable.fnSettings());
    $(oTable).everyTime(10000, "table", function() {
        //обнуляем массив с торрентами и обновляем его с сервера
        torrents = [];
        oTable.fnReloadAjax(oTable.fnSettings());
    });
}

function stopReload() {
    $(oTable).stopTime("table");
}

//эта функция будет вызываться как из контекстного меню, так и через "кнопки управления"
function doAction(action, hash) {
    $.get("/torrent/?action=" + action + "&hash=" + hash, {}, openDialog);
}

function loadDialog(name) {
    $.get("/settings/?dialog=" + name, {}, openDialog);
}

function openDialog(data) {
    $("#torrentDialog").html(data);
    //проверяем, нужно ли создавать диалог
    if ($("#torrentDialog #needUserNotice").val() == "true") {
        //инициализируем кнопки
        initializeButtons("#torrentDialog");
        //показываем диалог
        $("#torrentDialog .dialog").show();
        //открываем диалог
        $("#torrentDialog").dialog({ modal: false, resizable: false,
            draggable: true, width: 500, height: 400 });
        //устанавливаем заголовок
        $("#ui-dialog-title-torrentDialog").html($("#torrentDialog #title").val());
    }
}

function initializeMenu() {
    $("#mainMenu li a").each(function() {
        if ($(this).attr("dialog") != undefined) {
            $(this).click(function() {
                loadDialog($(this).attr("dialog"));
            });
        }
        if ($(this).attr("action") != undefined) {
            $(this).click(function() {
                doSimpleAction($(this).attr("action"))
            });
        }
    });
}

function afterAction(data) {
    if (data.length > 0)
    {
        $("#simpleAction").html(data);
        if ($("#simpleAction #actionDialog") != null)
            $("#simpleAction #actionDialog").dialog({ modal: false, resizable: false,
                draggable: true, width: 500, height: 400 });
    }
}

function doSimpleAction(name) {
    $.get("/action/?action=" + name, {}, afterAction);
}

function initializeLog() {
    $("#log #label a").click(function() {
        doSimpleAction("clearLog");
        $("#logArea").empty()
    });
}

function initializeTableButtons() {
    $("#refresh").click(function() {
        if (refresh) {
            stopReload();
            $(this).removeClass("refreshable");
            refresh = false;
        } else {
            reloadTable();
            $(this).addClass("refreshable");
            refresh = true;
        }
    })
}

//public static void main(null)
$().ready(function() {
    initializeMenu();
    initializeLog();
    initializeTable();
    initializeTableButtons();
});