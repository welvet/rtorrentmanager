<%@ page language="java" %>
<%@ page contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<div id="dialogPropertiesDiv" widthA="255" heightA="155"></div>
<div id="actionDialog" title="Добавить торрент">
    File: <input id="torrentFile" name="torrentFile" type="file"/>

    <div id="buttons">
        <button id="uploadButton">ok</button>
    </div>
    <script type="text/javascript">
        $("#uploadButton").button();
        $("#uploadButton").click(function() {
            $.ajaxFileUpload({
                url:'/upload/',
                secureuri:false,
                fileElementId:'torrentFile',
                dataType: 'json',
                success: function (data, status) {

                },
                error: function (data, status, e) {
                    //закрываем диалог кнопкой
                    $(".ui-dialog-titlebar-close").click();
                }
            });
        });
    </script>
</div>