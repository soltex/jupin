/**
 * 
 * @author ZhangHuihua@msn.com
 * @param {Object} opts Several options
 */
(function($){
	/**
	 *
	 * @param {Object} args {pageNum:"",numPerPage:"",orderField:"",orderDirection:""}
	 * @param String formId 分页表单选择器，非必填项默认值是 "pagerForm"
	 */
	function _getPagerForm($parent, args) {
		//var form = $("#pagerForm", $parent).get(0);
		var form = $("#pagerForm").get(0);
		if (form) {
			if (args["pageNum"]) form[DWZ.pageInfo.pageNum].value = args["pageNum"];
			if (args["numPerPage"]) form[DWZ.pageInfo.numPerPage].value = args["numPerPage"];
			if (args["orderField"]) form[DWZ.pageInfo.orderField].value = args["orderField"];
			if (args["orderDirection"] && form[DWZ.pageInfo.orderDirection]) form[DWZ.pageInfo.orderDirection].value = args["orderDirection"];
		}
		
		return form;
	}

	/**
	 * 处理分页和排序
	 * rel: 可选 用于局部刷新div id号
	 * data: pagerForm参数 {pageNum:"n", numPerPage:"n", orderField:"xxx", orderDirection:""}
	 */
	 DWZ.dwzPageBreak = function(options, event){
		var op = $.extend({ rel:"", data:{pageNum:"", numPerPage:"", orderField:"", orderDirection:""}, callback:null}, options);

		var $box = null;
		if (op.rel) {
			$box = $("#" + op.rel);
		} else {
			$box = $(event.target).unitBox();
		}

		var form = _getPagerForm($box, op.data);
		if (form) {
			$box.ajaxUrl({
				type:"POST", url:$(form).attr("action"), data: $(form).serializeArray(), callback:function(){}
			});
		}
	}
	
	/**
	 * 处理div上的局部查询, 会重新载入指定div
	 * @param {Object} form
	 */
	DWZ.dwzSearch = function(form, rel){
		var $form = $(form);
		if (form[DWZ.pageInfo.pageNum]) form[DWZ.pageInfo.pageNum].value = 1;

		var $box = null;
		if (rel) {
			$box = $("#" + rel);
		} else {
			$box = $form.unitBox();
		}
		$box.ajaxUrl({
			type:"POST", url:$form.attr("action"), data: $form.serializeArray(), callback:function(){}
		});

		return false;
	}

	$.fn.extend({
		pagination: function(opts){
			var setting = {
				pagination:'\
				<ul class="pagination">\
					<li class="j-first"><a href="javascript:;">&lt;&lt;</a></li>\
					<li class="j-prev"><a href="javascript:;">&lt;</a></li>\
					#pageNumFrag#\
					<li class="j-next"><a href="javascript:;">&gt;</a></li>\
					<li class="j-last"><a href="javascript:;">&gt;&gt;</a></li>\
				</ul>',
				first$:"li.j-first", prev$:"li.j-prev", next$:"li.j-next", last$:"li.j-last", nums$:"li.j-num>a", jumpto$:"li.jumpto",
				pageNumFrag:'<li class="#liClass#"><a href="javascript:;">#pageNum#</a></li>'
			};
			return this.each(function(){
				var $this = $(this);
				var pc = new Pagination(opts);
				var interval = pc.getInterval();
	
				var pageNumFrag = '';
				for (var i=interval.start; i<interval.end;i++){
					pageNumFrag += setting.pageNumFrag.replaceAll("#pageNum#", i).replaceAll("#liClass#", i==pc.getCurrentPage() ? 'active j-num' : 'j-num');
				}
				$this.html(setting.pagination.replaceAll("#pageNumFrag#", pageNumFrag).replaceAll("#currentPage#", pc.getCurrentPage()));
	
				var $first = $this.find(setting.first$);
				var $prev = $this.find(setting.prev$);
				var $next = $this.find(setting.next$);
				var $last = $this.find(setting.last$);
				
				if (pc.hasPrev()){
					$first.add($prev).removeClass('disabled');
					_bindEvent($prev, pc.getCurrentPage()-1, pc.rel());
					_bindEvent($first, 1, pc.rel());
				} else {
					$first.add($prev).addClass('disabled');
				}
				
				if (pc.hasNext()) {
					$next.add($last).removeClass('disabled');
					_bindEvent($next, pc.getCurrentPage()+1, pc.rel());
					_bindEvent($last, pc.numPages(), pc.rel());
				} else {
					$next.add($last).addClass('disabled');
				}
	
				$this.find(setting.nums$).each(function(i){
					var $num = $(this);

					if (!$num.parent().hasClass('active')) {
						_bindEvent($num, i+interval.start, pc.rel());
					}
				});
				$this.find(setting.jumpto$).each(function(){
					var $this = $(this);
					var $inputBox = $this.find(":text");
					var $button = $this.find(":button");
					$button.click(function(event){
						var pageNum = $inputBox.val();
						if (pageNum && pageNum.isPositiveInteger()) {
							DWZ.dwzPageBreak({rel:pc.rel(), data: {pageNum:pageNum}});
						}
					});
					$inputBox.keyup(function(event){
						if (event.keyCode == DWZ.keyCode.ENTER) $button.click();
					});
				});
			});
			
			function _bindEvent($target, pageNum, rel){
				$target.bind("click", {pageNum:pageNum}, function(event){
					DWZ.dwzPageBreak({rel:rel, data:{pageNum:event.data.pageNum}}, event);
					event.preventDefault();
				});
			}
		},
		
		orderBy: function(options){
			var op = $.extend({rel:"", asc:"asc", desc:"desc"}, options);
			return this.each(function(){
				var $this = $(this).css({cursor:"pointer"}).click(function(event){
					var orderField = $this.attr("orderField");
					var orderDirection = $this.hasClass(op.asc) ? op.desc : op.asc;
					DWZ.dwzPageBreak({rel:op.rel, data:{orderField: orderField, orderDirection: orderDirection}}, event);
				});
				
			});
		},
		pagerForm: function(options){
			var op = $.extend({pagerForm$:"#pagerForm", parentBox:document}, options);
			var frag = '<input type="hidden" name="#name#" value="#value#" />';
			return this.each(function(){
				var $searchForm = $(this), $pagerForm = $(op.pagerForm$, op.parentBox);
				var actionUrl = $pagerForm.attr("action").replaceAll("#rel#", $searchForm.attr("action"));
				$pagerForm.attr("action", actionUrl);
				$searchForm.find(":input").each(function(){
					var $input = $(this), name = $input.attr("name");
					if (name && (!$input.is(":checkbox,:radio") || $input.is(":checked"))){
						if ($pagerForm.find(":input[name='"+name+"']").length == 0) {
							var inputFrag = frag.replaceAll("#name#", name).replaceAll("#value#", $input.val());
							$pagerForm.append(inputFrag);
						}
					}
				});
			});
		}
	});
	
	var Pagination = function(opts) {
		this.opts = $.extend({
			rel:"", //用于局部刷新div id号
			totalCount:0,
			numPerPage:10,
			pageNumShown:10,
			currentPage:1,
			callback:function(){return false;}
		}, opts);
	}
	
	$.extend(Pagination.prototype, {
		rel:function(){return this.opts.rel},
		numPages:function() {
			return Math.ceil(this.opts.totalCount/this.opts.numPerPage);
		},
		getInterval:function(){
			var ne_half = Math.ceil(this.opts.pageNumShown/2);
			var np = this.numPages();
			var upper_limit = np - this.opts.pageNumShown;
			var start = this.getCurrentPage() > ne_half ? Math.max( Math.min(this.getCurrentPage() - ne_half, upper_limit), 0 ) : 0;
			var end = this.getCurrentPage() > ne_half ? Math.min(this.getCurrentPage()+ne_half, np) : Math.min(this.opts.pageNumShown, np);
			return {start:start+1, end:end+1};
		},
		getCurrentPage:function(){
			var currentPage = parseInt(this.opts.currentPage);
			if (isNaN(currentPage)) return 1;
			return currentPage;
		},
		hasPrev:function(){
			return this.getCurrentPage() > 1;
		},
		hasNext:function(){
			return this.getCurrentPage() < this.numPages();
		}
	});
})(jQuery);
