package com.example.multiplegranarymanager.Body.NewDownRaw;

public class NewDownRawBodyTWO {

    private String url;
    private String method;
    private Object headers;
    private Object query;
    private Object body;

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        if (body == null) {
            this.body = new Object();
        } else {
            this.body = body;
        }
    }
//    private List body;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getHeaders() {
        return headers;
    }

    public void setHeaders(Object headers) {
        if (headers == null) {
            this.headers = new Object();
        } else {
            this.headers = headers;
        }
    }

    public Object getQuery() {
        return query;
    }

    public void setQuery(Object query) {
        if (query == null) {
            this.query = new Object();
        } else {
            this.query = query;
        }
    }
}
