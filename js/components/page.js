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
		},
		componentDidUpdate: function() {
			$('#mr-spinner').css('display', this.state.loading ? 'static' : 'none');
		}
	});
});