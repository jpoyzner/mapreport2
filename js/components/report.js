define(['react', 'utils/css', 'utils/color'], function(React, Css, Color) {
	return React.createClass({
		getInitialState: function() {
			Css.load('report');		
			return {};
		},
		render: function() {
			return (
				<div id="mr-report">
					{this.props.news.models.map(function(article) {
						return (
							<div className='mr-report-article' style={{'backgroundColor': '#' + article.get('colorHex')}}>
								<img src={article.get('icon')}></img>
								<a href={article.get('url')} target="_blank"><p />{article.get('dateTime')}: {article.get('label')}<p /></a>
							</div>
						);
					})}
				</div>		
			);
		}
	});
});
