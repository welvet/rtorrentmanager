$(document).ready(function() {
    $('#torrentTable').dataTable({
        bPaginate:false,
        bInfo:false,
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

        ]
        //        "bProcessing": true,
        //        "bServerSide": true,
        //        "sAjaxSource": "/?do=tbl"
    });
});