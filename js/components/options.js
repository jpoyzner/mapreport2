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
									<div className="mr-option-title">TOPIC: {decodeURI(this.props.news.topic || 'All Topics')}</div>
									{this.props.news.topics.models.map(function(topic) {
										return (
											<div onClick={this.updateTopic}>
												<img src={imgPrefix + topic.get('icon')} />
												<span>
													<span>{topic.get('node')}</span>
												</span>
											</div>
										);
									}.bind(this))}
								</span>
							</div>
							<div className="mr-option mr-location-option" onClick={this.toggleMenu}>
								<span className="mr-option-cell">
									<div className="mr-option-title">LOCATION: {decodeURI(this.props.news.loc || 'All Locations')}</div>
									{this.props.news.locations.models.map(function(location) {
										return (
											<div onClick={this.updateLocation}>
												<img src={imgPrefix + location.get('icon')} />
												<span>	
													<span>{location.get('node')}</span>
												</span>
											</div>
										);
									}.bind(this))}
								</span>
							</div>
							<div className="mr-option mr-time-option" onClick={this.toggleMenu}>
								<span className="mr-option-cell">
									<div className="mr-option-title">TIME: {decodeURI(this.props.news.date || 'Latest')}</div>
									{this.props.news.dates.models.map(function(date) {
										return (
											<div onClick={this.updateTime}>
												<img src={imgPrefix + date.get('icon')} />
												<span>
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
			this.props.news.topic = $(event.currentTarget).find('span span').html();
			this.props.news.fetch();
		},
		updateLocation: function(event) {
			this.props.news.loc = $(event.currentTarget).find('span span').html();
			this.props.news.mapBounds = null;
			this.props.news.fetch();
		},
		updateTime: function(event) {
			this.props.news.date = $(event.currentTarget).find('span span').html();
			this.props.news.fetch();
		}
	});
});