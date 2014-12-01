<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<form:form action="/pdm/add-sizetemplate-action" method="post" commandName="sizeForm" cssClass="required-validate">
	
<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		<h4 class="modal-title">新建尺码模板</h4>
		</div>
		<div class="modal-body">
			<div class="form-group">
				<label for="templateName">模板名称</label>
				<form:input path="templateName" cssClass="form-control" placeholder="模板名称"  required="required" />
			</div>
			<div class="form-group">
				 <label for="templateContent">模板描述信息</label>
				<form:textarea path="templateContent" cssClass="form-control" placeholder="模板描述信息" />
			</div>
			<div class="panel panel-default">
				<div class="panel-body">
					<label>
      					<input type="checkbox"> 胸围
    				</label>
    				<label>
      					<input type="checkbox"> 胸围
    				</label>
    				<label>
      					<input type="checkbox"> 胸围
    				</label>
    				<label>
      					<input type="checkbox"> 胸围
    				</label>
    				<label>
      					<input type="checkbox"> 胸围
    				</label>
    				<label>
      					<input type="checkbox"> 胸围
    				</label>
    				<input type="button" value="新建行" class="btn btn-default"/>
				</div>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>尺码名称</th>
							<th>胸围</th>
							<th>腰围</th>
							<th>体重</th>
							<th>臀围</th>
							<th>身高</th>
							<th>肩宽</th>
							<th>##</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div>
	</div>
	<div class="modal-footer">
		<button type="submit" class="btn btn-default">保存尺码模板信息</button>
	</div>
</form:form>