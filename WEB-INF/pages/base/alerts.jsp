<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div id="topPagination" class="align-center" style="margin-bottom: 10px; position: relative"></div>

<div id="alerts-body"></div>

<div id="bottomPagination" class="align-center" style="margin-top: 10px; position: relative"></div>


<script type="text/javascript">
    setPageTitle('ALERTS');

    var totalPages = ${numberOfPages};
    var paginationHtml;
    loadAlertsBody(1);

    function loadAlertsBody(pageNumber)
    {
        loadAlertsContainerController(pageNumber);
        paginationHtml = createPagination(pageNumber)
        $('#topPagination').html(paginationHtml + '<div id="clearAlertsButtonDiv" style="position: absolute; right:0; top: 20px;"><button class="btn btn-primary" style="height:34px" onclick="cleanAlerts()">Clear</button></div>');
        $('#bottomPagination').html(paginationHtml);
    }


    function cleanAlerts()
    {
        var buttonDiv = $("#clearAlertsButtonDiv");
        var buttonHtml = buttonDiv.html();
        buttonDiv.html(getLoadingAnimation('height: 15px', 'primary', 5));

        $.ajax({
            type: 'POST',
            url: '/alerts_controller/clear'
        }).done(function ()
        {
            buttonDiv.html(buttonHtml);
            loadAlertsBody(1);
        }).fail(function ()
        {
        });
    }


    function createPagination(currentPage)
    {
        var html = '<ul class="pagination">';
        var prevPage = currentPage - 1;
        var nextPage = currentPage + 1;

        //Previous page
        html += currentPage > 1 ? createItemHtml('&lsaquo;', prevPage) : createDisabledItemHtml('&lsaquo;');
        //First page
        html += currentPage == 1 ? createActiveItemHtml(1) : createItemHtml(1, 1);

        if (prevPage > 2)
        {
            html += createDisabledItemHtml('...');
        }

        //Previous page
        if (prevPage > 1) {
            html += createItemHtml(prevPage, prevPage);
        }

        //Current page
        if (currentPage != 1 && currentPage != totalPages) {
            html += createActiveItemHtml(currentPage);
        }

        //Next page
        if (nextPage < totalPages) {
            html += createItemHtml(nextPage, nextPage);
        }

        if (nextPage < totalPages - 1)
        {
            html += createDisabledItemHtml('...');
        }

        //Last page
        if (totalPages > 1)
        {
            html += currentPage == totalPages ? createActiveItemHtml(totalPages)
                    : createItemHtml(totalPages, totalPages);
        }

        //Next page
        html += currentPage < totalPages ? createItemHtml('&rsaquo;', nextPage) : createDisabledItemHtml('&rsaquo;');

        html += '</ul>';
        return html;
    }


    function createItemHtml(text, pageNumber)
    {
        return '<li class="page-item"><a ' + 'onclick=loadAlertsBody(' + pageNumber + ')>' + text + '</a></li>';
    }


    function createDisabledItemHtml(text)
    {
        return '<li class="page-item disabled"><a>' + text + '</a></li>';
    }


    function createActiveItemHtml(text)
    {
        return '<li class="page-item active"><a>' + text + '</a></li>';
    }
</script>

