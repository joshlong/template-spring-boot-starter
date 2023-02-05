package com.joshlong.templates;

import com.samskivert.mustache.Mustache;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;

@Configuration
@EnableConfigurationProperties(TemplateProperties.class)
@ImportRuntimeHints(TemplateAutoConfiguration.TemplateHints.class)
public class TemplateAutoConfiguration {

	static class TemplateHints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			var classPathResource = new ClassPathResource("org/commonmark/internal/util/entities.properties");
			hints.resources().registerResource(classPathResource);
		}

	}

	private final Charset charset;

	public TemplateAutoConfiguration(TemplateProperties templateProperties) {
		var charset = templateProperties.charset();
		this.charset = !StringUtils.hasText(charset) ? Charset.defaultCharset() : Charset.forName(charset);
	}

	@Bean
	MarkdownService markdownService() {
		return new MarkdownService(Parser.builder().build(), HtmlRenderer.builder().build());
	}

	@Bean
	MustacheService mustacheService(Mustache.Compiler compiler) {
		return new MustacheService(compiler, this.charset);
	}

}
