package com.tower.service.log;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import ch.qos.logback.classic.Level;

/**
 * The Slf4jLogger implementation of Logger.
 */
public class Slf4jLogger implements Logger, Serializable {

    private static final String FQCN = Slf4jLogger.class.getName();

    private org.slf4j.Logger _impl;

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public Slf4jLogger(org.slf4j.Logger logger) {

        _impl = logger;
        
    }

    @Override
    public String getName() {
        return _impl.getName();
    }

    @Override
    public void trace(String message) {
    	_impl.log(FQCN, _impl.isTraceEnabled() ? Level.TRACE : Level.DEBUG,   message, null);
        //_impl.trace(Request.getId() +"-"+ message);
    }

    @Override
    public void trace(String format, Object... argArray) {
        if (isTraceEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            _impl.log(FQCN, _impl.isTraceEnabled() ? Level.TRACE : Level.DEBUG, ft
                    .getMessage(), ft.getThrowable());
        }
        //_impl.trace(Request.getId() +"-"+ format, args);
    }

    @Override
    public boolean isTraceEnabled() {
        return _impl.isTraceEnabled();
    }

    @Override
    public void debug(String message) {
        _impl.log(FQCN, Level.DEBUG,  message, null);
        //_impl.debug(Request.getId() +"-"+ message);
    }

    @Override
    public void debug(String format, Object... argArray) {
        if (_impl.isDebugEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            _impl.log(FQCN, Level.DEBUG,  ft.getMessage(), ft.getThrowable());
        }
        //_impl.debug(Request.getId() +"-"+ format, args);
    }

    @Override
    public boolean isDebugEnabled() {
        return _impl.isDebugEnabled();
    }

    @Override
    public void info(String message) {
        //_impl.info(message);
        _impl.log(FQCN, Level.INFO,   message, null);
    }

    @Override
    public void info(String format, Object... args) {
        if (_impl.isInfoEnabled()) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, args);
            _impl.log(FQCN, Level.INFO,  ft.getMessage(), ft.getThrowable());
        }
        //_impl.info(Request.getId() +"-"+ format, args);
    }

    @Override
    public boolean isInfoEnabled() {
        return _impl.isInfoEnabled();
    }

    @Override
    public void warn(String message) {
        _impl.log(FQCN, Level.WARN,  message, null);
        //_impl.warn(Request.getId() +"-"+ message);
    }

    @Override
    public void warn(String format, Object... args) {
        if (_impl.isEnabledFor(Level.WARN)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, args);
            _impl.log(FQCN, Level.WARN,  ft.getMessage(), ft.getThrowable());
        }
        //_impl.warn(Request.getId() +"-"+ format, args);
    }

    @Override
    public boolean isWarnEnabled() {
        return _impl.isWarnEnabled();
    }

    @Override
    public void error(String message) {
        _impl.log(FQCN, Level.ERROR,  message, null);
        //_impl.error(Request.getId() +"-"+ message);
    }

    @Override
    public void error(String format, Object... args) {
        if (_impl.isEnabledFor(Level.ERROR)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, args);
            _impl.log(FQCN, Level.ERROR,  ft.getMessage(), ft.getThrowable());
        }
        //_impl.error(Request.getId() +"-"+ format, args);
    }

    @Override
    public void error(Exception ex) {
        _impl.log(FQCN, Level.ERROR, "", ex);
        //_impl.error(Request.getId(), ex);
    }

    @Override
    public void error(String message, Exception ex) {
        _impl.log(FQCN, Level.ERROR,   message, ex);
        //_impl.error(Request.getId() +"-"+ message, ex);
    }

    public static String estacktack2Str(Exception ex) {
        PrintStream ps = null;
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ps = new PrintStream(bao);
            ex.printStackTrace(ps);
            return bao.toString("utf-8");
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }


}
