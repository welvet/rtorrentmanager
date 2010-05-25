$(document).ready(function() {
    //инициализация статических объектов
    var selectedTorrents = [];
    //инициализация таблицы рторрента
    //todo потом эта страница будет загружаться аяксом
    function initializeTable() {
        $('#torrentTable').dataTable({
            bPaginate:false,
            bInfo:false,
            bJQueryUI: true,
            aoColumns: [      //устанавливаем поля
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
    //каллбек для обработки полей
    function rowCallback(nRow, aData, iDisplayIndex) {
        //добавляем элемент в массив
        selectedTorrents.push(nRow);
        $(nRow).contextMenu({
            menu: 'contextMenu'
        },
                function(action, el, pos) {
                    //тут вероятно будет кейс на функции. функция будет вида doStart(hash);
                    alert(action + "(" + $(el).attr("hash") + ")");
                });
        //устанавливаем аттрибут со значением хеша
        $(nRow).attr("hash", aData[0]);
        //устанавливаем выделение
        $(nRow).mousedown(function() {
            for (var i = 0; i <= selectedTorrents.length; i++) {
                var sel = selectedTorrents[i];
                $(sel).removeClass("rowSelected");
            }
            $(this).addClass("rowSelected");
        });
        var img = $(nRow).children().get(1);
        //устанавливаем изображение для статуса торрента
        $(img).html("<img src=\"images/"+aData[2]+".jpg\"/>");
        return nRow;
    }

    //открыть диалог с настройками
    function openSettingsDialog() {
        $("#dialogBody").tabs();
        $("#dialog").dialog({ modal: false, resizable: false,
            draggable: true, width: 800, height: 500 });
        $.widget("#leftMenu");
    }


    //public static void main(null)
    initializeTable();
});