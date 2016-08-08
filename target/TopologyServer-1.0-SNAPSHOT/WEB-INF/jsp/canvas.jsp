<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Network | Basic usage</title>
    <script src="/TopologyServer/resources/pace/pace.js" type="text/javascript"></script>
    <link href="/TopologyServer/resources/pace/themes/blue/pace-theme-loading-bar.css" rel="stylesheet">
    <!-- Include Twitter Bootstrap and jQuery: -->
    <link rel="stylesheet" href="/TopologyServer/resources/bootstrap-3.3.6-dist/css/bootstrap.min.css" type="text/css"/>
    <script src="/TopologyServer/resources/jQuery/jquery-2.1.4.js" type="text/javascript"></script>
    <script src="/TopologyServer/resources/bootstrap-3.3.6-dist/js/bootstrap.min.js" type="text/javascript"></script>

    <!-- Include the plugin's CSS and JS: -->
    <script src="/TopologyServer/resources/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js" type="text/javascript"></script>
    <link rel="stylesheet" href="/TopologyServer/resources/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css" type="text/css"/>

    <script src="/TopologyServer/resources/js/createCanvas.js" type="text/javascript"></script>
    <script src="/TopologyServer/resources/visJSdist/dist/vis.js" type="text/javascript"></script>

    <link rel="stylesheet" type="text/css" href="/TopologyServer/resources/css/filterStyle.css">
    <link href="/TopologyServer/resources/visJSdist/dist/vis.css" rel="stylesheet" type="text/css" />

    <!-- ================== BEGIN BASE CSS STYLE ================== -->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">
    <link href="/TopologyServer/resources/jquery-ui/themes/base/minified/jquery-ui.min.css" rel="stylesheet" />
    <link href="/TopologyServer/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/TopologyServer/resources/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
    <link href="/TopologyServer/resources/animate.min.css" rel="stylesheet" />
    <link href="/TopologyServer/resources/style.min.css" rel="stylesheet" />
    <link href="/TopologyServer/resources/style-responsive.min.css" rel="stylesheet" />
    <link href="/TopologyServer/resources/theme/default.css" rel="stylesheet" id="theme" />
    <!-- ================== END BASE CSS STYLE ================== -->
</head>
<body>
<!-- begin #page-container -->
    <div id="page-container" class="fade page-sidebar-fixed page-header-fixed">
    <!-- begin #header -->
    <div id="header" class="header navbar navbar-inverse navbar-fixed-top">
        <!-- begin container-fluid -->
        <div class="container-fluid">
            <!-- begin mobile sidebar expand / collapse button -->
            <div class="navbar-header">
                <a href="/TopologyServer/canvas" class="navbar-brand" style="background-color:#2d353c; padding: 0px 0px 0px 0px; margin-right: 0px;">
                    <img src="/TopologyServer/resources/img/transcorrelata.png" style="padding: 9px 0px 0px 15px; width:150px;">
                </a>
                <%--<button type="button" class="navbar-toggle" data-click="sidebar-toggled">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>--%>
            </div>
            <!-- end mobile sidebar expand / collapse button -->

            <!-- begin header navigation right -->
            <%--<ul class="nav navbar-nav navbar-right">
                <li>
                    <form class="navbar-form full-width">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Enter keyword" />
                            <button type="submit" class="btn btn-search"><i class="fa fa-search"></i></button>
                        </div>
                    </form>
                </li>
                <li class="dropdown">
                    <a href="javascript:;" data-toggle="dropdown" class="dropdown-toggle f-s-14">
                        <i class="fa fa-bell-o"></i>
                        <span class="label">5</span>
                    </a>
                    <ul class="dropdown-menu media-list pull-right animated fadeInDown">
                        <li class="dropdown-header">Notifications (5)</li>
                        <li class="media">
                            <a href="javascript:;">
                                <div class="media-left"><i class="fa fa-bug media-object bg-red"></i></div>
                                <div class="media-body">
                                    <h6 class="media-heading">Server Error Reports</h6>
                                    <div class="text-muted f-s-11">3 minutes ago</div>
                                </div>
                            </a>
                        </li>
                        <li class="media">
                            <a href="javascript:;">
                                <div class="media-left"><img src="assets/img/user-1.jpg" class="media-object" alt="" /></div>
                                <div class="media-body">
                                    <h6 class="media-heading">John Smith</h6>
                                    <p>Quisque pulvinar tellus sit amet sem scelerisque tincidunt.</p>
                                    <div class="text-muted f-s-11">25 minutes ago</div>
                                </div>
                            </a>
                        </li>
                        <li class="media">
                            <a href="javascript:;">
                                <div class="media-left"><img src="assets/img/user-2.jpg" class="media-object" alt="" /></div>
                                <div class="media-body">
                                    <h6 class="media-heading">Olivia</h6>
                                    <p>Quisque pulvinar tellus sit amet sem scelerisque tincidunt.</p>
                                    <div class="text-muted f-s-11">35 minutes ago</div>
                                </div>
                            </a>
                        </li>
                        <li class="media">
                            <a href="javascript:;">
                                <div class="media-left"><i class="fa fa-plus media-object bg-green"></i></div>
                                <div class="media-body">
                                    <h6 class="media-heading"> New User Registered</h6>
                                    <div class="text-muted f-s-11">1 hour ago</div>
                                </div>
                            </a>
                        </li>
                        <li class="media">
                            <a href="javascript:;">
                                <div class="media-left"><i class="fa fa-envelope media-object bg-blue"></i></div>
                                <div class="media-body">
                                    <h6 class="media-heading"> New Email From John</h6>
                                    <div class="text-muted f-s-11">2 hour ago</div>
                                </div>
                            </a>
                        </li>
                        <li class="dropdown-footer text-center">
                            <a href="javascript:;">View more</a>
                        </li>
                    </ul>
                </li>
                <li class="dropdown navbar-user">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="assets/img/user-13.jpg" alt="" />
                        <span class="hidden-xs">Adam Schwartz</span> <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu animated fadeInLeft">
                        <li class="arrow"></li>
                        <li><a href="javascript:;">Edit Profile</a></li>
                        <li><a href="javascript:;"><span class="badge badge-danger pull-right">2</span> Inbox</a></li>
                        <li><a href="javascript:;">Calendar</a></li>
                        <li><a href="javascript:;">Setting</a></li>
                        <li class="divider"></li>
                        <li><a href="javascript:;">Log Out</a></li>
                    </ul>
                </li>
            </ul>--%>
            <!-- end header navigation right -->
        </div>
        <!-- end container-fluid -->
    </div>
    <!-- end #header -->
    <!-- begin #sidebar -->
    <div id="sidebar" class="sidebar">
        <%--<div id="filter" style="visibility: hidden">--%>
            <ul class="nav">
                <li class="nav-header">Navigation</li>
                <%--<li>
                    <a href="javascript:;">
                        <i class="fa fa-laptop"></i>
                        <span>HOST's</span>

                    </a>
                </li>--%>
                <select id="showSetHostName" multiple="multiple" name="HOST's"></select>
                <br/>

                <%--<li>
                    <a href="javascript:;">
                        <i class="fa fa-laptop"></i>
                        <span>SWITCH's</span>

                    </a>
                </li>--%>
                <select id="showSetSwitch" multiple="multiple" name="SWITCH's"></select>
                <br/>

                <%--<li>
                    <a href="javascript:;">
                        <i class="fa fa-laptop"></i>
                        <span>ArrayID's</span>

                    </a>
                </li>--%>
                <select id="showSetArrayID" multiple="multiple" name="ArrayID's"></select>
                <br/>

                <%--<li>
                    <a href="javascript:;">
                        <i class="fa fa-laptop"></i>
                        <span>ZONE's</span>

                    </a>
                </li>--%>
                <select id="showKeySetNodesByZoneNameMap" multiple="multiple" name="ZONE's"></select>
                <br/>


            </ul>

            <br/>
            <input type="button" id="btnSelected" value="Draw" class="btn btn-success"/>
            <input type="button" id="resetAll" value="Destroy the network" class="btn btn-danger">


    </div>
    <!-- end #sidebar -->
    <!-- begin #content -->
    <div id="content" class="content">

        <div id="mynetwork" style="visibility: hidden"></div>
        <div id="event"></div>

    </div>
    <!-- end #content -->
    <%--<div id="content" class="row">


    </div>--%>
</div>
    <!-- ================== BEGIN BASE JS ================== -->
    <script src="assets/plugins/jquery/jquery-1.9.1.min.js"></script>
    <script src="assets/plugins/jquery/jquery-migrate-1.1.0.min.js"></script>
    <script src="assets/plugins/jquery-ui/ui/minified/jquery-ui.min.js"></script>
    <script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
    <!--[if lt IE 9]>
    <script src="assets/crossbrowserjs/html5shiv.js"></script>
    <script src="assets/crossbrowserjs/respond.min.js"></script>
    <script src="assets/crossbrowserjs/excanvas.min.js"></script>
    <![endif]-->
    <script src="assets/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="assets/plugins/jquery-cookie/jquery.cookie.js"></script>
    <!-- ================== END BASE JS ================== -->

    <!-- ================== BEGIN PAGE LEVEL JS ================== -->
    <%--<script src="assets/plugins/morris/raphael.min.js"></script>
    <script src="assets/plugins/morris/morris.js"></script>
    <script src="assets/plugins/jquery-jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
    <script src="assets/plugins/jquery-jvectormap/jquery-jvectormap-world-merc-en.js"></script>
    <script src="assets/plugins/bootstrap-calendar/js/bootstrap_calendar.min.js"></script>
    <script src="assets/plugins/gritter/js/jquery.gritter.js"></script>
    <script src="assets/js/dashboard-v2.min.js"></script>--%>
    <script src="/TopologyServer/resources/js/apps.js"></script>
    <!-- ================== END PAGE LEVEL JS ================== -->

    <script>
        $(document).ready(function() {
            App.init();
        });
    </script>
</body>
</html>

