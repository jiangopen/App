package com.example.multiplegranarymanager.A_WuWeiHuaiNanPackage.Body;

import java.util.List;

public class NewDownRawMultipleBody {
    private String url;
    private String method;
    private Object headers;
    private Object query;
    private list body;

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
        this.headers = headers;
    }

    public Object getQuery() {
        return query;
    }

    public void setQuery(Object query) {
        this.query = query;
    }

    public list getBody() {
        return body;
    }

    public void setBody(list body) {
        this.body = body;
    }

    public static class list {
        private List<CountMultiple> list;
        private String commandMapId;
        private String url;

        public String getCommandMapId() {
            return commandMapId;
        }

        public void setCommandMapId(String commandMapId) {
            this.commandMapId = commandMapId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<CountMultiple> getList() {
            return list;
        }

        public void setList(List<CountMultiple> list) {
            this.list = list;
        }
    }
}
