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
								data-cid={article.cid}
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
		componentDidMount: function() {
			setTimeout(function() {
				$('.mr-report-article').off().hover(
					function() {
						$('.mr-marker').removeClass('mr-selected-marker');
						
						var article = $(this);
						article.addClass('mr-selected-article');
						$('.mr-marker[data-cid="' + article.attr('data-cid') + '"]').addClass('mr-selected-marker');
					},
					function() {
						var article = $(this);
						article.removeClass('mr-selected-article');
						$('.mr-marker[data-cid="' + article.attr('data-cid') + '"]').removeClass('mr-selected-marker');
					});
			}, 200);
			
			setTimeout(function() {
				var articles = $('.mr-report-article');
				var articleIndex = 0;
				setInterval(function() {
					if (articleIndex >= articles.length) {
						articleIndex = 0;
					}
					
					$('.mr-marker').removeClass('mr-selected-marker');
					$('.mr-report-article').removeClass('mr-selected-article');
					
					var article = $(articles[articleIndex]);
					article.addClass('mr-selected-article');
					$('.mr-marker[data-cid="' + article.attr('data-cid') + '"]').addClass('mr-selected-marker');
					
					articleIndex++;
				}, 5000);
			}, 200);
		},
		goToArticle: function(article, event) {
			open(article.get($(event.target).is('.mr-video') ? 'video' : 'url'), '_blank');
		}
	});
});
