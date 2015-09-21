define(['react', 'utils/detector', 'collections/news', 'components/options', 'components/map', 'components/report', 'router'],
function(React, Detector, News, Options, Map, Report, Router) {
	return React.createClass({
		getInitialState: function() {
			window.page = this;
			
			new News("http://" + Router.rootDomain + Router.pathPrefix, Router.settings)
				.on('sync', function(news) {
					//won't need coordinates probably
			    	this.setState({loading: false, news: news, latitude: 37.759753, longitude: -122.50232699999998});
			    }.bind(this))
			    .on('request', function() {
			    	this.setState({loading: true});
			    }.bind(this));
			
		    return {loading: true};
		},
		render: function() {
			return (
				<div id="mr-buckets" className={Detector.phone() ? 'mobile' : 'desktop'}>
					<Options news={this.state.news} />
					<Map news={this.state.news} latitude={this.state.latitude} longitude={this.state.longitude} />
					<div id="mr-report-bucket">
						{(this.state.loading || this.state.news.fetches) ?
							"LOADER"
							: <Report news={this.state.news} />
						}
					</div>
				</div>
			);
		}
	});
});