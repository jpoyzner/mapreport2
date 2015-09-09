define(['jquerycolor'], function() {
	return {
		random: function() {
		  // 30 random hues with step of 12 degrees
		  var hue = Math.floor(Math.random() * 30) * 12;

		  return $.Color({
		    hue: hue,
		    saturation: 0.9,
		    lightness: 0.8,
		    alpha: 1
		  }).toHexString();
		},
		blue: '000080', //NAVY
		yellow: 'FFD700', //GOLD
		gray: '708090', //SLATE GRAY
		orange: 'A0522D', //SIENNA
		green: '228B22', //FOREST GREEN
		purple: '800080',
		white: 'FFFFFF'
	};
});
