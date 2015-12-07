//PERHAPS USE THIS LATER:

//define(['react'], function(React) {
//	return React.createClass({
//		render: function() {
//			return (
//				<div className="mr-option mr-topic-option">
//					<span className="mr-option-cell">
//						<span>TOPIC: </span>
//						<% var topicsList = _.map(topics.models, function(topic) {
//							return [topic.get('node'), topic.get('node')];
//						});
//
//						var topicTitle = typeof topic == 'undefined' ? 'All Topics' : topic %>
//						<input-options options="<%= JSON.stringify(topicsList).replace(/\"/g, '\'') %>" value="<%= topicTitle %>">
//						</input-options>
//					</span>
//				</div>						
//			);
//		},
//		componentDidUpdate: function() {
//			
//		}
//	});
//});