define(['templates', 'utils/css', 'backbone', 'underscore'], function(Templates, Css) {
	return Backbone.View.extend({
		initialize: function(options) {
			Css.load('options');
			
			this.news = options.news;
			this.bucket = $('#mr-options-bucket');
			
			this.listenTo(options.news, 'request', this.refresh);
			this.listenTo(options.news, 'sync', this.render);
		},
		refresh: function() {
			this.bucket.html('<span class="header">&nbsp;MAPREPORT</span>');
		},
		render: function() {
			if (this.news.fetches) {
				return;
			}
			
			this.bucket.html(Templates['mr-options-template'](this.news));
			
			var menus = $('.mr-option');
			
			menus.click(function(e) {
				if ($(e.target).is('input-options')) {
					return;
				}
				
				//this is bullshit but works, can't be done with trigger
				var e1 = document.createEvent("MouseEvents");
				
				e1.initMouseEvent(
					this.expanded ? 'collapse' : 'mousedown',
					true,
					true,
					window,
					1,
					0,
					0,
					0,
					0,
					false,
					false,
					false,
					false,
					0,
					null);
				
				$(this).find('input-options')[0].dispatchEvent(e1);
				this.expanded = !this.expanded;
			});
			
			$(menus[0]).find('input-options').on('action-fired', _.bind(function(event) {
				this.news.topic = event.target.value_;
				this.news.optionsChanged = 2;
				this.news.fetch();
			}, this));
			
			$(menus[1]).find('input-options').on('action-fired', _.bind(function(event) {
				this.news.loc = event.target.value_;
				this.news.optionsChanged = 2;
				this.news.fetch();
			}, this));
			
			$(menus[2]).find('input-options').on('action-fired', _.bind(function(event) {
				this.news.date = event.target.value_;
				this.news.optionsChanged = 2;
				this.news.fetch();
			}, this));
		}
	});
});
