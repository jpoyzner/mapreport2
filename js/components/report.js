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
						var style = {'color': article.get('color'), 'backgroundColor': '#' + article.get('colorHex')};
						
						return (
							<div className={'mr-report-article ' + (article.get('isMain') ? 'mr-main-news' : '')}
								style={style}
								onClick={this.goToArticle.bind(this, article)}>
							
								<img src={article.get('icon')}></img>
								{article.get('video') ? <img className="mr-video" src="http://www.mapreport.com/images/common/video.jpg"></img> : ''}
								<p>{article.get('dateTime')}: {article.get('label')}</p>
							</div>
						);
					}.bind(this))}
				</div>		
			);
		},
		goToArticle: function(article, event) {
			open(article.get($(event.target).is('.mr-video') ? 'video' : 'url'), '_blank');
		}
	});
});
