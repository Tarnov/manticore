<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="row" style="margin-top: 15px; margin-bottom: 15px;">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <span class="primary-color m-label panel-header"><i class="fa fa-users"></i> Post json-payload</span>
                </div>
            </div>
            <div id="stash_post_receive_hook-body" class="panel-body"></div>
        </div>
    </div>
</div>

<!-- Modal -->
<div id="json" class="modal dialog" tabindex="-1" data-backdrop="static">
    <div class="modal-header">
        <span class="modal-title m-label"><i class="fa fa-pencil"></i> Post json-payload</span>
    </div>
    <div class="modal-body">
        <table class="width-full margin-auto">
            <tr>
                <td>
                    <div style="padding: 5px">
                        <input id="json-string" class="form-control text-center m-label" type="text">
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div class="modal-footer">
        <button id="close" type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
        <button id="post" type="button" class="btn btn-primary" onclick="postJson();"><i class="fa fa-check"></i> Post
        </button>
    </div>
</div>
<!-- /.modal -->

<script type="text/javascript">

    loadBlockContainer('stash_post_receive_hook', '/remote_controller/stash_post_receive_hook', false, '', '', '');

    /* Add by Enter */
    $('#json-string').keypress(function (event) {
        if (event.which == 13) {
            $("#post").click();
        }
    });

</script>
