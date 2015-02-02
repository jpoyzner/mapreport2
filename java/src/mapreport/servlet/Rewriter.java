package mapreport.servlet;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

@RewriteConfiguration
public class Rewriter extends HttpConfigurationProvider
{
	@Override
   	public int priority() {
		return 10;
	}

	@Override
	public Configuration getConfiguration(final ServletContext context) {
		ConfigurationBuilder builder = ConfigurationBuilder.begin();
		addRulesForPath("/topic/{topic}/location/{location}/date", builder);
		addRulesForPath("/location/{location}/date", builder);
		addRulesForPath("/topic/{topic}/date", builder);
		addRulesForPath("/topic/{topic}/location", builder);
		addRulesForPath("/date", builder);
		addRulesForPath("/location", builder);
		addRulesForPath("/topic", builder);	
		return builder;
	}
	
	private final ConfigurationBuilder addRulesForPath(String path, ConfigurationBuilder builder) {
		return builder.addRule(Join.path(path + "/{anything}").to("/"))
			.addRule(Join.path(path + "/{anything1}/{anything2}").to("/{anything1}/{anything2}"))
			.addRule(Join.path(path + "/{anything1}/{anything2}/{anything3}").to("/{anything1}/{anything2}/{anything3}"))
			.addRule(
				Join.path(path + "/{anything1}/{anything2}/{anything3}/{anything4}")
					.to("/{anything1}/{anything2}/{anything3}/{anything4}"));
	}
}