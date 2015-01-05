define(['underscore', 'backbone'], function() {
	return Backbone.Model.extend({
		initialize: function(data) {
			//TODO: replace with a pre-generated topic icon and number or a way to combine the two:
			
			//DOC here:https://developers.google.com/chart/image/docs/gallery/dynamic_icons
			//THIS IS TECHINCALY ALREADY DEPRECTAED!
			this.set('icon', 'http://chart.apis.google.com/chart?chst=d_map_spin&chld=.75|0|FF0000|12|_|' + data.mapLabel);
			//OLD DEFAULT ICON: 'http://www.mapreport.com/images/common/list5.gif'
		}
	});
});