package com.zfoo.web.wtest.form;

import org.apache.commons.fileupload.FileUploadBase;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.*;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2019/1/8
 */
public class MockHttpServletRequest implements HttpServletRequest {

    private static class MyServletInputStream extends ServletInputStream {
        private final InputStream in;

        private final int readLimit;

        /*
         Creates a new instance, which returns the given streams data.
         */
        public MyServletInputStream(final InputStream pStream, final int readLimit) {
            this.in = pStream;
            this.readLimit = readLimit;
        }

        @Override
        public int read() throws IOException {
            return this.in.read();
        }

        @Override
        public int read(final byte[] b, final int off, final int len) throws IOException {
            if ((this.readLimit > 0)) {
                return this.in.read(b, off, Math.min(this.readLimit, len));
            }
            return this.in.read(b, off, len);
        }

        @Override
        public boolean isFinished() {
            try {
                int _available = this.in.available();
                return (_available == 0);
            } catch (Throwable _e) {
                throw new RuntimeException();
            }
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(final ReadListener arg0) {
        }
    }

    private final InputStream m_requestData;

    private long length;

    private final String m_strContentType;

    private final Map<String, String> m_headers = new HashMap<>();

    /*
     Creates a new instance with the given request data and content type.
     */
    public MockHttpServletRequest(final byte[] requestData, final String strContentType) {
        this(new ByteArrayInputStream(requestData), requestData.length, strContentType);
    }

    /*
     Creates a new instance with the given request data and content type.
     */
    public MockHttpServletRequest(final InputStream requestData, final long requestLength, final String strContentType) {
        this.m_requestData = requestData;
        this.length = requestLength;
        this.m_strContentType = strContentType;
        this.m_headers.put(FileUploadBase.CONTENT_TYPE, strContentType);
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return null;
    }

    @Override
    public long getDateHeader(final String arg0) {
        return 0;
    }

    @Override
    public String getHeader(final String headerName) {
        return this.m_headers.get(headerName);
    }

    @Override
    public Enumeration<String> getHeaders(final String arg0) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return null;
    }

    @Override
    public int getIntHeader(final String arg0) {
        return 0;
    }

    @Override
    public String getMethod() {
        return "POST";
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(final String arg0) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public String getRequestURI() {
        return null;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(final boolean arg0) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }
    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Deprecated
    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public Object getAttribute(final String arg0) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(final String arg0) {
    }

    @Override
    public int getContentLength() {
        int iLength;
        if ((null == this.m_requestData)) {
            iLength = (-1);
        } else {
            if ((this.length > Integer.MAX_VALUE)) {
                String str = "Value \'" + this.length + "\' is too large to be converted to int";
                throw new RuntimeException(str);
            }
            iLength = ((int) this.length);
        }
        return iLength;
    }


    @Override
    public String getContentType() {
        return this.m_strContentType;
    }

    @Override
    public ServletInputStream getInputStream() {
        int readLimit = (-1);
        return new MyServletInputStream(this.m_requestData, readLimit);
    }

    @Override
    public String getParameter(final String arg0) {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(final String arg0) {
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @SuppressWarnings("javadoc")
    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @SuppressWarnings("javadoc")
    @Override
    public int getLocalPort() {
        return 0;
    }

    @SuppressWarnings("javadoc")
    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @SuppressWarnings("javadoc")
    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(final String arg0, final Object arg1) {
    }

    @Override
    public void removeAttribute(final String arg0) {
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(final String arg0) {
        return null;
    }

    @Deprecated
    @Override
    public String getRealPath(final String arg0) {
        return null;
    }

    @Override
    public boolean authenticate(final HttpServletResponse arg0) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public String changeSessionId() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public Part getPart(final String arg0) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public Collection<Part> getParts()  {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public void login(final String arg0, final String arg1) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public void logout() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(final Class<T> arg0) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public AsyncContext getAsyncContext() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public long getContentLengthLong() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public DispatcherType getDispatcherType() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public boolean isAsyncStarted() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public boolean isAsyncSupported() {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }

    @Override
    public AsyncContext startAsync(final ServletRequest arg0, final ServletResponse arg1) throws IllegalStateException {
        throw new UnsupportedOperationException("TODO: auto-generated method stub");
    }
}
