<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ include file="/inc/var.jsp" %>


<div class="row parentproductcategory">
	<div class="col-md-2" id="collevel1" >
		<select id="level1" size="10" class="form-control" name="level1" no='1'>
			<c:forEach var="item" items="${productCategoryDetailsOfRoot }">
				<option value="${item.id }" leafable=${item.leafable }>${item.categoryName }</option>
			</c:forEach>
		</select>
	</div>
	
	
	<div class="col-md-2" id="collevel2" ></div>
	<div class="col-md-2" id="collevel3"></div>
	<div class="col-md-2" id="collevel4"></div>
	<div class="col-md-2" id="collevel5" ></div>
	<div class="col-md-2" id="collevel6" ></div>
	
</div>


<script type="text/javascript">

	$(document).ready(function() {
		
		bindSelectEvent();
		
		function bindSelectEvent(context) {
			if (!context) {
				context = $("#collevel1");
			}
			$("select",context).change(function() {
				
				var $select = $(this);
				var id = $select.val();
				var no = $select.attr('no');
				var leafable;
				$("option", $select).each(function() {
					if ($(this).val() == id) {
						leafable = $(this).attr("leafable");
					}
				});
				console.info("id = " + id + " no = " + no + " leafable = " + leafable);
				clearSub('level' + no);
				if (id != null && id != '' && leafable == 'false') {
					//选择ID并且不存在商品
					$.get(
						"/pdm/select-byparentid",
						{
							parentid:id,
							no:no
						},
						function(data) {
							$("#collevel" + (parseInt(no)+1)).html(data);
							bindSelectEvent($("#collevel" + (parseInt(no)+1)));
						},
						'html'
					);
				}
			});
		}
		
		function clearSub(parentid) {
			if (parentid == null || parentid == '') {
				return ;
			}
			console.info(parentid);
			if (parentid == 'level1') {
				console.info("clear 2,3,4,5,6");
				$("#collevel2").html('');
				$("#collevel3").html('');
				$("#collevel4").html('');
				$("#collevel5").html('');
				$("#collevel6").html('');
				return;
			}
			if (parentid == 'level2') {
				console.info("clear 3,4,5,6");
				$("#collevel3").html('');
				$("#collevel4").html('');
				$("#collevel5").html('');
				$("#collevel6").html('');
				return;
			}
			if (parentid == 'level3') {
				console.info("clear 4,5,6");
				$("#collevel4").html('');
				$("#collevel5").html('');
				$("#collevel6").html('');
				return;
			}
			if (parentid == 'level4') {
				console.info("clear 5,6");
				$("#collevel5").html('');
				$("#collevel6").html('');
				return;
			}
			if (parentid == 'level5') {
				console.info("clear 6");
				$("#collevel6").html('');
				return;
			}
		}
	});

</script>