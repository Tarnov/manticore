<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="row" style="margin-top: 15px; margin-bottom: 15px;">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-left">
                    <div id="test-rank-update-button" class="btn-group" style="height: 20px">
                        <button type="button" class="btn btn-xs btn-update btn-primary"
                                onclick="loadBlockContainer('test-rank', '/administration_controller/test_rank', false, '', '', '')">
                            <i class="fa fa-refresh"></i> Refresh
                        </button>
                    </div>
                    <div id="test-rank-update-loading" class="btn-group" style="height: 20px"></div>
                </div>
                <div class="pull-right">
                    <span class="primary-color m-label panel-header"><i class="fa fa-balance-scale"></i> Test Rank Automation</span>
                </div>
            </div>
            <div id="test-rank-body" class="panel-body"></div>
        </div>
    </div>
</div>

<script type="text/javascript">

    /* Set page title */
    var title = 'ADMINISTRATION / TEST RANK AUTOMATION';
    setPageTitle(title);

    loadBlockContainer('test-rank', '/administration_controller/test_rank', false, '', '', '');

</script>
