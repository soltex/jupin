/**
 * jQuery ajax history plugins
 * 只考虑支持onhashchange事件的浏览器
 * @author z@j-ui.com
 */


(function($){

	$.extend({
		
		History: {
			/**
			 * {
			 * 	hashKey1:{url:'',data:null}
			 * 	...
			 * }
			 */
			_hash: {},
			_setting: {containerId: 'container', homeHash: 'index'},
			_supportHashChange: false,

			init: function(options){
				$.extend(this._setting, options);

				this._supportHashChange = 'onhashchange' in window;

				var _hash = location.hash.skipChar('#').replace(/\?.*$/, '');
				if (_hash){ //刷新根据hash定位
					this._loadHistory(_hash);
				}

				$(window).bind('hashchange', function(event){
					var current_hash = location.hash.skipChar('#').replace(/\?.*$/, '') || $.History._setting.homeHash;
					$.History._loadHistory(current_hash);
				});
			},

			addHistory: function(hash, url, data){
				var current_hash = hash.replace(/\?.*$/, '');
				$.History._hash[current_hash] = {url:url, data:data};
			},

			loadHistory: function(hash){
				// 修改hash自动触发this._loadHistory(hash)
				location.hash = hash;

				// IE8以前版本浏览器，不支持onhashchange事件
				if (!this._supportHashChange) {
					this._loadHistory(hash);
				}
			},
			reload: function(){
				$(window).trigger('hashchange');
			},

			_loadHistory: function(hash){
				var hashItem = $.History._hash[hash] || {url:hash};
				$('#'+$.History._setting.containerId).loadUrl(hashItem.url, hashItem.data);
			}

		}
	});
})(jQuery);
