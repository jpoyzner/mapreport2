define(['react', 'utils/css'], function(React, Css) {
	return React.createClass({
		getInitialState: function() {
			Css.load('options');
			return {};
		},
		render: function() {
			return (
				<div id="mr-options-bucket">
					{this.props.loading ?
						<span className="header">&nbsp;MAPREPORT</span>
						: <div id="mr-options">
							<div className="mr-option mr-topic-option" onClick={this.toggleMenu}>
								<span className="mr-option-cell">
									<span>TOPIC: {this.props.news.topic || 'All Topics'}</span>
									{this.props.news.topics.models.map(function(topic) {
										return (
											<div onClick={this.updateTopic}>{topic.get('node')}</div>
										);
									}.bind(this))}
								</span>
							</div>
							<div className="mr-option mr-location-option" onClick={this.toggleMenu}>
								<span className="mr-option-cell">
									<span>LOCATION: {this.props.news.loc || 'All Locations'}</span>
									{this.props.news.locations.models.map(function(location) {
										return (
											<div onClick={this.updateLocation}>{location.get('node')}</div>
										);
									}.bind(this))}
								</span>
							</div>
							<div className="mr-option mr-time-option" onClick={this.toggleMenu}>
								<span className="mr-option-cell">
									<span>TIME: {this.props.news.date || 'All Time'}</span>
									{this.props.news.dates.models.map(function(date) {
										return (
											<div onClick={this.updateTime}>{date.get('node')}</div>
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