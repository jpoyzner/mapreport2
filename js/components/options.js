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
							<div className="mr-option mr-topic-option">
								<span className="mr-option-cell">
									<span>TOPIC: {this.props.news.topic || 'All Topics'}</span>
									{this.props.news.topics.models.map(function(topic) {
										return (
											<div>{topic.get('node')}</div>
										);
									})}
								</span>
							</div>
							<div className="mr-option mr-location-option">
								<span className="mr-option-cell">
									<span>LOCATION: {this.props.news.loc || 'All Locations'}</span>
									{this.props.news.locations.models.map(function(location) {
										return (
											<div>{location.get('node')}</div>
										);
									})}
								</span>
							</div>
							<div className="mr-option mr-time-option">
								<span className="mr-option-cell">
									<span>TIME: {this.props.news.date || 'All Time'}</span>
									{this.props.news.dates.models.map(function(date) {
										return (
											<div>{date.get('node')}</div>
										);
									})}
								</span>
							</div>
						</div>}
				</div>
			);
		},
		componentDidUpdate: function() {
			var menus = $('.mr-option');
			
			menus.click(function(e) {
				$(e.currentTarget).toggleClass('mr-expanded')
			});
			
			$(menus[0]).find('div').click(function(event) {
				this.props.news.topic = $(event.target).html();
				this.props.news.fetch();
			}.bind(this));
			
			$(menus[1]).find('div').click(function(event) {
				this.props.news.loc = $(event.target).html();
				this.props.news.fetch();
			}.bind(this));
			
			$(menus[2]).find('div').click(function(event) {
				this.props.news.date = $(event.target).html();
				this.props.news.fetch();
			}.bind(this));
		}
	});
});