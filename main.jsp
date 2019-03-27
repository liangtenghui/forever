<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="common/head.jsp"%>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar user panel -->
		<div class="user-panel">
			<div class="pull-left image">
			</div>
		</div>
		<!-- /.search form -->
		<!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu">
			<li class="header">导航栏</li>
			<c:if test="${not empty resourceList}">

				<c:forEach var="res" items="${resourceList}" varStatus="status">
				
						<c:if test="${status.index==0}">
						<li class="active treeview">
						</c:if>
						<c:if test="${status.index!=0}">
						<li class="treeview">
						</c:if>
					
					<a href="#"> <i class="${res.icoUrl}"></i> <span>${res.name}</span>
						<i class="fa fa-angle-left pull-right"></i>
					</a>
					<ul class="treeview-menu">
						
						<c:if test="${not empty res.children}">
							<c:forEach var="children" items="${res.children}"
								varStatus="childrenStatus">
								
								
								<c:if test="${childrenStatus.index==0}">
								<li class="active treeview">
								</c:if>
								<c:if test="${childrenStatus.index!=0}">
								<li class="treeview">
								</c:if>
								
								<a target="iframepage" class="clickEvent"
									href="${children.url}"><i class="${children.icoUrl}"></i>
										${children.name}
								<c:if test="${not empty children.children}">
								<i class="fa fa-angle-left pull-right"></i>		
								</c:if>
								</a>
									
										<c:if test="${not empty children.children}">
										<ul class="treeview-menu">
											<c:forEach var="son" items="${children.children}"
												varStatus="sonStatus">
												<li class="active"><a target="iframepage" class="clickEvent"
													href="${son.url}"><i class="${son.icoUrl}"></i>
														${son.name}</a></li>	
										    </c:forEach>
										</ul>
										</c:if>
									
									</li>
							</c:forEach>
						</c:if>
					</ul>
					</li>
				</c:forEach>
			</c:if>
		</ul>
	</section>
	<!-- /.sidebar -->
</aside>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<c:forEach var="res" begin="0" end="0" items="${resourceList}"
		varStatus="status">
		<c:forEach var="children" begin="0" end="0" items="${res.children}"
			varStatus="status">
			<iframe src="${children.url}" style="min-height:620px" id="iframepage" name="iframepage" onload = 'document.getElementById("iframepage").style.height = document.documentElement.clientHeight+"px"'
				width="100%" frameBorder=0 scrolling="auto"></iframe>
		</c:forEach>
	</c:forEach>
</div>
<!-- /.content-wrapper -->
<footer class="main-footer">
</footer>
<!-- jQuery 2.1.4 -->
<script src="plugins/jQuery/jQuery-2.1.4.min.js">
	
</script>
<!-- Bootstrap 3.3.5 -->
<script src="bootstrap/js/bootstrap.min.js">
	
</script>
<!-- Sparkline -->
<script src="plugins/sparkline/jquery.sparkline.min.js">
	
</script>
<!-- jvectormap -->
<script src="plugins/jvectormap/jquery-jvectormap-1.2.2.min.js">
	
</script>
<script src="plugins/jvectormap/jquery-jvectormap-world-mill-en.js">
	
</script>
<!-- jQuery Knob Chart -->
<script src="plugins/knob/jquery.knob.js">
	
</script>
<!-- datepicker -->
<script src="plugins/datepicker/bootstrap-datepicker.js">
	
</script>
<!-- Bootstrap WYSIHTML5 -->
<script
	src="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js">
	
</script>
<!-- Slimscroll -->
<script src="plugins/slimScroll/jquery.slimscroll.min.js">
	
</script>
<!-- FastClick -->
<script src="plugins/fastclick/fastclick.min.js">
	
</script>
<!-- AdminLTE App -->
<script src="dist/js/app.min.js">
	
</script>
<!-- AdminLTE for demo purposes -->
<script src="dist/js/demo.js">

</script>
<script>
 
$(function(){
    $(".clickEvent").click(function(){
      
       
         $(".clickEvent").removeClass("menu-active");
          $(this).addClass("menu-active");
      
    });
});
</script>


</body>
</html>

