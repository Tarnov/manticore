<div style="margin-top: 15px;margin-bottom: 15px;">
    <div class="panel panel-default">
        <div class="panel-heading">
            <div id="current-issues-ratio-update-button" class="btn-group" style="height: 20px">
                <button type="button" class="btn btn-xs btn-update btn-primary"
                        onclick="loadBlockContainer('current-issues-ratio', '/statistics_controller/current_issues_ratio', true, '', '', '')">
                    <i class="fa fa-refresh"></i> Update
                </button>
            </div>
            <div id="current-issues-ratio-update-loading" class="btn-group" style="height: 20px"></div>
        </div>
        <div id="current-issues-ratio-body" class="panel-body"></div>
    </div>
</div>


<!--
<div role="tabpanel" style="margin-top: 15px;">
    <ul class="nav nav-tabs nav-justified" role="tablist">
        <li role="presentation" class="active"><a class="m-label" href="#statistics-issues-ratio"
                                                  aria-controls="statistics-issues-ratio" role="tab"
                                                  data-toggle="tab">Issues ratio statistics</a></li>
        <li role="presentation"><a class="m-label" href="#commands-statistics" aria-controls="commands-statistics"
                                   role="tab" data-toggle="tab">Commands statistics</a></li>
    </ul>

    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="statistics-issues-ratio">
            <div class="row" style="margin-bottom: 15px;">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div id="current-issues-ratio-update-button" class="btn-group" style="height: 20px">
                                <button type="button" class="btn btn-xs btn-update btn-primary"
                                        onclick="loadBlockContainer('current-issues-ratio', '/statistics_controller/current_issues_ratio', true, '', '', '')">
                                    <i class="fa fa-refresh"></i> Update
                                </button>
                            </div>
                            <div id="current-issues-ratio-update-loading" class="btn-group" style="height: 20px"></div>
                        </div>
                        <div id="current-issues-ratio-body" class="panel-body"></div>
                    </div>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="commands-statistics">
            <div class="row" style="margin-bottom: 15px;">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div id="commands-statistics-update-button" class="btn-group" style="height: 20px">
                                <button type="button" class="btn btn-xs btn-update btn-primary"
                                        onclick="loadBlockContainer('commands-statistics', '/statistics_controller/commands_statistics', true, '', '', '')">
                                    <i class="fa fa-refresh"></i> Update
                                </button>
                            </div>
                            <div id="commands-statistics-update-loading" class="btn-group" style="height: 20px"></div>
                        </div>
                        <div id="commands-statistics-body" class="panel-body"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
-->

<script type="text/javascript">

    /* Set page title */
    setPageTitle('STATISTICS');

    /* Preload internal containers */
    $(document).ready(function () {
        preLoadBlockContainer("/statistics_controller");
    });

</script>