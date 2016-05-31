define(['react', 'utils/detector', 'collections/news', 'components/options', 'components/map', 'components/report', 'lib/spin'],
function(React, Detector, News, Options, Map, Report, Spinner) {
	return React.createClass({
		getInitialState: function() {
			window.page = this;
			
			//TODO: duplicated in router.js and page.js
			new News("http://" + router.rootDomain + router.pathPrefix, router.settings)
				.on('sync', function(news) {
					//won't need coordinates probably
			    	this.setState({
			    		loading: false,
			    		news: news, latitude: 37.759753,
			    		longitude: -122.50232699999998,
			    		search: news.search});
			    }.bind(this))
			    .on('request', function() {
			    	this.setState({loading: true});
			    }.bind(this));
			
		    return {loading: true};
		},
		render: function() {
			console.log(this.state.loading);
			
			return (
				<div id="mr-buckets" className={Detector.phone() ? 'mobile' : 'desktop'}>
					<Options news={this.state.news} loading={this.state.loading} />
					<Map news={this.state.news}
						latitude={this.state.latitude}
						longitude={this.state.longitude}
						loading={this.state.loading}
						search={this.state.search} />
					<div id="mr-report-bucket">
						{this.state.loading ?
							""
							: <Report news={this.state.news} />
						}
					</div>
					<div id="mr-spinner" />
				</div>
			);
		},
		componentDidMount: function() {
			new Spinner({color: 'blue'}).spin($('#mr-spinner')[0]);
			
			setTimeout(function() {
				this.articleIndex = 0;
				this.cycleNewsInterval = setInterval(this.cycleNews, 5000);
			}.bind(this), 200);
		},
		componentDidUpdate: function() {
			$('#mr-spinner').css('display', this.state.loading ? 'static' : 'none');
		},
		prepareNewsCycle: function() {
			clearInterval(this.cycleNewsInterval);
			clearTimeout(this.cycleNewsTimeout);
			
			this.cycleNewsTimeout = setTimeout(function() {
				this.cycleNewsInterval = setInterval(this.cycleNews, 5000);
			}.bind(this), 30000);
		},
		cycleNews: function() {
			var articles = $('.mr-report-article');
			if (this.articleIndex >= articles.length) {
				this.articleIndex = 0;
			}
			
			$('.mr-marker').removeClass('mr-selected-marker');
			$('.mr-report-article').removeClass('mr-selected-article');
			
			var article = $(articles[this.articleIndex]);
			if (article.length) {
				article.addClass('mr-selected-article');
				this.scrollTo(article);
			}
			
			$('.mr-marker[data-cid="' + article.attr('data-cid') + '"]').addClass('mr-selected-marker');
			
			this.articleIndex++;
		},
		scrollTo: function($target) {
		    var $container = $('#mr-report-bucket');

		    var pos = $target.position(), height = $target.outerHeight();
		    var containerScrollTop = $container.scrollTop(), containerHeight = $container.height();
		    var top = pos.top + containerScrollTop;     // position.top is relative to the scrollTop of the containing element

		    var paddingPx = containerHeight * 0.15;      // padding keeps the target from being butted up against the top / bottom of the container after scroll

		    if (top < containerScrollTop) {     // scroll up                
		        $container.scrollTop(top - paddingPx);
		    } else if (top + height > containerScrollTop + containerHeight) {     // scroll down
		        $container.scrollTop(top + height - containerHeight + paddingPx);
		    }
		}
	});
});