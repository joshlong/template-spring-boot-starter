package com.joshlong.templates;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("templates")
public class TemplateProperties {

	private String charset;

}
