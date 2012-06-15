package com.prefabsoft.log;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.json.RooJson;

@RooJavaBean
@RooToString
@RooEntity
@RooJson
public class Log {

    public Log() {
		super();
	}

	public Log(String logLevel, String logMessage) {
		super();
		this.logLevel = logLevel;
		this.logMessage = logMessage;
	}

	private String logLevel;
	
	@Column(length=2048)
    private String logMessage;

    private Boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date updateDate;
}
