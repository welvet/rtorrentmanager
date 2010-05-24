$(document).ready(function() {
    //инициализация таблицы рторрента
    //todo потом эта страница будет загружаться аяксом
    function initializeTable() {
        $('#torrentTable').dataTable({
            bPaginate:false,
            bInfo:false,
            bJQueryUI: true,
            bLengthChange: false,
            aoColumns: [
                null,//            <th>Имя</th>
                {bVisible:false},//            <th>hash</th>
                null,//            <th>Статус</th>
                null,//            <th>&nbsp;</th>
                null,//            <th>Скачано</th>
                null,//            <th>Размер</th>
                null,//            <th>Ратио</th>
                null,//            <th>Пиры</th>
                null//            <th>Сиды</th>

            ],
            "bProcessing": true,
            "sAjaxSource": "mockSource.html"
        });
    }

    //public static void main(null)
    initializeTable();
    $("#dialogBody").tabs();
    $("#dialog").dialog({ modal: false, resizable: false,
        draggable: true, width: 800, height: 500 });
    $.widget("#leftMenu");
});