define(['react', 'utils/css'], function(React, Css) {
	return React.createClass({
		getInitialState: function() {
			Css.load('options');
			return {};
		},
		render: function() {
			var imgPrefix = "http://www.mapreport.com/images/";
			
			return (
				<div id="mr-options-bucket">
					{this.props.loading ?
						<span className="header">&nbsp;MAPREPORT</span>
						: <div id="mr-options">
							<div className="mr-option mr-topic-option" onClick={this.toggleMenu}>
								<span className="mr-option-cell">
									<div className="mr-option-title">TOPIC: {this.props.news.topic || 'All Topics'}</div>
									{this.props.news.topics.models.map(function(topic) {
										return (
											<div>
												<img src={imgPrefix + topic.get('icon')} />
												<span onClick={this.updateTopic}>
													<span>{topic.get('node')}</span>
												</span>
											</div>
										);
									}.bind(this))}
								</span>
							</div>
							<div className="mr-option mr-location-option" onClick={this.toggleMenu}>
								<span className="mr-option-cell">
									<div className="mr-option-title">LOCATION: {this.props.news.loc || 'All Locations'}</div>
									{this.props.news.locations.models.map(function(location) {
										return (
											<div>
												<img src={imgPrefix + location.get('icon')} />
												<span onClick={this.updateLocation}>	
													<span>{location.get('node')}</span>
												</span>
											</div>
										);
									}.bind(this))}
								</span>
							</div>
							<div className="mr-option mr-time-option" onClick={this.toggleMenu}>
								<span className="mr-option-cell">
									<div className="mr-option-title">TIME: {this.props.news.date || 'All Time'}</div>
									{this.props.news.dates.models.map(function(date) {
										return (
											<div>
												<img src={imgPrefix + date.get('icon')} />
												<span onClick={this.updateTime}>
													<span>{date.get('node')}</span>
												</span>
											</div>
										);
									}.bind(this))}
								</span>
							</div>
						</div>}
				</div>
			);
		},
		toggleMenu: function(event) {
			$(event.currentTarget).toggleClass('mr-expanded');
		},
		updateTopic: function(event) {
			this.props.news.topic = $(event.target).html();
			this.props.news.fetch();
		},
		updateLocation: function(event) {
			this.props.news.loc = $(event.target).html();
			this.props.news.mapBounds = null;
			this.props.news.fetch();
		},
		updateTime: function(event) {
			this.props.news.date = $(event.target).html();
			this.props.news.fetch();
		}
	});
});