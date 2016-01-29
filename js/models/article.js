define(['utils/color', 'underscore', 'backbone'], function(Color) {
	return Backbone.Model.extend({
		initialize: function(data) {
			//TODO: replace with a pre-generated topic icon and number or a way to combine the two:
			
			//DOC here:https://developers.google.com/chart/image/docs/gallery/dynamic_icons
			
			var color = 'black';
			var colorHex;
			switch(data.rootTopic) {
				case 'Business': color = 'white'; colorHex = Color.blue; break;
				case 'Disasters': colorHex = Color.yellow; break;
				case 'Politics': color = 'white'; colorHex = Color.gray; break;
				case 'Crime': colorHex = Color.orange; break;
				case 'Sports': colorHex = Color.green; break;
				case 'Science & Education': colorHex = Color.purple; break;
				default: colorHex = Color.white;
			}
			this.set('color', color);
			this.set('colorHex', colorHex);
			
//			this.set(
//				'icon',
//				'http://chart.apis.google.com/chart?chst=d_map_spin&chld=.75|0|' + colorHex + '|12|_|' + data.mapLabel);
			
			//OLD DEFAULT ICON: 'http://www.mapreport.com/images/common/list5.gif'
			
			//hex random: //Color.random().slice(1)
			
			/*RELATED LINKS:
			 * http://stackoverflow.com/questions/3562506/add-numbering-label-to-google-map-marker?lq=1
			 * http://stackoverflow.com/questions/2436484/how-can-i-create-numbered-map-markers-in-google-maps-v3
			 * http://stackoverflow.com/questions/16734830/google-maps-custom-marker-with-number
			 * http://stackoverflow.com/questions/2890670/google-maps-place-number-in-marker
			 * https://developers.google.com/maps/documentation/javascript/markers
			 */
		}
	});
});