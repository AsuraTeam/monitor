/**
 * @FileName: DateFormatTool.java
 * @Package: com.asura.sms.archetype.commons.util
 * @author sence
 * @created 4/14/2016 7:39 PM
 * <p/>
 * Copyright 2015 asura
 */
package com.asura.common.velocity;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class DateFormatTool extends Directive {

    private static final String DEFAULT_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final Logger LOGGER = LoggerFactory.getLogger(DateFormatTool.class);

    @Override
    public String getName() {
        return "dateFormat";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        Date date = null;
        String pattern = null;
        try {
            //params
            if (node.jjtGetChild(0) != null) {
                date = (Date) node.jjtGetChild(0).value(context);
            }
            if (node.jjtGetChild(1) != null) {
                pattern = String.valueOf(node.jjtGetChild(1).value(context));
            }
            if ("".equals(pattern)) {
                pattern = DEFAULT_FORMAT_PATTERN;
            }
            writer.write(format(date, pattern));
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("VELOCITY RENDER ERROR:", e);
            }
            writer.write("");
        }
        return true;
    }

    private String format(Date date, String formatPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
        return sdf.format(date);
    }
}
